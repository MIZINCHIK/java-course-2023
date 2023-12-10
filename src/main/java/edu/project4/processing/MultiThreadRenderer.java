package edu.project4.processing;

import edu.project4.image.FractalImage;
import edu.project4.processing.transformation.ContractiveAffineTransformation;
import edu.project4.processing.transformation.Transformation;
import edu.project4.shape.Point;
import edu.project4.shape.Rectangle;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import static edu.project4.shape.Point.rotate;
import static java.lang.Math.log10;
import static java.lang.Math.pow;

public class MultiThreadRenderer implements Renderer {
    private static final int IDLE_ITERATIONS = 20;

    @Override
    public FractalImage render(
        FractalImage canvas,
        Rectangle world,
        List<ContractiveAffineTransformation> affineTransformations,
        List<Transformation> variations,
        ProcessingParameters parameters
    ) {
        if (parameters.symmetry() == 0) {
            throw new IllegalArgumentException();
        }
        int threadNumber = Runtime.getRuntime().availableProcessors();
        CountDownLatch latch = new CountDownLatch(threadNumber);
        int samplesPerThread = parameters.samples() / threadNumber;
        int left = parameters.samples() % threadNumber;
        Random random = new Random(parameters.seed());
        try (var threadPool = Executors.newFixedThreadPool(threadNumber)) {
            for (int i = 0; i < threadNumber; i++) {
                int currentOverLoad = (left > 0) ? 1 : 0;
                threadPool.submit(() -> chaosGame(
                    new ProcessingParameters(samplesPerThread + currentOverLoad,
                        parameters.iterationsPerSample(), random.nextLong(), parameters.gamma(), parameters.symmetry()
                    ),
                    world,
                    affineTransformations,
                    variations,
                    canvas,
                    latch
                ));
                left--;
            }
            latch.await();
            threadPool.shutdown();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
//        correction(canvas, parameters.gamma());
        return canvas;
    }

    protected static void chaosGame(
        ProcessingParameters parameters,
        Rectangle world,
        List<ContractiveAffineTransformation> affineTransformations,
        List<Transformation> variations,
        FractalImage canvas,
        CountDownLatch latch
    ) {
        Random random = new Random(parameters.seed());
        for (int sample = 0; sample < parameters.samples(); sample++) {
            Point point = world.getRandomPoint(random);
            for (int iteration = -IDLE_ITERATIONS; iteration < parameters.iterationsPerSample(); iteration++) {
                var affine = affineTransformations.get(random.nextInt(affineTransformations.size()));
                point = affine.apply(point);
                Transformation variation = variations.get(random.nextInt(variations.size()));
                point = variation.apply(point);
                if (iteration < 0) {
                    continue;
                }
                double theta2 = 0;
                double angleStep = Math.PI * 2 / parameters.symmetry();
                for (int s = 0; s < parameters.symmetry(); theta2 += angleStep, s++) {
                    point = rotate(point, theta2);
                    if (world.contains(point)) {
                        canvas.setPixel(point, world, affine.getRed(), affine.getGreen(), affine.getBlue());
                    }
                }
            }
        }
        if (latch != null) {
            latch.countDown();
        }
    }

    protected static void correction(FractalImage image, double gamma) {
        int width = image.width();
        int height = image.height();
        double[][] normals = new double[width][height];
        double max = 0;
        for (int row = 0; row < height; row++) {
            for (int column = 0; column < width; column++) {
                double current = log10(image.getPixel(column, row).getHitCount());
                normals[column][row] = current;
                max = Math.max(max, current);
            }
        }
        if (max == 0) {
            return;
        }
        for (int row = 0; row < height; row++) {
            for (int column = 0; column < width; column++) {
                image.getPixel(column, row).modifyBrightness(pow(normals[column][row] / max, 1.0 / gamma));
            }
        }
    }
}

package edu.project4;

import edu.project4.files.ImageFormat;
import edu.project4.image.FractalImage;
import edu.project4.processing.MultiThreadRenderer;
import edu.project4.processing.ProcessingParameters;
import edu.project4.processing.Renderer;
import edu.project4.processing.SingleThreadRenderer;
import edu.project4.processing.transformation.ContractiveAffineTransformation;
import edu.project4.processing.transformation.Transformation;
import edu.project4.processing.transformation.variations.Eyefish;
import edu.project4.processing.transformation.variations.Heart;
import edu.project4.processing.transformation.variations.Julia;
import edu.project4.processing.transformation.variations.Square;
import edu.project4.shape.Rectangle;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import static edu.project4.files.ImageUtils.save;

public class Test {
    private Test() {
        throw new IllegalArgumentException();
    }

    @SuppressWarnings({"UncommentedMain", "MagicNumber"})
    public static void main(String[] args) {
        List<ContractiveAffineTransformation> affines = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            affines.add(ContractiveAffineTransformation.create());
        }
        List<Transformation> variations = List.of(
            new Eyefish(),
            new Heart(),
            new Julia(),
            new Square()
        );
        FractalImage image = FractalImage.create(2000, 2000);
        ProcessingParameters parameters =
            new ProcessingParameters(3_000_000, (short) 100, new Random().nextLong(), 2.2, 1);
        Renderer renderer = new MultiThreadRenderer();
        long start = System.nanoTime();
        renderer.render(
            image,
            new Rectangle(3, 3),
            affines,
            variations,
            parameters
        );
        Path images = Path.of(System.getProperty("user.dir")).resolve("images");
        Path image1 = images.resolve("image27.png");
        Path image2 = images.resolve("image28.png");
        save(image, image1, ImageFormat.PNG);
        renderer = new SingleThreadRenderer();
        image = FractalImage.create(2000, 2000);
        start = System.nanoTime();
        renderer.render(
            image,
            new Rectangle(3, 3),
            affines,
            variations,
            parameters
        );
        save(image, image2, ImageFormat.PNG);
    }
}

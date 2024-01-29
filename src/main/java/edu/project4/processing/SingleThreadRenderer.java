package edu.project4.processing;

import edu.project4.image.FractalImage;
import edu.project4.processing.transformation.ContractiveAffineTransformation;
import edu.project4.processing.transformation.Transformation;
import edu.project4.shape.Rectangle;
import java.util.List;

public class SingleThreadRenderer extends MultiThreadRenderer {
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
        chaosGame(
            parameters,
            world,
            affineTransformations,
            variations,
            canvas,
            null
        );
//        correction(canvas, parameters.gamma());
        return canvas;
    }
}

package edu.project4.processing;

import edu.project4.image.FractalImage;
import edu.project4.processing.transformation.ContractiveAffineTransformation;
import edu.project4.processing.transformation.Transformation;
import edu.project4.shape.Rectangle;
import java.util.List;

@FunctionalInterface
public interface Renderer {
    FractalImage render(
        FractalImage canvas,
        Rectangle world,
        List<ContractiveAffineTransformation> affineTransformations,
        List<Transformation> variations,
        ProcessingParameters parameters
    );
}

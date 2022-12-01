
package seproject;

import javafx.scene.shape.Shape;

/**
 *
 * @author Group14
 */
public class ToFrontCommand implements OperationCommand{
    private ShapeModel shapeToChange;

    public ToFrontCommand(ShapeModel shapeToChange) {
        this.shapeToChange = shapeToChange;
    }
    
    
    @Override
    public void execute() throws GenericDrawException {
        ((Shape)shapeToChange).toFront();
    }
    
}

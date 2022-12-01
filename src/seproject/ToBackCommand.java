
package seproject;

import javafx.scene.shape.Shape;

/**
 *
 * @author Group14
 */
public class ToBackCommand implements OperationCommand{
    private ShapeModel shapeToChange;

    public ToBackCommand(ShapeModel shapeToChange) {
        this.shapeToChange = shapeToChange;
    }
    
    
    @Override
    public void execute() throws GenericDrawException {
        ((Shape)shapeToChange).toBack();
    }

    @Override
    public void undo() {
        ((Shape)shapeToChange).toFront();
    }
    
}

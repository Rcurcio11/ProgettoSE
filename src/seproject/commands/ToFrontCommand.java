
package seproject.commands;

import seproject.shapes.ShapeModel;
import javafx.scene.shape.Shape;
import seproject.exceptions.GenericDrawException;

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

    @Override
    public void undo() {
       ((Shape)shapeToChange).toBack();
    }
    
}


package seproject.commands;

import seproject.shapes.ShapeModel;
import javafx.scene.shape.Shape;
import seproject.exceptions.GenericDrawException;

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

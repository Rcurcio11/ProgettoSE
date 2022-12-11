
package seproject.commands;

import seproject.exceptions.GenericDrawException;
import seproject.shapes.ShapeModel;


/**
 *
 * @author Group14
 */
public class RotateCommand implements OperationCommand{
    
    private ShapeModel selectedShape;
    private double angle;

    public RotateCommand(ShapeModel selectedShape, double angle) {
        this.selectedShape = selectedShape;
        this.angle = angle;
    }
    
    
    @Override
    public void execute() throws GenericDrawException {
        selectedShape.rotate(angle);
    }

    @Override
    public void undo() {
        selectedShape.rotate(-angle);
    }
    
}

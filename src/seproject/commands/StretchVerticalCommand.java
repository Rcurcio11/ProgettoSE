
package seproject.commands;

import seproject.exceptions.GenericDrawException;
import seproject.shapes.ShapeModel;

/**
 *
 * @author Group14
 */
public class StretchVerticalCommand implements OperationCommand{
    private ShapeModel shape;
    private double increment;

    public StretchVerticalCommand(ShapeModel shape,double increment) {
        this.increment = increment;
        this.shape = shape;
    }
    
    @Override
    public void execute() throws GenericDrawException {
        shape.stretchVertical(increment);
    }

    @Override
    public void undo() {
        shape.stretchVertical(-increment);
    }
    
}


package seproject;

/**
 *
 * @author Group14
 */
public class StretchHorizontalCommand implements OperationCommand{
    private ShapeModel shape;
    private double increment;
    
    public StretchHorizontalCommand(ShapeModel shape,double increment) {
        this.increment = increment;
        this.shape = shape;
    }
    
    @Override
    public void execute() throws GenericDrawException {
        shape.stretchHorizontal(increment);
    }

    @Override
    public void undo() {
        shape.stretchHorizontal(-increment);
    }
    
}

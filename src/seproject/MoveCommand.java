
package seproject;

import javafx.geometry.Point2D;

/**
 *
 * @author Group14
 */
public class MoveCommand implements OperationCommand{
    
    private ShapeModel selectedShape;


    public MoveCommand(ShapeModel selectedShape) {
        this.selectedShape = selectedShape;
        
    }

    @Override
    public void execute() throws GenericDrawException {
        selectedShape.move();
    }
}

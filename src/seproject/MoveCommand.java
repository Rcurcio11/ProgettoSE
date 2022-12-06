
package seproject;

import javafx.geometry.Point2D;

/**
 *
 * @author Group14
 */
public class MoveCommand implements OperationCommand{
    
    private ShapeModel selectedShape;
    private Point2D translatePoint;
    private Point2D oldTranslatePoint;


    public MoveCommand(ShapeModel selectedShape,Point2D translatePoint) {
        this.selectedShape = selectedShape;
        this.translatePoint = translatePoint;
        Point2D startPoint = selectedShape.getLowerBound();
        this.oldTranslatePoint = new Point2D(-translatePoint.getX(),-translatePoint.getY());
        
    }

    @Override
    public void execute() throws GenericDrawException {
        selectedShape.move(translatePoint);
    }

    @Override
    public void undo() {
        selectedShape.move(oldTranslatePoint);
    }
}

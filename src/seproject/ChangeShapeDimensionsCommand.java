
package seproject;

import javafx.geometry.Point2D;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author Group14
 */
public class ChangeShapeDimensionsCommand implements OperationCommand{

    private AnchorPane drawingArea;
    private Point2D startPoint;
    private Point2D endPoint;
    private ShapeModel shape;  
    private Point2D oldStartPoint;
    private Point2D oldEndPoint;
    
    public ChangeShapeDimensionsCommand(AnchorPane drawingArea, Point2D startPoint, Point2D endPoint, ShapeModel shape) {
        this.drawingArea = drawingArea;
        this.startPoint = startPoint;
        this.endPoint = endPoint;
        this.shape = shape;
        this.oldStartPoint = shape.getStartPoint();
        this.oldEndPoint = shape.getEndPoint();
    }
    
    @Override
    public void execute() throws GenericDrawException {
        if(shape == null)
            throw new ShapeNotSelectedDrawException();
        shape.changeDimensions(startPoint, endPoint);
    }

    @Override
    public void undo() {
        shape.changeDimensions(oldStartPoint, oldEndPoint);
    }
    
}

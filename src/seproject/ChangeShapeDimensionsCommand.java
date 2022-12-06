
package seproject;

import java.util.ArrayList;
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
        this.oldStartPoint = shape.getLowerBound();
        this.oldEndPoint = shape.getUpperBound();
    }
    
    @Override
    public void execute() throws GenericDrawException {
        if(shape == null)
            throw new ShapeNotSelectedDrawException();
        ArrayList<Point2D> points = new ArrayList<>();
        points.add(startPoint);
        points.add(endPoint);
        shape.changeDimensions(points);
    }

    @Override
    public void undo() {
        ArrayList<Point2D> points = new ArrayList<>();
        points.add(oldStartPoint);
        points.add(oldEndPoint);
        shape.changeDimensions(points);
    }
    
}

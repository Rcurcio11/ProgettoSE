
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
    private ArrayList<Point2D> points;
    private ShapeModel shape;  
    private Point2D oldStartPoint;
    private Point2D oldEndPoint;
    
    public ChangeShapeDimensionsCommand(AnchorPane drawingArea, ArrayList<Point2D> points, ShapeModel shape) {
        this.drawingArea = drawingArea;
        this.points = points;
        this.shape = shape;
        this.oldStartPoint = shape.getLowerBound();
        this.oldEndPoint = shape.getUpperBound();
    }
    
    @Override
    public void execute() throws GenericDrawException {
        if(shape == null)
            throw new ShapeNotSelectedDrawException();
        shape.changeDimensions(points);
    }

    @Override
    public void undo() {
        shape.changeDimensions(points);
    }
    
}

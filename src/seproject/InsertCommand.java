
package seproject;

import javafx.geometry.Point2D;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author Group14
 */
public class InsertCommand implements OperationCommand{
    private AnchorPane drawingArea;
    private ShapeModel selectedShape;
    private Point2D starPoint;
    private Point2D endPoint;

    public InsertCommand(AnchorPane drawingArea, ShapeModel selectedShape, Point2D starPoint, Point2D endPoint) {
        this.drawingArea = drawingArea;
        this.selectedShape = selectedShape;
        this.starPoint = starPoint;
        this.endPoint = endPoint;
    }

    @Override
    public void execute() {
        selectedShape.insert(drawingArea,starPoint, endPoint);
        
    }
}

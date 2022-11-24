
package seproject;

import javafx.geometry.Point2D;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;

/**
 *
 * @author Group14
 */
public class InsertCommand implements OperationCommand{
    private AnchorPane drawingArea;
    private ShapeModel selectedShape;
    private Point2D starPoint;
    private Point2D endPoint;
    private Color outlineColor;

    public InsertCommand(AnchorPane drawingArea, ShapeModel selectedShape, Point2D starPoint, Point2D endPoint, Color outlineColor) {
        this.drawingArea = drawingArea;
        this.selectedShape = selectedShape;
        this.starPoint = starPoint;
        this.endPoint = endPoint;
        this.outlineColor = outlineColor;
    }

    @Override
    public void execute() throws GenericDrawException{
        if(selectedShape == null)
            throw new ShapeNotSelectedDrawException();
        selectedShape.insert(drawingArea,starPoint, endPoint, outlineColor);
        
    }
}

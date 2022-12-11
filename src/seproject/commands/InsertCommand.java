
package seproject.commands;

import seproject.exceptions.GenericDrawException;
import seproject.shapes.ShapeModel;
import java.util.ArrayList;
import javafx.geometry.Point2D;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import seproject.exceptions.ShapeNotSelectedDrawException;

/**
 *
 * @author Group14
 */
public class InsertCommand implements OperationCommand{
    private AnchorPane drawingArea;
    private ShapeModel selectedShape;
    private ArrayList<Point2D> points = new ArrayList<>();
    private Color outlineColor;
    private Color fillingColor;

    public InsertCommand(AnchorPane drawingArea, ShapeModel selectedShape, ArrayList<Point2D> points, Color outlineColor, Color fillingColor) {
        this.drawingArea = drawingArea;
        this.selectedShape = selectedShape;
        this.points = points;
        this.outlineColor = outlineColor;
        this.fillingColor = fillingColor;
    }

    @Override
    public void execute() throws GenericDrawException{
        if(selectedShape == null)
            throw new ShapeNotSelectedDrawException();
        selectedShape.insert(drawingArea,points, outlineColor, fillingColor);
        
    }

    @Override
    public void undo() {
        drawingArea.getChildren().remove(selectedShape);
    }
}

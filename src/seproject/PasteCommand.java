
package seproject;

import javafx.geometry.Point2D;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author Group14
 */
public class PasteCommand implements OperationCommand{
    private AnchorPane drawingArea;
    private Point2D startPoint;
    private ShapeModel shapeToPaste;

    public PasteCommand(AnchorPane drawingArea, Point2D startPoint, ShapeModel shapeToPaste) {
        this.drawingArea = drawingArea;
        this.startPoint = startPoint;
        this.shapeToPaste = shapeToPaste;
    }

    @Override
    public void execute() throws GenericDrawException {
        shapeToPaste.pasteShape(drawingArea, startPoint);
    }

    @Override
    public void undo() {
        drawingArea.getChildren().remove(shapeToPaste);
    }
    
}

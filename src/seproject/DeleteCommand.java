
package seproject;

import javafx.scene.layout.AnchorPane;

/**
 *
 * @author Group14
 */
public class DeleteCommand implements OperationCommand{
    private AnchorPane drawingArea;
    private ShapeModel shapeToDelete;

    public DeleteCommand(AnchorPane drawingArea, ShapeModel shapeToDelete) {
        this.drawingArea = drawingArea;
        this.shapeToDelete = shapeToDelete;
    }
    
    @Override
    public void execute() throws GenericDrawException {
        shapeToDelete.deleteShape(drawingArea);
    }

    @Override
    public void undo() {
        shapeToDelete.insert(drawingArea, shapeToDelete.getStartPoint(), shapeToDelete.getEndPoint(), shapeToDelete.getOutlineColor(), shapeToDelete.getFillingColor());
    }
    
}

package seproject;

import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Shape;

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
    
}

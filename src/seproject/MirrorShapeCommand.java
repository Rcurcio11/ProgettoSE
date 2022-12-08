package seproject;

import java.util.ArrayList;
import javafx.geometry.Point2D;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author Gruop14
 */
public class MirrorShapeCommand implements OperationCommand{
    private AnchorPane drawingArea;
    private ShapeModel shape;

    public MirrorShapeCommand(AnchorPane drawingArea,ShapeModel shape) {
        this.drawingArea = drawingArea;
        this.shape = shape;
    }

    @Override
    public void execute() throws GenericDrawException {
        shape.mirrorShape();
    }

    @Override
    public void undo() {
        shape.mirrorShape();
    }
    
    
    
}

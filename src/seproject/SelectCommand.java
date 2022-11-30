
package seproject;

import java.util.List;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;


/**
 *
 * @author Gruppo14
 */
public class SelectCommand implements OperationCommand{
    
    private AnchorPane drawingArea;
    private Point2D selectedPoint;
    private List<Integer> index;

    public SelectCommand(AnchorPane drawingArea, Point2D selectedPoint, List<Integer> index) {
        this.drawingArea = drawingArea;
        this.selectedPoint = selectedPoint;
        this.index = index;
    }

    

    @Override
    public void execute() throws GenericDrawException {
        Node actualNode = null;
        
        for(int i = drawingArea.getChildren().size()-1; i>=0; i--){
            actualNode = drawingArea.getChildren().get(i);
            if(actualNode.contains(selectedPoint)){
                int j = index.set(0, i);
                break;
            }
        }
    }
    
}

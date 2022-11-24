
package seproject;


import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;



/**
*
* @author Group14
*/
public class LineModel extends ShapeModel {
    
    private Line line;

   public LineModel() {
        line = new Line();
    }

    @Override
    public void nextDraw() {
        line = new Line();
    }
    
   @Override
    public void insert(AnchorPane drawingPane, double prevX, double prevY, double lastX, double lastY) {
        line.setStartX(prevX);
        line.setStartY(prevY);
        line.setEndX(lastX);
        line.setEndY(lastY);

        drawingPane.getChildren().add(line);
    }
    
}
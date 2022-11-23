
package seproject;

import static java.lang.Math.abs;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;

/**
 *
 * @author Group14
 */
public class RectangleModel extends ShapeModel{
    private Rectangle rectangle;

    public RectangleModel() {
        rectangle = new Rectangle();
    }

    @Override
    public void nextDraw() {
        rectangle = new Rectangle();
    }

    @Override
    public void insert(AnchorPane drawingPane, double prevX, double prevY, double lastX, double lastY) {
        if(prevX>lastX && prevY>lastY){
            rectangle.setX(lastX);
            rectangle.setY(lastY);
            
        }else if(prevX>lastX){

            rectangle.setX(lastX);
            rectangle.setY(prevY);
            
        }else if(prevY>lastY){

            rectangle.setX(prevX);
            rectangle.setY(lastY);

        }else{

            rectangle.setX(prevX);
            rectangle.setY(prevY);

        }
        double width = abs(lastX-prevX);
        double height = abs(lastY- prevY);
        
        rectangle.setWidth(width);
        rectangle.setHeight(height);
        
        drawingPane.getChildren().add(rectangle);
    }
}

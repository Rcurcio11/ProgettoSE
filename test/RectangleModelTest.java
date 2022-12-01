
import javafx.geometry.Point2D;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import org.junit.Test;
import static org.junit.Assert.*;
import seproject.LineModel;
import seproject.RectangleModel;

/**
 *
 * @author Group14
 */
public class RectangleModelTest {
    private RectangleModel testShapeRectangle;
    
    public RectangleModelTest() {
        testShapeRectangle = new RectangleModel();
        
    }

    @Test
    public void testInsert() {
       AnchorPane testPane = new AnchorPane();     
       double startX = Math.random()*663;
       double startY = Math.random()*479;
       double endX = Math.random()*663; 
       double endY = Math.random()*479;
       Point2D startPoint = new Point2D(startX,startY);
       Point2D endPoint = new Point2D(endX,endY);
       Color outlineColor = Color.color(Math.random(), Math.random(), Math.random());
       Color fillingColor = Color.color(Math.random(), Math.random(), Math.random());
       
       testShapeRectangle.insert(testPane, startPoint, endPoint, outlineColor, fillingColor);
       
       assertEquals(1, testPane.getChildren().size());
       assertEquals(RectangleModel.class, testPane.getChildren().get(0).getClass());
    }  
    
    @Test
    public void testMove(){
        
        AnchorPane testPane = new AnchorPane();     
        double startX = Math.random()*663;
        double startY = Math.random()*479;
        double endX = Math.random()*663; 
        double endY = Math.random()*479;
        Point2D startPoint = new Point2D(startX,startY);
        Point2D endPoint = new Point2D(endX,endY);
        Color outlineColor = Color.color(Math.random(), Math.random(), Math.random());
        Color fillingColor = Color.color(Math.random(), Math.random(), Math.random());

        testShapeRectangle.insert(testPane, startPoint, endPoint, outlineColor, fillingColor);
        
        RectangleModel actualShape = (RectangleModel) testPane.getChildren().get(0);
        
        testShapeRectangle.setTranslateX(Math.random()*663);
        testShapeRectangle.setTranslateX(Math.random()*479);
        
        
        testShapeRectangle.move();
        
        assertEquals(testShapeRectangle, testPane.getChildren().get(0));
        
        assertEquals(actualShape.getTranslateX() + actualShape.getX() , testShapeRectangle.getX(), 0.1);
        assertEquals(actualShape.getY() + actualShape.getTranslateY() , testShapeRectangle.getY(), 0.1);
    }
}



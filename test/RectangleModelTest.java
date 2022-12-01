
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
    private AnchorPane testDrawingArea;
    
    public RectangleModelTest() {
       testShapeRectangle = new RectangleModel();
       testDrawingArea = new AnchorPane();     
       double startX = Math.random()*663;
       double startY = Math.random()*479;
       double endX = Math.random()*663; 
       double endY = Math.random()*479;
       Point2D startPoint = new Point2D(startX,startY);
       Point2D endPoint = new Point2D(endX,endY);
       Color outlineColor = Color.color(Math.random(), Math.random(), Math.random());
       Color fillingColor = Color.color(Math.random(), Math.random(), Math.random());
       
       testShapeRectangle.insert(testDrawingArea, startPoint, endPoint, outlineColor, fillingColor);
        
    }

    @Test
    public void testInsert() {
       
       assertEquals(1, testDrawingArea.getChildren().size());
       assertEquals(RectangleModel.class, testDrawingArea.getChildren().get(0).getClass());
    }  
    
    @Test
    public void testMove(){
        
        RectangleModel actualShape = (RectangleModel) testDrawingArea.getChildren().get(0);
        
        testShapeRectangle.setTranslateX(Math.random()*663);
        testShapeRectangle.setTranslateX(Math.random()*479);
        
        
        testShapeRectangle.move();
        
        assertEquals(testShapeRectangle, testDrawingArea.getChildren().get(0));
        
        assertEquals(actualShape.getTranslateX() + actualShape.getX() , testShapeRectangle.getX(), 0.1);
        assertEquals(actualShape.getY() + actualShape.getTranslateY() , testShapeRectangle.getY(), 0.1);
    }
    
    @Test
    public void testDeleteShape(){
        testShapeRectangle.deleteShape(testDrawingArea);

        assertEquals(0, testDrawingArea.getChildren().size());
    }
}



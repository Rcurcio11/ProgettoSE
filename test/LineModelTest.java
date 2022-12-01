
import javafx.geometry.Point2D;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import seproject.LineModel;


/**
 *
 * @author Group14
 */
public class LineModelTest {
    private LineModel testShapeLine;
    private AnchorPane testDrawingArea;
    
    @Before
    public void setup(){
        testShapeLine = new LineModel();
        testDrawingArea = new AnchorPane();
        double startX = Math.random()*663;
        double startY = Math.random()*479;
        double endX = Math.random()*663; 
        double endY = Math.random()*479;
        Point2D startPoint = new Point2D(startX,startY);
        Point2D endPoint = new Point2D(endX,endY);
        
        Color outlineColor = Color.color(Math.random(), Math.random(), Math.random());
        Color fillingColor = Color.color(Math.random(), Math.random(), Math.random());
        
        testShapeLine.insert(testDrawingArea, startPoint, endPoint, outlineColor, fillingColor);
    }
    
    public LineModelTest() {
    }

    @Test
    public void testInsert(){    
        
        assertEquals(1,testDrawingArea.getChildren().size());
        assertEquals(LineModel.class,testDrawingArea.getChildren().get(0).getClass());
    }
    
    @Test
    public void testMove(){
        
        LineModel actualShape = (LineModel) testDrawingArea.getChildren().get(0);
        
        testShapeLine.setTranslateX(Math.random()*663);
        testShapeLine.setTranslateX(Math.random()*479);
        
        
        testShapeLine.move();
        
        assertEquals(testShapeLine, testDrawingArea.getChildren().get(0));
        
        assertEquals(actualShape.getTranslateX() + actualShape.getStartX() , testShapeLine.getStartX(), 0.1);
        assertEquals(actualShape.getStartY() + actualShape.getTranslateY() , testShapeLine.getStartY(), 0.1);
        
    }
    
    @Test
    public void testDeleteShape(){
        testShapeLine.deleteShape(testDrawingArea);

        assertEquals(0, testDrawingArea.getChildren().size());
    }
    
    @Test
    public void testPasteShape(){
        double newStartX = Math.random()*663;
        double newStartY = Math.random()*479;
        
       Point2D newStart = new Point2D(newStartX, newStartY);
       testShapeLine.pasteShape(testDrawingArea, newStart);
       
       LineModel actualLine = (LineModel)testDrawingArea.getChildren().get(1);
       assertEquals(newStartX, actualLine.getStartX(), 0.1);
       assertEquals(newStartY, actualLine.getStartY(), 0.1);
    }
    
    @Test 
    public void testChangeColor(){
        testShapeLine.changeColor(testShapeLine, Color.ALICEBLUE, Color.GAINSBORO);
        assertEquals(Color.GAINSBORO, testShapeLine.getFillingColor());
        assertEquals(Color.ALICEBLUE, testShapeLine.getOutlineColor());
    }


}

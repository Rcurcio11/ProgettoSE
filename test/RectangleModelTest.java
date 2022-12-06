
import java.util.ArrayList;
import javafx.geometry.Point2D;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import seproject.RectangleModel;

/**
 *
 * @author Group14
 */
public class RectangleModelTest {
    private RectangleModel testShapeRectangle;
    private AnchorPane testDrawingArea;
    
    @Before
    public void setup(){
        testShapeRectangle = new RectangleModel();
        testDrawingArea = new AnchorPane();     
        ArrayList<Point2D> points = new ArrayList<>();
        double startX = Math.random()*663;
        double startY = Math.random()*479;
        double endX = Math.random()*663; 
        double endY = Math.random()*479;
        points.add(new Point2D(startX,startY));
        points.add(new Point2D(endX,endY));
        Color outlineColor = Color.color(Math.random(), Math.random(), Math.random());
        Color fillingColor = Color.color(Math.random(), Math.random(), Math.random());
       
        testShapeRectangle.insert(testDrawingArea, points, outlineColor, fillingColor);

    }
    
    public RectangleModelTest() {
    }

    @Test
    public void testInsert() {
       
       assertEquals(1, testDrawingArea.getChildren().size());
       assertEquals(RectangleModel.class, testDrawingArea.getChildren().get(0).getClass());
    }  
    
    @Test
    public void testMove(){
        
        Point2D translatePoint= new Point2D(12,34);
        Point2D startPoint = testShapeRectangle.getLowerBound();
        
        testShapeRectangle.move(translatePoint);
        
        assertEquals(testShapeRectangle, testDrawingArea.getChildren().get(0));
        assertEquals(startPoint.getX() + translatePoint.getX() , testShapeRectangle.getLowerBound().getX(),0.1);
        assertEquals(startPoint.getY() + translatePoint.getY() , testShapeRectangle.getLowerBound().getY(), 0.1);
        
    }
    
    @Test
    public void testDeleteShape(){
        testShapeRectangle.deleteShape(testDrawingArea);

        assertEquals(0, testDrawingArea.getChildren().size());
    }
    
    @Test
    public void testPasteShape(){
        double newStartX = Math.random()*663;
        double newStartY = Math.random()*479;
        
       Point2D newStart = new Point2D(newStartX, newStartY);
       testShapeRectangle.pasteShape(testDrawingArea, newStart);
       
       RectangleModel actualLine = (RectangleModel)testDrawingArea.getChildren().get(1);
       assertEquals(newStartX, actualLine.getX(), 0.1);
       assertEquals(newStartY, actualLine.getY(), 0.1);
    }
    
    @Test 
    public void testChangeColor(){
        testShapeRectangle.changeColor(Color.ALICEBLUE, Color.GAINSBORO);
        assertEquals(Color.GAINSBORO, testShapeRectangle.getFillingColor());
        assertEquals(Color.ALICEBLUE, testShapeRectangle.getOutlineColor());
    }
    
    @Test
    public void testChangeDimensions(){
        Point2D oldEndPoint = testShapeRectangle.getUpperBound();
        Point2D oldStartPoint = testShapeRectangle.getLowerBound();
        Point2D newEndPoint = new Point2D(100,100);
        Point2D newStartPoint = new Point2D(20,15);
        ArrayList<Point2D> testPoints = new ArrayList<>();
        
        testPoints.add(oldStartPoint);
        testPoints.add(oldEndPoint);
        
        testShapeRectangle.changeDimensions(testPoints);
        assertEquals(oldEndPoint,testShapeRectangle.getUpperBound());
        
        testPoints.set(0, newStartPoint);
        testPoints.set(1, newEndPoint);
        
        testShapeRectangle.changeDimensions(testPoints);
        assertEquals(newStartPoint,testShapeRectangle.getLowerBound());
        assertEquals(newEndPoint,testShapeRectangle.getUpperBound());
        
        testPoints.set(1, oldEndPoint);
        
        testShapeRectangle.changeDimensions(testPoints);
        assertEquals(newStartPoint,testShapeRectangle.getLowerBound());
    }
}



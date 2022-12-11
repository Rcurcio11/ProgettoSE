
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
        double startX = Math.random()*2000;
        double startY = Math.random()*2000;
        double endX = Math.random()*2000; 
        double endY = Math.random()*2000;
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
        
        Point2D translatePoint= new Point2D(Math.random()*2000,Math.random()*2000);
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
        double newStartX = Math.random()*2000;
        double newStartY = Math.random()*2000;
        
       Point2D newStart = new Point2D(newStartX, newStartY);
       testShapeRectangle.pasteShape(testDrawingArea, newStart);
       
       RectangleModel actualLine = (RectangleModel)testDrawingArea.getChildren().get(1);
       assertEquals(newStartX, actualLine.getX(), 0.1);
       assertEquals(newStartY, actualLine.getY(), 0.1);
    }
    
      @Test 
    public void testChangeOutlineColor(){
        Color testOutline = Color.color(Math.random(), Math.random(), Math.random());
        testShapeRectangle.changeOutlineColor(testOutline);
        assertEquals(testOutline, testShapeRectangle.getOutlineColor());
    }
    
    @Test 
    public void testChangeFillingColor(){
        Color testFilling = Color.color(Math.random(), Math.random(), Math.random());
        testShapeRectangle.changeFillingColor(testFilling);
        assertEquals(testFilling, testShapeRectangle.getFillingColor());
    }
    
    @Test
    public void testChangeDimensions(){
        Point2D oldEndPoint = testShapeRectangle.getLowerBound();
        Point2D oldStartPoint = testShapeRectangle.getUpperBound();
        Point2D newEndPoint = new Point2D(oldEndPoint.getX() + Math.random()*10,oldEndPoint.getY() + Math.random()*10);
        Point2D newStartPoint = new Point2D(oldStartPoint.getX() + Math.random()*10,oldStartPoint.getY() + Math.random()*10);
        ArrayList<Point2D> testPoints = new ArrayList<>();
        
        testPoints.add(oldStartPoint);
        testPoints.add(oldEndPoint);
        
        testShapeRectangle.changeDimensions(testPoints);
        
        assertEquals(oldEndPoint.getX(),testShapeRectangle.getLowerBound().getX(),0.1);
        assertEquals(oldEndPoint.getY(),testShapeRectangle.getLowerBound().getY(),0.1);
        assertEquals(oldStartPoint.getX(),testShapeRectangle.getUpperBound().getX(),0.1);
        assertEquals(oldStartPoint.getY(),testShapeRectangle.getUpperBound().getY(),0.1);
        
        testPoints.set(0, newStartPoint);
        testPoints.set(1, newEndPoint);
        
        testShapeRectangle.changeDimensions(testPoints);
        
        assertEquals(newEndPoint.getX(),testShapeRectangle.getLowerBound().getX(),0.1);
        assertEquals(newEndPoint.getY(),testShapeRectangle.getLowerBound().getY(),0.1);
        assertEquals(newStartPoint.getX(),testShapeRectangle.getUpperBound().getX(),0.1);
        assertEquals(newStartPoint.getY(),testShapeRectangle.getUpperBound().getY(),0.1);
        
        testPoints.set(0, oldEndPoint);
        testPoints.set(1, oldStartPoint);
        
        testShapeRectangle.changeDimensions(testPoints);
        
        assertEquals(oldEndPoint.getX(),testShapeRectangle.getLowerBound().getX(),0.1);
        assertEquals(oldEndPoint.getY(),testShapeRectangle.getLowerBound().getY(),0.1);
        assertEquals(oldStartPoint.getX(),testShapeRectangle.getUpperBound().getX(),0.1);
        assertEquals(oldStartPoint.getY(),testShapeRectangle.getUpperBound().getY(),0.1);
    }
    
    @Test 
    public void testMirrorShape(){
        Point2D startPoint = testShapeRectangle.getUpperBound();
        Point2D endPoint = testShapeRectangle.getLowerBound();
        testShapeRectangle.mirrorShape();
        
        assertEquals(startPoint.getX(),testShapeRectangle.getUpperBound().getX(),0.1);
        assertEquals(endPoint.getX(),testShapeRectangle.getLowerBound().getX(),0.1);
        assertEquals(startPoint.getY(),testShapeRectangle.getUpperBound().getY(),0.1);
        assertEquals(endPoint.getY(),testShapeRectangle.getLowerBound().getY(),0.1);   
    }
    
    @Test
    public void testStretchVertical(){
        Point2D startPoint = testShapeRectangle.getUpperBound();
        Point2D endPoint = testShapeRectangle.getLowerBound();
        
        double height = endPoint.getY() - startPoint.getY();
        testShapeRectangle.stretchVertical(-2*height);
        assertEquals(startPoint,testShapeRectangle.getUpperBound());
        assertEquals(endPoint,testShapeRectangle.getLowerBound());
        
        
        testShapeRectangle.stretchVertical(height/2);
        assertEquals(startPoint.getY() - height/4,testShapeRectangle.getUpperBound().getY(),0.1);
        assertEquals(endPoint.getY() + height/4,testShapeRectangle.getLowerBound().getY(),0.1);
        assertEquals(startPoint.getX(),testShapeRectangle.getUpperBound().getX(),0);
        assertEquals(endPoint.getX(),testShapeRectangle.getLowerBound().getX(),0);
        
    }
    
    @Test
    public void testStretchHorizontal(){
        Point2D startPoint = testShapeRectangle.getUpperBound();
        Point2D endPoint = testShapeRectangle.getLowerBound();
        
        double width = endPoint.getX() - startPoint.getX();
        testShapeRectangle.stretchHorizontal(-2*width);
        assertEquals(startPoint,testShapeRectangle.getUpperBound());
        assertEquals(endPoint,testShapeRectangle.getLowerBound());
        
        
        testShapeRectangle.stretchHorizontal(width/2);
        assertEquals(startPoint.getX() - width/4,testShapeRectangle.getUpperBound().getX(),0.1);
        assertEquals(endPoint.getX() + width/4,testShapeRectangle.getLowerBound().getX(),0.1);
        assertEquals(startPoint.getY(),testShapeRectangle.getUpperBound().getY(),0);
        assertEquals(endPoint.getY(),testShapeRectangle.getLowerBound().getY(),0);
    }
    
    @Test
    public void testRotate(){
        double deg = 10;
        testShapeRectangle.rotate(deg);
        
        assertEquals(deg,testShapeRectangle.getRotation(),0.01);
       
        testShapeRectangle.rotate(-deg);
        assertEquals(0,testShapeRectangle.getRotation(),0.01);
    }
    
    
}



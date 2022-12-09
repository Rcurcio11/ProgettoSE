
import java.util.ArrayList;
import javafx.geometry.Point2D;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
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
        ArrayList<Point2D> points = new ArrayList<>();
        double startX = Math.random()*2000;
        double startY = Math.random()*2000;
        double endX = Math.random()*2000; 
        double endY = Math.random()*2000;
        points.add(new Point2D(startX,startY));
        points.add(new Point2D(endX,endY));
        
        Color outlineColor = Color.color(Math.random(), Math.random(), Math.random());
        Color fillingColor = Color.color(Math.random(), Math.random(), Math.random());
        
        testShapeLine.insert(testDrawingArea, points, outlineColor, fillingColor);
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
        
        Point2D translatePoint= new Point2D(Math.random()*2000,Math.random()*2000);
        Point2D startPoint = testShapeLine.getLowerBound();
        
        testShapeLine.move(translatePoint);
        
        assertEquals(testShapeLine, testDrawingArea.getChildren().get(0));
        assertEquals(startPoint.getX() + translatePoint.getX() , testShapeLine.getLowerBound().getX(),0.1);
        assertEquals(startPoint.getY() + translatePoint.getY() , testShapeLine.getLowerBound().getY(), 0.1);
        
    }
    
    @Test
    public void testDeleteShape(){
        testShapeLine.deleteShape(testDrawingArea);

        assertEquals(0, testDrawingArea.getChildren().size());
    }
    
    @Test
    public void testPasteShape(){
        double newStartX = Math.random()*Math.random()*2000;
        double newStartY = Math.random()*Math.random()*2000;
        
       Point2D newStart = new Point2D(newStartX, newStartY);
       testShapeLine.pasteShape(testDrawingArea, newStart);
       
       LineModel actualLine = (LineModel)testDrawingArea.getChildren().get(1);
       assertEquals(newStartX, actualLine.getStartX(), 0.1);
       assertEquals(newStartY, actualLine.getStartY(), 0.1);
    }
    
    @Test 
    public void testChangeOutlineColor(){
        Color testOutline = Color.color(Math.random(), Math.random(), Math.random());
        testShapeLine.changeOutlineColor(testOutline);
        assertEquals(testOutline, testShapeLine.getOutlineColor());
    }
    

    
    @Test public void testChangeDimensions(){
        Point2D oldEndPoint = testShapeLine.getUpperBound();
        Point2D oldStartPoint = testShapeLine.getLowerBound();
        Point2D newEndPoint = new Point2D(Math.random()*2000,Math.random()*2000);
        Point2D newStartPoint = new Point2D(Math.random()*2000,Math.random()*2000);
        
        ArrayList<Point2D> testPoints = new ArrayList<>();
        
        testPoints.add(oldStartPoint);
        testPoints.add(oldEndPoint);
        
        testShapeLine.changeDimensions(testPoints);
        assertEquals(oldEndPoint,testShapeLine.getUpperBound());
        
        testPoints.set(0, newStartPoint);
        testPoints.set(1, newEndPoint);
        
        testShapeLine.changeDimensions(testPoints);
        assertEquals(newStartPoint,testShapeLine.getLowerBound());
        assertEquals(newEndPoint,testShapeLine.getUpperBound());
        
        testPoints.set(1, oldEndPoint);
        
        testShapeLine.changeDimensions(testPoints);
        assertEquals(newStartPoint,testShapeLine.getLowerBound());
    }

    
    @Test 
    public void testMirrorShape(){
        Point2D startPoint = testShapeLine.getUpperBound();
        Point2D endPoint = testShapeLine.getLowerBound();
        System.out.println("b " + startPoint + " " + endPoint);
        testShapeLine.mirrorShape();
        System.out.println("a " + startPoint + " " + endPoint);
        assertEquals(startPoint.getX(),testShapeLine.getUpperBound().getX(),0.1);
        assertEquals(endPoint.getX(),testShapeLine.getLowerBound().getX(),0.1);
        assertEquals(startPoint.getY(),testShapeLine.getLowerBound().getY(),0.1);
        assertEquals(endPoint.getY(),testShapeLine.getUpperBound().getY(),0.1);
        
    }
    
    @Test
    public void testStretchVertical(){
        Point2D startPoint = testShapeLine.getUpperBound();
        Point2D endPoint = testShapeLine.getLowerBound();
        
        double height = endPoint.getY() - startPoint.getY();
        testShapeLine.stretchVertical(-height);
        assertEquals(startPoint,testShapeLine.getUpperBound());
        assertEquals(endPoint,testShapeLine.getLowerBound());
        
        
        testShapeLine.stretchVertical(height/2);
        assertEquals(startPoint.getY() - height/4,testShapeLine.getUpperBound().getY(),0.1);
        assertEquals(endPoint.getY() + height/4,testShapeLine.getLowerBound().getY(),0.1);
        assertEquals(startPoint.getX(),testShapeLine.getUpperBound().getX(),0);
        assertEquals(endPoint.getX(),testShapeLine.getLowerBound().getX(),0);
        
    }
    
    @Test
    public void testStretchHorizontal(){
        Point2D startPoint = testShapeLine.getUpperBound();
        Point2D endPoint = testShapeLine.getLowerBound();
        
        double width = endPoint.getX() - startPoint.getX();
        testShapeLine.stretchHorizontal(-width);
        assertEquals(startPoint,testShapeLine.getUpperBound());
        assertEquals(endPoint,testShapeLine.getLowerBound());
        
        
        testShapeLine.stretchHorizontal(width/2);
        assertEquals(startPoint.getX() - width/4,testShapeLine.getUpperBound().getX(),0.1);
        assertEquals(endPoint.getX() + width/4,testShapeLine.getLowerBound().getX(),0.1);
        assertEquals(startPoint.getY(),testShapeLine.getUpperBound().getY(),0);
        assertEquals(endPoint.getY(),testShapeLine.getLowerBound().getY(),0);
    }

}

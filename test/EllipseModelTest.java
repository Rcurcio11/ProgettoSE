

import java.util.ArrayList;
import javafx.geometry.Point2D;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import seproject.EllipseModel;

/**
 *
 * @author Group14
 */
public class EllipseModelTest {
    private EllipseModel testShapeEllipse;
    private AnchorPane testDrawingArea;
    
    @Before
    public void setup(){
        testShapeEllipse = new EllipseModel();
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
        
        testShapeEllipse.insert(testDrawingArea, points, outlineColor, fillingColor);
    }
    public EllipseModelTest() {
    }

    @Test
    public void testInsert() {
        assertEquals(1,testDrawingArea.getChildren().size());
        assertEquals(EllipseModel.class,testDrawingArea.getChildren().get(0).getClass());
    }
    
    @Test
    public void testMove() {   
        Point2D translatePoint= new Point2D(Math.random()*2000,Math.random()*2000);
        Point2D startPoint = testShapeEllipse.getLowerBound();
        
        testShapeEllipse.move(translatePoint);
        
        assertEquals(testShapeEllipse, testDrawingArea.getChildren().get(0));
        assertEquals(startPoint.getX() + translatePoint.getX() , testShapeEllipse.getLowerBound().getX(),0.1);
        assertEquals(startPoint.getY() + translatePoint.getY() , testShapeEllipse.getLowerBound().getY(), 0.1);
        
    }
    
    @Test
    public void testDeleteShape(){
        testShapeEllipse.deleteShape(testDrawingArea);

        assertEquals(0, testDrawingArea.getChildren().size());
    }
    
    @Test
    public void testPasteShape(){
       double newStartX = Math.random()*2000;
       double newStartY = Math.random()*2000;
        
       Point2D newStart = new Point2D(newStartX, newStartY);
       testShapeEllipse.pasteShape(testDrawingArea, newStart);
       
       EllipseModel actualLine = (EllipseModel)testDrawingArea.getChildren().get(1);
       assertEquals(newStartX, actualLine.getUpperBound().getX(), 0.1);
       assertEquals(newStartY, actualLine.getUpperBound().getY(), 0.1);
    }
    
    @Test 
    public void testChangeOutlineColor(){
        Color testOutline = Color.color(Math.random(), Math.random(), Math.random());
        testShapeEllipse.changeOutlineColor(testOutline);
        assertEquals(testOutline, testShapeEllipse.getOutlineColor());
    }
    
    @Test 
    public void testChangeFillingColor(){
        Color testFilling = Color.color(Math.random(), Math.random(), Math.random());
        testShapeEllipse.changeFillingColor(testFilling);
        assertEquals(testFilling, testShapeEllipse.getFillingColor());
    }
    
    @Test
    public void testChangeDimensions(){
        Point2D oldEndPoint = testShapeEllipse.getLowerBound();
        Point2D oldStartPoint = testShapeEllipse.getUpperBound();
        Point2D newEndPoint = new Point2D(oldEndPoint.getX() + Math.random()*10,oldEndPoint.getY() + Math.random()*10);
        Point2D newStartPoint = new Point2D(oldStartPoint.getX() + Math.random()*10,oldStartPoint.getY() + Math.random()*10);
        ArrayList<Point2D> testPoints = new ArrayList<>();
        
        testPoints.add(oldStartPoint);
        testPoints.add(oldEndPoint);
        
        testShapeEllipse.changeDimensions(testPoints);
        assertEquals(oldEndPoint,testShapeEllipse.getLowerBound());
        
        testPoints.set(0, newStartPoint);
        testPoints.set(1, newEndPoint);
        
        testShapeEllipse.changeDimensions(testPoints);
        assertEquals(newStartPoint,testShapeEllipse.getUpperBound());
        assertEquals(newEndPoint,testShapeEllipse.getLowerBound());
        
        testPoints.set(1, oldEndPoint);
        
        testShapeEllipse.changeDimensions(testPoints);
        assertEquals(newStartPoint,testShapeEllipse.getUpperBound());
        
    }
    
    @Test 
    public void testMirrorShape(){
        Point2D startPoint = testShapeEllipse.getUpperBound();
        Point2D endPoint = testShapeEllipse.getLowerBound();
        testShapeEllipse.mirrorShape();
        
        assertEquals(startPoint.getX(),testShapeEllipse.getUpperBound().getX(),0.1);
        assertEquals(startPoint.getY(),testShapeEllipse.getUpperBound().getY(),0.1);
        assertEquals(endPoint.getX(),testShapeEllipse.getLowerBound().getX(),0.1);
        assertEquals(endPoint.getY(),testShapeEllipse.getLowerBound().getY(),0.1);
        
    }
    
    @Test
    public void testStretchVertical(){
        Point2D startPoint = testShapeEllipse.getUpperBound();
        Point2D endPoint = testShapeEllipse.getLowerBound();
        
        double height = endPoint.getY() - startPoint.getY();
        testShapeEllipse.stretchVertical(-2*height);
        assertEquals(startPoint,testShapeEllipse.getUpperBound());
        assertEquals(endPoint,testShapeEllipse.getLowerBound());
        
        
        testShapeEllipse.stretchVertical(height/2);
        assertEquals(startPoint.getY() - height/4,testShapeEllipse.getUpperBound().getY(),0.1);
        assertEquals(endPoint.getY() + height/4,testShapeEllipse.getLowerBound().getY(),0.1);
        assertEquals(startPoint.getX(),testShapeEllipse.getUpperBound().getX(),0);
        assertEquals(endPoint.getX(),testShapeEllipse.getLowerBound().getX(),0);
        
    }
    
    @Test
    public void testStretchHorizontal(){
        Point2D startPoint = testShapeEllipse.getUpperBound();
        Point2D endPoint = testShapeEllipse.getLowerBound();
        
        double width = endPoint.getX() - startPoint.getX();
        testShapeEllipse.stretchHorizontal(-2*width);
        assertEquals(startPoint,testShapeEllipse.getUpperBound());
        assertEquals(endPoint,testShapeEllipse.getLowerBound());
        
        
        testShapeEllipse.stretchHorizontal(width/2);
        assertEquals(startPoint.getX() - width/4,testShapeEllipse.getUpperBound().getX(),0.1);
        assertEquals(endPoint.getX() + width/4,testShapeEllipse.getLowerBound().getX(),0.1);
        assertEquals(startPoint.getY(),testShapeEllipse.getUpperBound().getY(),0);
        assertEquals(endPoint.getY(),testShapeEllipse.getLowerBound().getY(),0);
    }

}

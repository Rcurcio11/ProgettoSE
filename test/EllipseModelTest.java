

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
        double startX = 50.0;
        double startY = 50.0;
        double endX = 60.0; 
        double endY = 60.0;
        Point2D startPoint = new Point2D(startX,startY);
        Point2D endPoint = new Point2D(endX,endY);
        Color outlineColor = Color.color(Math.random(), Math.random(), Math.random());
        Color fillingColor = Color.color(Math.random(), Math.random(), Math.random());
        
        testShapeEllipse.insert(testDrawingArea, startPoint, endPoint, outlineColor, fillingColor);
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
        Point2D translatePoint= new Point2D(12,34);
        Point2D startPoint = testShapeEllipse.getStartPoint();
        
        testShapeEllipse.move(translatePoint);
        
        assertEquals(testShapeEllipse, testDrawingArea.getChildren().get(0));
        assertEquals(startPoint.getX() + translatePoint.getX() , testShapeEllipse.getStartPoint().getX(),0.1);
        assertEquals(startPoint.getY() + translatePoint.getY() , testShapeEllipse.getStartPoint().getY(), 0.1);
        
    }
    
    @Test
    public void testDeleteShape(){
        testShapeEllipse.deleteShape(testDrawingArea);

        assertEquals(0, testDrawingArea.getChildren().size());
    }
    
    @Test
    public void testPasteShape(){
       double newStartX = Math.random()*663;
       double newStartY = Math.random()*479;
        
       Point2D newStart = new Point2D(newStartX, newStartY);
       testShapeEllipse.pasteShape(testDrawingArea, newStart);
       
       EllipseModel actualLine = (EllipseModel)testDrawingArea.getChildren().get(1);
       assertEquals(newStartX, actualLine.getStartPoint().getX(), 0.1);
       assertEquals(newStartY, actualLine.getStartPoint().getY(), 0.1);
    }
    
    @Test 
    public void testChangeColor(){
        testShapeEllipse.changeColor(Color.ALICEBLUE, Color.GAINSBORO);
        assertEquals(Color.GAINSBORO, testShapeEllipse.getFillingColor());
        assertEquals(Color.ALICEBLUE, testShapeEllipse.getOutlineColor());
    }
    
    @Test
    public void testChangeDimensions(){
        Point2D oldEndPoint = testShapeEllipse.getEndPoint();
        Point2D oldStartPoint = testShapeEllipse.getStartPoint();
        Point2D newEndPoint = new Point2D(100,100);
        Point2D newStartPoint = new Point2D(20,15);
        
        testShapeEllipse.changeDimensions(oldStartPoint, oldEndPoint);
        assertEquals(oldEndPoint,testShapeEllipse.getEndPoint());
        
        testShapeEllipse.changeDimensions(newEndPoint, newStartPoint);
        assertEquals(newStartPoint,testShapeEllipse.getEndPoint());
        assertEquals(newEndPoint,testShapeEllipse.getStartPoint());
        
        testShapeEllipse.changeDimensions(newStartPoint, oldEndPoint);
        assertEquals(newStartPoint,testShapeEllipse.getStartPoint());
        
    }
    

}

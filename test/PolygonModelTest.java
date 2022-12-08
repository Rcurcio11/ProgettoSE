
import java.util.ArrayList;
import javafx.geometry.Point2D;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import seproject.PolygonModel;

/**
 *
 * @author Group14
 */
public class PolygonModelTest {
    private PolygonModel testShapePolygon;
    private AnchorPane testDrawingArea;
    
    @Before
    public void setup(){
        testShapePolygon = new PolygonModel();
        testDrawingArea = new AnchorPane();     
        
        ArrayList<Point2D> points = new ArrayList<>();
        double sidesNumber = Math.random()*10 +2;
        double pointX = Math.random()*653;
        double pointY = Math.random()*469;
        Point2D startendPoint = new Point2D(pointX, pointY);
        points.add(startendPoint);
        for(int i = 1; i<sidesNumber; i++){
            pointX = Math.random()*653;
            pointY = Math.random()*469;
            points.add(new Point2D(pointX, pointY));
        }
        points.add(startendPoint);
        Color outlineColor = Color.color(Math.random(), Math.random(), Math.random());
        Color fillingColor = Color.color(Math.random(), Math.random(), Math.random());
       
        testShapePolygon.insert(testDrawingArea, points, outlineColor, fillingColor);

    }
    
    public PolygonModelTest() {
    }
    
    @Test
    public void testInsert() {
       
       assertEquals(1, testDrawingArea.getChildren().size());
       assertEquals(PolygonModel.class, testDrawingArea.getChildren().get(0).getClass());
    } 
    
    @Test
    public void testMove(){
        
        Point2D translatePoint= new Point2D(12,34);
        Point2D startPoint = testShapePolygon.getAllPoints().get(0);
        
        testShapePolygon.move(translatePoint);
        
        assertEquals(testShapePolygon, testDrawingArea.getChildren().get(0));
        assertEquals(startPoint.getX() + translatePoint.getX() , testShapePolygon.getAllPoints().get(0).getX(),0.1);
        assertEquals(startPoint.getY() + translatePoint.getY() , testShapePolygon.getAllPoints().get(0).getY(), 0.1);
        
    }
    
    @Test
    public void testDeleteShape(){
        testShapePolygon.deleteShape(testDrawingArea);

        assertEquals(0, testDrawingArea.getChildren().size());
    }
    
    @Test
    public void testPasteShape(){
        double newStartX = Math.random()*653;
        double newStartY = Math.random()*469;
        
       Point2D newStart = new Point2D(newStartX, newStartY);
       testShapePolygon.pasteShape(testDrawingArea, newStart);
       
       PolygonModel actualPoly = (PolygonModel)testDrawingArea.getChildren().get(1);
       assertEquals(newStartX, actualPoly.getPoints().get(0), 0.1);
       assertEquals(newStartY, actualPoly.getPoints().get(1), 0.1);
    }
    
    @Test 
    public void testChangeColor(){
        testShapePolygon.changeColor(Color.ALICEBLUE, Color.GAINSBORO);
        assertEquals(Color.GAINSBORO, testShapePolygon.getFillingColor());
        assertEquals(Color.ALICEBLUE, testShapePolygon.getOutlineColor());
    }
    
    @Test
    public void testChangeDimensions(){
        Point2D oldStartPoint = testShapePolygon.getBounds().get(0);
        Point2D oldEndPoint = testShapePolygon.getBounds().get(1);
        Point2D newStartPoint = new Point2D(oldStartPoint.getX()+ Math.random()*10,oldStartPoint.getY() + Math.random()*10);
        Point2D newEndPoint = new Point2D(oldEndPoint.getX() + Math.random()*10, oldEndPoint.getY() + Math.random()*10);
        ArrayList<Point2D> points = new ArrayList<>();
        points.add(0,newStartPoint);
        points.add(1,newEndPoint);
        testShapePolygon.changeDimensions(points);
        
        assertEquals(newStartPoint.getX(),testShapePolygon.getBounds().get(0).getX(),0.1);
        assertEquals(newEndPoint.getX(),testShapePolygon.getBounds().get(1).getX(),0.1);
        assertEquals(newStartPoint.getY(),testShapePolygon.getBounds().get(0).getY(),0.1);
        assertEquals(newEndPoint.getY(),testShapePolygon.getBounds().get(1).getY(),0.1);
        
        points.clear();
        points.add(0,newStartPoint);
        points.add(1,oldStartPoint);
        testShapePolygon.changeDimensions(points);
        assertEquals(oldStartPoint.getX(),testShapePolygon.getBounds().get(0).getX(),0.1);
        assertEquals(newStartPoint.getX(),testShapePolygon.getBounds().get(1).getX(),0.1);
        assertEquals(oldStartPoint.getY(),testShapePolygon.getBounds().get(0).getY(),0.1);
        assertEquals(newStartPoint.getY(),testShapePolygon.getBounds().get(1).getY(),0.1);
        
    }
    
     @Test 
    public void testMirrorShape(){
        Point2D startPoint = testShapePolygon.getUpperBound();
        Point2D endPoint = testShapePolygon.getLowerBound();
        testShapePolygon.mirrorShape();
        
        assertEquals(startPoint.getX(),testShapePolygon.getUpperBound().getX(),0.1);
        assertEquals(endPoint.getX(),testShapePolygon.getLowerBound().getX(),0.1);
        assertEquals(startPoint.getY(),testShapePolygon.getUpperBound().getY(),0.1);
        assertEquals(endPoint.getY(),testShapePolygon.getLowerBound().getY(),0.1);
    }

}




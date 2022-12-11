
import java.util.ArrayList;
import javafx.geometry.Point2D;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import seproject.shapes.PolygonModel;

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
        double pointX = 10 + Math.random()*10;
        double pointY = 10 + Math.random()*10;
        Point2D startendPoint = new Point2D(pointX, pointY);
        points.add(startendPoint);
        for(int i = 1; i<sidesNumber; i++){
            pointX = 200 + Math.random()*100;
            pointY = 200 + Math.random()*100;
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
        
        Point2D translatePoint= new Point2D(Math.random()*2000,Math.random()*2000);
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
        double newStartX = Math.random()*2000;
        double newStartY = Math.random()*2000;
        
       Point2D newStart = new Point2D(newStartX, newStartY);
       testShapePolygon.pasteShape(testDrawingArea, newStart);
       
       PolygonModel actualPoly = (PolygonModel)testDrawingArea.getChildren().get(1);
       assertEquals(newStartX, actualPoly.getPoints().get(0), 0.1);
       assertEquals(newStartY, actualPoly.getPoints().get(1), 0.1);
    }
    
     @Test 
    public void testChangeOutlineColor(){
        Color testOutline = Color.color(Math.random(), Math.random(), Math.random());
        testShapePolygon.changeOutlineColor(testOutline);
        assertEquals(testOutline, testShapePolygon.getOutlineColor());
    }
    
    @Test 
    public void testChangeFillingColor(){
        Color testFilling = Color.color(Math.random(), Math.random(), Math.random());
        testShapePolygon.changeFillingColor(testFilling);
        assertEquals(testFilling, testShapePolygon.getFillingColor());
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
        
        assertEquals(newStartPoint.getX(),testShapePolygon.getUpperBound().getX(),0.1);
        assertEquals(newEndPoint.getX(),testShapePolygon.getLowerBound().getX(),0.1);
        assertEquals(newStartPoint.getY(),testShapePolygon.getUpperBound().getY(),0.1);
        assertEquals(newEndPoint.getY(),testShapePolygon.getLowerBound().getY(),0.1);
        
        points.clear();
        points.add(0,newStartPoint);
        points.add(1,oldStartPoint);
        testShapePolygon.changeDimensions(points);
        assertEquals(oldStartPoint.getX(),testShapePolygon.getUpperBound().getX(),0.1);
        assertEquals(newStartPoint.getX(),testShapePolygon.getLowerBound().getX(),0.1);
        assertEquals(oldStartPoint.getY(),testShapePolygon.getUpperBound().getY(),0.1);
        assertEquals(newStartPoint.getY(),testShapePolygon.getLowerBound().getY(),0.1);
        
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
    
    @Test
    public void testStretchVertical(){
        Point2D startPoint = testShapePolygon.getUpperBound();
        Point2D endPoint = testShapePolygon.getLowerBound();
        
        double height = endPoint.getY() - startPoint.getY();
        testShapePolygon.stretchVertical(-2*height);
        assertEquals(startPoint,testShapePolygon.getUpperBound());
        assertEquals(endPoint,testShapePolygon.getLowerBound());
        
        
        testShapePolygon.stretchVertical(height/2);
        assertEquals(startPoint.getY() - height/4,testShapePolygon.getUpperBound().getY(),0.1);
        assertEquals(endPoint.getY() + height/4,testShapePolygon.getLowerBound().getY(),0.1);
        assertEquals(startPoint.getX(),testShapePolygon.getUpperBound().getX(),0.1);
        assertEquals(endPoint.getX(),testShapePolygon.getLowerBound().getX(),0.1);
        
    }
    
    @Test
    public void testStretchHorizontal(){
        Point2D startPoint = testShapePolygon.getUpperBound();
        Point2D endPoint = testShapePolygon.getLowerBound();
        
        double width = endPoint.getX() - startPoint.getX();
        testShapePolygon.stretchHorizontal(-2*width);
        assertEquals(startPoint,testShapePolygon.getUpperBound());
        assertEquals(endPoint,testShapePolygon.getLowerBound());
        
        
        testShapePolygon.stretchHorizontal(width/2);
        assertEquals(startPoint.getX() - width/4,testShapePolygon.getUpperBound().getX(),0.1);
        assertEquals(endPoint.getX() + width/4,testShapePolygon.getLowerBound().getX(),0.1);
        assertEquals(startPoint.getY(),testShapePolygon.getUpperBound().getY(),0.1);
        assertEquals(endPoint.getY(),testShapePolygon.getLowerBound().getY(),0.1);
    }

    @Test
    public void testRotate(){
        double deg = 10;
        testShapePolygon.rotate(deg);
        
        assertEquals(deg,testShapePolygon.getRotation(),0.01);
        
        testShapePolygon.rotate(-deg);
        assertEquals(0,testShapePolygon.getRotation(),0.01);
    }
}




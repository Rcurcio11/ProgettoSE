
import java.util.ArrayList;
import javafx.geometry.Point2D;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import org.junit.Before;
import static org.junit.Assert.*;
import org.junit.Test;
import seproject.shapes.TextModel;

/**
 *
 * @author Group14
 */
public class TextModelTest {
    
    private AnchorPane testDrawingArea;
    private TextModel testShapeText;
    private String testText;
    
    @Before
    public void setUp() {
        testDrawingArea = new AnchorPane();
        testShapeText = new TextModel();
        testText = "Test Content";
        ArrayList<Point2D> testPoints = new ArrayList<>();
        testPoints.add(new Point2D(10 + Math.random()*2000, 10 + Math.random()*2000));
        Color outlineColor = Color.color(Math.random(), Math.random(), Math.random());
        Color fillingColor = Color.color(Math.random(), Math.random(), Math.random());
        testShapeText.insert(testDrawingArea, testPoints, outlineColor, fillingColor);
        testShapeText.setContent(testText);
    }
    
    @Test
    public void testInsert() {
        assertEquals(1,testDrawingArea.getChildren().size());
        assertEquals(TextModel.class,testDrawingArea.getChildren().get(0).getClass());
    }
    
    @Test
    public void testMove() {   
        Point2D translatePoint= new Point2D(Math.random()*10,Math.random()*10);
        Point2D startPoint = testShapeText.getUpperBound();
        
        testShapeText.move(translatePoint);
        
        assertEquals(testShapeText, testDrawingArea.getChildren().get(0));
        assertEquals(startPoint.getX() + translatePoint.getX() , testShapeText.getUpperBound().getX(),2);
        assertEquals(startPoint.getY() + translatePoint.getY() , testShapeText.getUpperBound().getY(), 2);
        
    }
    
    @Test
    public void testDeleteShape(){
        testShapeText.deleteShape(testDrawingArea);

        assertEquals(0, testDrawingArea.getChildren().size());
    }
    
    @Test
    public void testPasteShape(){
       double newStartX = Math.random()*2000;
       double newStartY = Math.random()*2000;
        
       Point2D newStart = new Point2D(newStartX, newStartY);
       testShapeText.pasteShape(testDrawingArea, newStart);
       
       TextModel actualLine = (TextModel)testDrawingArea.getChildren().get(1);
       assertEquals(newStartX, actualLine.getUpperBound().getX(), 2);
       assertEquals(newStartY, actualLine.getUpperBound().getY(), 2);
    }
    
    @Test 
    public void testChangeOutlineColor(){
        Color testOutline = Color.color(Math.random(), Math.random(), Math.random());
        testShapeText.changeOutlineColor(testOutline);
        assertEquals(testOutline, testShapeText.getOutlineColor());
    }
    
    @Test 
    public void testChangeFillingColor(){
        Color testFilling = Color.color(Math.random(), Math.random(), Math.random());
        testShapeText.changeFillingColor(testFilling);
        assertEquals(testFilling, testShapeText.getFillingColor());
    }
    
    @Test
    public void testChangeDimensions(){
        Point2D oldEndPoint = testShapeText.getLowerBound();
        Point2D oldStartPoint = testShapeText.getUpperBound();
        Point2D newEndPoint = new Point2D(oldEndPoint.getX() + Math.random()*10,oldEndPoint.getY() + Math.random()*10);
        Point2D newStartPoint = new Point2D(oldStartPoint.getX() + Math.random()*10,oldStartPoint.getY() + Math.random()*10);
        ArrayList<Point2D> testPoints = new ArrayList<>();
        testPoints.add(oldStartPoint);
        testPoints.add(newEndPoint);
        
        testShapeText.changeDimensions(testPoints);
        double width = testShapeText.getBoundsInParent().getWidth();
        double newWidth = newEndPoint.getX() - testShapeText.getX();
        double newFont = (testShapeText.getFont().getSize()*newWidth)/width;
            
        assertEquals(newFont, testShapeText.getFont().getSize(), 1);
    }
    
}

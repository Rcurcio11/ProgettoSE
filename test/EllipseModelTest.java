

import javafx.geometry.Point2D;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import seproject.EllipseModel;
import seproject.LineModel;

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
        double startX = Math.random()*663;
        double startY = Math.random()*479;
        double endX = Math.random()*663; 
        double endY = Math.random()*479;
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
        EllipseModel actualShape = (EllipseModel) testDrawingArea.getChildren().get(0);
        
        testShapeEllipse.setTranslateX(Math.random()*663);
        testShapeEllipse.setTranslateX(Math.random()*479);
        
        testShapeEllipse.move();
        
        assertEquals(testShapeEllipse, testDrawingArea.getChildren().get(0));
        
        assertEquals(actualShape.getTranslateX() + actualShape.getStartPoint().getX() , testShapeEllipse.getStartPoint().getX(), 0.1);
        assertEquals(actualShape.getTranslateY() + actualShape.getStartPoint().getY() , testShapeEllipse.getStartPoint().getY(), 0.1);
        
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
}

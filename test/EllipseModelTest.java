

import javafx.geometry.Point2D;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import seproject.EllipseModel;

/**
 *
 * @author Group14
 */
public class EllipseModelTest {
    private EllipseModel testShapeEllipse;
    
    public EllipseModelTest() {
        testShapeEllipse = new EllipseModel();
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }


    @Test
    public void testInsert() {
        AnchorPane drawingPane = new AnchorPane();
        double startX = Math.random()*663;
        double startY = Math.random()*479;
        double endX = Math.random()*663; 
        double endY = Math.random()*479;
        Point2D startPoint = new Point2D(startX,startY);
        Point2D endPoint = new Point2D(endX,endY);
        Color outlineColor = Color.color(Math.random(), Math.random(), Math.random());
        Color fillingColor = Color.color(Math.random(), Math.random(), Math.random());
        
        
        testShapeEllipse.insert(drawingPane, startPoint, endPoint, outlineColor, fillingColor);
        
        assertEquals(1,drawingPane.getChildren().size());
        assertEquals(EllipseModel.class,drawingPane.getChildren().get(0).getClass());
    }
}

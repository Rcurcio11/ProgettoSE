
import javafx.geometry.Point2D;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import seproject.LineModel;


/**
 *
 * @author Group14
 */
public class LineModelTest {
    
    private LineModel testShapeLine;
    
    public LineModelTest() {
        testShapeLine = new LineModel();
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
    public void testInsert(){    
        AnchorPane drawingPane = new AnchorPane();
        double startX = Math.random()*663;
        double startY = Math.random()*479;
        double endX = Math.random()*663; 
        double endY = Math.random()*479;
        Point2D startPoint = new Point2D(startX,startY);
        Point2D endPoint = new Point2D(endX,endY);
        
        testShapeLine.insert(drawingPane, startPoint, endPoint, Color.color(Math.random(), Math.random(), Math.random()));
        assertEquals(1,drawingPane.getChildren().size());
        assertEquals(LineModel.class,drawingPane.getChildren().get(0).getClass());
    }
    
    

}

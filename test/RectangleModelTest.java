
import javafx.geometry.Point2D;
import javafx.scene.layout.AnchorPane;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import seproject.RectangleModel;

/**
 *
 * @author Group14
 */
public class RectangleModelTest {
    private RectangleModel testShapeRectangle;
    
    public RectangleModelTest() {
        testShapeRectangle = new RectangleModel();
        
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
       AnchorPane testPane = new AnchorPane();     
       double startX = Math.random()*663;
       double startY = Math.random()*479;
       double endX = Math.random()*663; 
       double endY = Math.random()*479;
       Point2D startPoint = new Point2D(startX,startY);
       Point2D endPoint = new Point2D(endX,endY);
       
       testShapeRectangle.insert(testPane, startPoint, endPoint);
       
       assertEquals(1, testPane.getChildren().size());
       assertEquals(RectangleModel.class, testPane.getChildren().get(0).getClass());
    }   
}



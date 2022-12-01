

import javafx.geometry.Point2D;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import org.junit.Test;
import static org.junit.Assert.*;
import seproject.EllipseModel;
import seproject.LineModel;

/**
 *
 * @author Group14
 */
public class EllipseModelTest {
    private EllipseModel testShapeEllipse;
    
    public EllipseModelTest() {
        testShapeEllipse = new EllipseModel();
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
    
    @Test
    public void testMove() {   
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
        
        EllipseModel actualShape = (EllipseModel) drawingPane.getChildren().get(0);
        
        testShapeEllipse.setTranslateX(Math.random()*663);
        testShapeEllipse.setTranslateX(Math.random()*479);
        
        testShapeEllipse.move();
        
        assertEquals(testShapeEllipse, drawingPane.getChildren().get(0));
        
        assertEquals(actualShape.getTranslateX() + actualShape.getStartPoint().getX() , testShapeEllipse.getStartPoint().getX(), 0.1);
        assertEquals(actualShape.getTranslateY() + actualShape.getStartPoint().getY() , testShapeEllipse.getStartPoint().getY(), 0.1);
        
    }
}

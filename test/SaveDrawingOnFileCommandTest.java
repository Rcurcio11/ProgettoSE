
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;
import javafx.geometry.Point2D;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import seproject.shapes.EllipseModel;
import seproject.shapes.LineModel;
import seproject.shapes.PolygonModel;
import seproject.shapes.RectangleModel;
import seproject.commands.SaveDrawingOnFileCommand;
import seproject.shapes.ShapeModel;

/**
 *
 * @author Group14
 */
public class SaveDrawingOnFileCommandTest {
    private SaveDrawingOnFileCommand testSaveDrawingOnFileCommand;
    private AnchorPane testDrawingArea;
    private String testFilePath;
    private ArrayList<Point2D> points;
    
    public SaveDrawingOnFileCommandTest() {
    }
    
    @Before
    public void setUp() {
        testFilePath = "SaveDrawingOnFileCommandTest.txt";
        testDrawingArea = new AnchorPane();
        testSaveDrawingOnFileCommand = new SaveDrawingOnFileCommand(testDrawingArea,testFilePath);
        
        ArrayList<Point2D> points = new ArrayList<>();
        points.add(new Point2D(10,12));
        points.add(new Point2D(14,16)); 
        Color stroke = Color.BLACK;
        Color fill = Color.RED;
        ShapeModel line = new LineModel();
        line.insert(testDrawingArea, points,stroke,fill);
        
        ShapeModel rect = new RectangleModel();
        rect.insert(testDrawingArea,points,stroke,fill);
        
        ShapeModel ellipse = new EllipseModel();
        ellipse.insert(testDrawingArea,points,stroke,fill);
        
        //line = new LineModel();
        //line.insert(testDrawingArea, points,stroke,fill);
        
        ShapeModel poly = new PolygonModel();
        points.add(new Point2D(20,30));
        points.add(new Point2D(16,32));
        points.add(new Point2D(10,12));
        poly.insert(testDrawingArea, points, stroke, fill);
        this.points = points;
    }
   
    @Test
    public void testSaveOnSuccess() throws FileNotFoundException {
        testSaveDrawingOnFileCommand.execute();
        
        Scanner loader = new Scanner(new FileInputStream(testFilePath)).useDelimiter(";").useLocale(Locale.US);

        String className = loader.next();
        assertEquals(className,LineModel.class.getSimpleName());
        //ShapeModel shape = new LineModel();
        
        int points = loader.nextInt();
        assertEquals(points,2);
        double deg = loader.nextDouble();
        double startX = loader.nextDouble();
        double startY = loader.nextDouble();
        double endX = loader.nextDouble();
        double endY = loader.nextDouble();
        String stroke = loader.next();
        String fill = loader.next();
        Point2D startPoint = new Point2D(startX,startY);
        Point2D endPoint = new Point2D(endX,endY);
        //assertEquals(className,LineModel.class.getSimpleName());
        assertEquals(startPoint,new Point2D(10,12));
        assertEquals(endPoint,new Point2D(14,16));
        assertEquals(stroke,Color.BLACK.toString());
        assertEquals(fill,Color.BLACK.toString());
        assertEquals(0,deg,0.1);
        
        className = loader.next();
        //shape = new RectangleModel();
        assertEquals(className,RectangleModel.class.getSimpleName());
        
        points = loader.nextInt();
        assertEquals(points,2);
        deg = loader.nextDouble();
        startX = loader.nextDouble();
        startY = loader.nextDouble();
        endX = loader.nextDouble();
        endY = loader.nextDouble();
        stroke = loader.next();
        fill = loader.next();
        startPoint = new Point2D(startX,startY);
        endPoint = new Point2D(endX,endY);
        //assertEquals(className,RectangleModel.class.getSimpleName());
        assertEquals(startPoint,new Point2D(10,12));
        assertEquals(endPoint,new Point2D(14,16));
        assertEquals(stroke,Color.BLACK.toString());
        assertEquals(fill,Color.RED.toString());
        assertEquals(0,deg,0.1);
        
        className = loader.next();
        assertEquals(className,EllipseModel.class.getSimpleName());
        
        //shape = new EllipseModel();
        points = loader.nextInt();
        assertEquals(points,2);
        deg = loader.nextDouble();
        startX = loader.nextDouble();
        startY = loader.nextDouble();
        endX = loader.nextDouble();
        endY = loader.nextDouble();
        stroke = loader.next();
        fill = loader.next();
        startPoint = new Point2D(startX,startY);
        endPoint = new Point2D(endX,endY);
        assertEquals(className,EllipseModel.class.getSimpleName());
        assertEquals(startPoint,new Point2D(10,12));
        assertEquals(endPoint,new Point2D(14,16));
        assertEquals(stroke,Color.BLACK.toString());
        assertEquals(fill,Color.RED.toString());
        assertEquals(0,deg,0.1);
        
        className = loader.next();
        assertEquals(className,PolygonModel.class.getSimpleName());
        
        points = loader.nextInt();
        assertEquals(5,points);
        deg = loader.nextDouble();
        for(int i=0; i<points; i++){
            double X = loader.nextDouble();
            double Y = loader.nextDouble();
            assertEquals(this.points.get(i),new Point2D(X,Y));
        }
        
        stroke = loader.next();
        fill = loader.next();
        assertEquals(stroke,Color.BLACK.toString());
        assertEquals(fill,Color.RED.toString());
        assertEquals(0,deg,0.1);
    }
}

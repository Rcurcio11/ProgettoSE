
package seproject;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Locale;
import java.util.Scanner;
import javafx.geometry.Point2D;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;

/**
 *
 * @author Group14
 */
public class LoadDrawingFromFileCommand implements OperationCommand{
    
    private AnchorPane drawingArea;
    private String filePath;
    
    public LoadDrawingFromFileCommand(AnchorPane drawingArea,String filePath){
        this.drawingArea = drawingArea;
        this.filePath = filePath;
    }
    
    @Override
    public void execute() throws GenericDrawException{
        drawingArea.getChildren().clear();
        try{
            Scanner loader = new Scanner(new FileInputStream(filePath)).useDelimiter(";").useLocale(Locale.US);
            while(loader.hasNext()){
                String className = loader.next();
                ShapeModel shape = null;
                if(className.equals(RectangleModel.class.getSimpleName())){
                    shape = new RectangleModel();
                }
                else if(className.equals(EllipseModel.class.getSimpleName())){
                    shape = new EllipseModel();
                }
                else if(className.equals(LineModel.class.getSimpleName())){
                    shape = new LineModel();
                }
                else
                    throw new ShapeModelNotSupportedDrawException();
                double startX = loader.nextDouble();
                double startY = loader.nextDouble();
                double endX = loader.nextDouble();
                double endY = loader.nextDouble();
                String outlineColor = loader.next();
                Point2D startPoint = new Point2D(startX,startY);
                Point2D endPoint = new Point2D(endX,endY);
                shape.insert(drawingArea,startPoint,endPoint,Color.web(outlineColor));
             }
        }catch(FileNotFoundException ex){
            throw new FileErrorDrawException();
        }
    }
    
}

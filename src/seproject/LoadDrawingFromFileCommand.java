
package seproject;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Locale;
import java.util.Scanner;
import javafx.collections.ObservableList;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;

/**
 *
 * @author Group14
 */
public class LoadDrawingFromFileCommand implements OperationCommand{
    
    private AnchorPane drawingArea;
    private ObservableList<Node> children;
    private String filePath;
    
    public LoadDrawingFromFileCommand(AnchorPane drawingArea,String filePath){
        this.drawingArea = drawingArea;
        this.filePath = filePath;
        children = drawingArea.getChildren();
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
                String fillingColor = loader.next();
                Point2D startPoint = new Point2D(startX,startY);
                Point2D endPoint = new Point2D(endX,endY);
                shape.insert(drawingArea,startPoint,endPoint,Color.web(outlineColor), Color.web(fillingColor));
             }
        }catch(FileNotFoundException ex){
            throw new FileErrorDrawException();
        }
    }

    @Override
    public void undo() {
        drawingArea.getChildren().removeAll();
        for(Node n:children){
            drawingArea.getChildren().add(n);
        }
    }
    
}


package seproject;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
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
    private List<Node> children;
    private String filePath;
    
    public LoadDrawingFromFileCommand(AnchorPane drawingArea,String filePath){
        this.drawingArea = drawingArea;
        this.filePath = filePath;
        this.children = new ArrayList<>();
        for(Node n:drawingArea.getChildren()){
            children.add(n);
        }
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
                
                double pointsNumber = loader.nextDouble();
                ArrayList<Point2D> points = new ArrayList<>();
                double pointX;
                double pointY;
                
                for(int i = 0; i<pointsNumber; i++){
                    pointX = loader.nextDouble();
                    pointY = loader.nextDouble();
                    points.add(new Point2D(pointX, pointY));
                }
                String outlineColor = loader.next();
                String fillingColor = loader.next();
                shape.insert(drawingArea,points,Color.web(outlineColor), Color.web(fillingColor));
             }
        }catch(FileNotFoundException ex){
            throw new FileErrorDrawException();
        }
    }

    @Override
    public void undo() {
        drawingArea.getChildren().clear();
        for(Node n:children){
            drawingArea.getChildren().add(n);
        }
    }
    
}

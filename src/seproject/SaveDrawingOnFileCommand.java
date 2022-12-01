
package seproject;


import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author Group14
 */
public class SaveDrawingOnFileCommand implements OperationCommand{
    
    private AnchorPane drawingArea;
    private String filePath;
    
    public SaveDrawingOnFileCommand(AnchorPane drawingArea,String filePath){
        this.drawingArea = drawingArea;
        this.filePath = filePath;
    }
    
    @Override
    public void execute() throws GenericDrawException{
        try{
            PrintWriter saver = new PrintWriter(new FileOutputStream(filePath));
                for(Node n:drawingArea.getChildren()){
                    ShapeModel shape = (ShapeModel)n;
                    saver.print(shape.saveOnFileString(";"));
                }
                saver.close();
            }catch(FileNotFoundException ex){
                System.out.println("Error");
                throw new FileErrorDrawException();
        }
    }
    
    @Override
    public void undo(){
        
    }
}

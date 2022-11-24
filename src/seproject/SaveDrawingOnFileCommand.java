
package seproject;


import javafx.scene.layout.AnchorPane;

/**
 *
 * @author Gruppo14
 */
public class SaveDrawingOnFileCommand implements OperationCommand{
    
    private AnchorPane drawingArea;
    private String filePath;
    
    public SaveDrawingOnFileCommand(AnchorPane drawingArea,String filePath){
        this.drawingArea = drawingArea;
        this.filePath = filePath;
    }
    
    @Override
    public void execute() {
        
    }
    
}

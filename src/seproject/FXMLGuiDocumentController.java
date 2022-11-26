
package seproject;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Point2D;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;

/**
 *
 * @author Group14
 */
public class FXMLGuiDocumentController implements Initializable {

    ///////////////// USER VARIABLES /////////////////
    
    private ShapeModel selectedShape;
    private OperationExecutor commandInvoker = new OperationExecutor();
    private Point2D startPoint;
    private Point2D endPoint;
    private FileChooser fc;
    
    //////////////////////////////////////////////////
    
    @FXML
    private AnchorPane drawingArea;
    @FXML
    private HBox toolBox;
    @FXML
    private Button rettangleButton;
    @FXML
    private Button ellipseButton;
    @FXML
    private Button lineButton;
    @FXML
    private VBox menuVBox;
    @FXML
    private Menu fileMenu;
    @FXML
    private MenuItem saveMenu;
    @FXML
    private MenuItem loadMenu;
    @FXML
    private ColorPicker outlineColor;
    @FXML
    private ColorPicker fillingColor;
    @FXML
    private Label statusLabel;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        startPoint = new Point2D(0,0);
        endPoint = new Point2D(0,0);
        statusLabel.setText("Welcome");
    }

    @FXML
    private void handleMouseReleasedOnDrawingArea(MouseEvent event) {
        endPoint = new Point2D(event.getX(),event.getY());
        try{
            InsertCommand command = new InsertCommand(drawingArea, selectedShape,startPoint, endPoint, outlineColor.getValue(), fillingColor.getValue());
            commandInvoker.execute(command);
            selectedShape = selectedShape.nextDraw();
        }catch(ShapeNotSelectedDrawException ex){
            //manage exception message
            statusLabel.setText("Shape not selected");
        }
        
    }
    @FXML
    private void handleMousePressedOnDrawingArea(MouseEvent event) {
        startPoint = new Point2D(event.getX(),event.getY());
    }


    @FXML
    private void handleButtonActionRectangle(ActionEvent event) {
        selectedShape = new RectangleModel();
        statusLabel.setText("Rectangle");
    }

    @FXML
    private void handleButtonActionEllipse(ActionEvent event) {
        selectedShape = new EllipseModel();
        statusLabel.setText("Ellipse");
    }

    @FXML
    private void handleButtonActionLine(ActionEvent event) {
        selectedShape = new LineModel();
        statusLabel.setText("Line");
        
    }

    @FXML
    private void handleActionSaveDrawing(ActionEvent event) {
        statusLabel.setText("");
        fc = new FileChooser();
        File selectedFile = fc.showSaveDialog(null);
        if(selectedFile != null){
            try{
                commandInvoker.execute(new SaveDrawingOnFileCommand(drawingArea,selectedFile.getAbsolutePath()));
            }catch(FileErrorDrawException ex){
                statusLabel.setText("File error, try again");
            }
        }
    }

    @FXML
    private void handleActionLoadDrawing(ActionEvent event) {
        statusLabel.setText("");
        fc = new FileChooser();
        File selectedFile = fc.showOpenDialog(null);
        if(selectedFile != null){
            try{
                commandInvoker.execute(new LoadDrawingFromFileCommand(drawingArea,selectedFile.getAbsolutePath()));
            }catch(FileErrorDrawException | ShapeModelNotSupportedDrawException ex){
                statusLabel.setText("File not supported");
            }
        }
    }    

    @FXML
    private void handleClickedToolBox(MouseEvent event) {
        statusLabel.setText("");
        selectedShape = null;
    }

    
}

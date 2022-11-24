
package seproject;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Point2D;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

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
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        startPoint = new Point2D(0,0);
        endPoint = new Point2D(0,0);
    }

    @FXML
    private void handleMouseReleasedOnDrawingArea(MouseEvent event) {
        endPoint = new Point2D(event.getX(),event.getY());
        InsertCommand command = new InsertCommand(drawingArea, selectedShape, startPoint, endPoint);
        commandInvoker.execute(command);
        selectedShape = selectedShape.nextDraw();
        
    }
    @FXML
    private void handleMousePressedOnDrawingArea(MouseEvent event) {
        startPoint = new Point2D(event.getX(),event.getY());
    }


    @FXML
    private void handleButtonActionRectangle(ActionEvent event) {
        selectedShape = new RectangleModel();
    }

    @FXML
    private void handleButtonActionEllipse(ActionEvent event) {
        selectedShape = new EllipseModel();
    }

    @FXML
    private void handleButtonActionLine(ActionEvent event) {
        
    }

    

    
}

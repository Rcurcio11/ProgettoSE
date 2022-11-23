
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

    @FXML
    private AnchorPane drawingArea;
    @FXML
    private HBox toolBox;

    private Point2D startPoint;
    private Point2D endPoint;
    @FXML
    private Button rettangleButton;
    @FXML
    private Button ellipseButton;
    @FXML
    private Button lineeButton;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        startPoint = new Point2D(0,0);
        endPoint = new Point2D(0,0);
    }

    @FXML
    private void handleMouseReleasedOnDrawingArea(MouseEvent event) {
        endPoint = new Point2D(event.getX(),event.getY());
    }

    @FXML
    private void handleMouseClickedOnDrawingArea(MouseEvent event) {
        startPoint = new Point2D(event.getX(),event.getY());
    }

    @FXML
    private void handleButtonActionRectangle(ActionEvent event) {
    }

    @FXML
    private void handleButtonActionEllipse(ActionEvent event) {
    }

    @FXML
    private void handleButtonActionLinee(ActionEvent event) {
    }

    
}

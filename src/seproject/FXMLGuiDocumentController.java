
package seproject;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Point2D;
import javafx.scene.input.MouseEvent;
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
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        startPoint = new Point2D(0,0);
        endPoint = new Point2D(0,0);
    }

    @FXML
    private void endDrawingPoint(MouseEvent event) {
        endPoint = new Point2D(event.getX(),event.getY());
    }

    @FXML
    private void startDrawingPoint(MouseEvent event) {
        startPoint = new Point2D(event.getX(),event.getY());
    }

    
}

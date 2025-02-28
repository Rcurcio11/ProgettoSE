
package seproject;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 *
 * @author Group14
 */
public class SEProject extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("FXMLGuiDocument.fxml"));
        stage.getIcons().add(new Image("file:C:\\Università\\Magistrale\\1semetre\\SE\\SEProject\\src\\seproject\\icon.png"));
        stage.setTitle("Drawing shape");
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}

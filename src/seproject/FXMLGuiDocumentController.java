
package seproject;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;
import javafx.stage.FileChooser;

/**
 *
 * @author Group14
 */
public class FXMLGuiDocumentController implements Initializable {

    ///////////////// USER VARIABLES /////////////////
    
    private ShapeModel shapeToInsert;
    private ShapeModel toyShape;
    private OperationExecutor commandInvoker;
    private Point2D startPoint;
    private Point2D endPoint;
    private FileChooser fc;
    private ShapeModel selectedShape;
    private BooleanProperty shapeIsSelected;
    private RectangleModel selectionRectangle;
    
    //////////////////////////////////////////////////
    
    @FXML
    private AnchorPane drawingArea;
    @FXML
    private HBox toolBox;
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
    @FXML
    private CheckBox selectShapeCheckBox;
    @FXML
    private HBox editBox;
    @FXML
    private Button deleteButton;
    @FXML
    private Menu undoMenu;
    @FXML
    private Button changeDimensionsButton;
    @FXML
    private Button rectangleButton;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        commandInvoker = new OperationExecutor();
        startPoint = new Point2D(0,0);
        endPoint = new Point2D(0,0);
        statusLabel.setText("Welcome");
        selectedShape = null;
        shapeIsSelected = new SimpleBooleanProperty(false);
        selectionRectangle = null;
        shapeToInsert = null;
        toyShape = null;
        
        //BINDINGS
        editBox.disableProperty().bind(shapeIsSelected.not());
        editBox.visibleProperty().bind(selectShapeCheckBox.selectedProperty());
        shapeIsSelected.addListener((o, oldVal, newVal) -> {
            if(!newVal){
                removeSelectionRectangle();
                selectedShape = null;
                changeDimensionsButton.setDefaultButton(false);
            }
            else
                insertSelectionRectangle();
        });
        
        ChangeListener<Boolean> shapeButtonListener = (o, oldVal, newVal) -> {
            if(newVal.booleanValue()){
                selectShapeCheckBox.setSelected(false);
                shapeIsSelected.setValue(false);
            }
            
        };        
        rectangleButton.armedProperty().addListener(shapeButtonListener);
        ellipseButton.armedProperty().addListener(shapeButtonListener);
        lineButton.armedProperty().addListener(shapeButtonListener);
    }

    @FXML
    private void handleMouseReleasedOnDrawingArea(MouseEvent event) {
        endPoint = new Point2D(event.getX(),event.getY());
        try{            
            if(shapeIsSelected.getValue() == true && changeDimensionsButton.defaultButtonProperty().getValue()){
                OperationCommand c = new ChangeShapeDimensionsCommand(drawingArea,selectedShape.getStartPoint(),endPoint,selectedShape);
                commandInvoker.execute(c);
                shapeIsSelected.setValue(false);
            }
            else{  
                InsertCommand command = new InsertCommand(drawingArea, shapeToInsert,startPoint, endPoint, outlineColor.getValue(), fillingColor.getValue());
                commandInvoker.execute(command);
                drawingArea.getChildren().remove(toyShape);
                shapeToInsert = shapeToInsert.nextDraw();
            }
        }catch(ShapeNotSelectedDrawException ex){
            //manage exception message
        }
    }
    
    @FXML
    private void handleMousePressedOnDrawingArea(MouseEvent event) {
        startPoint = new Point2D(event.getX(),event.getY());
        if(toyShape != null)
            toyShape.insert(drawingArea, startPoint, startPoint, Color.GREY, Color.TRANSPARENT);
    }

    @FXML
    private void handleButtonActionRectangle(ActionEvent event) {
        shapeToInsert = new RectangleModel();
        toyShape = new RectangleModel();
        statusLabel.setText("Rectangle");
       
    }

    @FXML
    private void handleButtonActionEllipse(ActionEvent event) {
        shapeToInsert = new EllipseModel();
        toyShape = new EllipseModel();
        statusLabel.setText("Ellipse");
    }

    @FXML
    private void handleButtonActionLine(ActionEvent event) {
        shapeToInsert = new LineModel();
        toyShape = new LineModel();
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
        shapeToInsert = null;
        toyShape = null;
    }


    @FXML
    private void handleSelectCheckBox(ActionEvent event) {
        if(selectShapeCheckBox.isSelected())
            statusLabel.setText("Select a shape");
        else
            statusLabel.setText("");
        shapeIsSelected.setValue(false);
        removeSelectionRectangle();
        shapeToInsert = null;
        toyShape = null;
    }

    @FXML
    private void handleMouseClickeOnDrawingArea(MouseEvent event) {
        //System.out.println("set done");
        if(selectShapeCheckBox.isSelected()){
            Point2D selectPoint = new Point2D(event.getX(),event.getY());
            //System.out.println(selectedShape);
            selectShape(selectPoint);
        }
    }
    
    @FXML
    private void handleMouseDraggedOnDrawingArea(MouseEvent event) {
        Point2D endPoint = new Point2D(event.getX(),event.getY());
        if(shapeIsSelected.getValue() == true && changeDimensionsButton.defaultButtonProperty().getValue()){
            startPoint = selectedShape.getStartPoint();
            selectionRectangle.changeDimensions(drawingArea, startPoint, endPoint);
        }
        if(toyShape != null){
            toyShape.changeDimensions(drawingArea, startPoint, endPoint);
        }
        
    }
    
    private void selectShape(Point2D selectPoint){
        Node actualNode = null;
        shapeIsSelected.setValue(false);
        for(int i = drawingArea.getChildren().size()-1; i>=0; i--){
            actualNode = drawingArea.getChildren().get(i);
            if(actualNode.contains(selectPoint)){
               //actualNode.setOpacity(0.5);
               selectedShape = (ShapeModel)actualNode; 
               
               shapeIsSelected.setValue(true);
               //insertSelectionRectangle();
               return;
            }
        }
        
    }

    private void insertSelectionRectangle(){
        selectionRectangle = new RectangleModel();
        ((Shape)selectionRectangle).getStrokeDashArray().addAll(5d);
        selectionRectangle.insert(drawingArea, selectedShape.getStartPoint(), selectedShape.getEndPoint(), Color.GREY, Color.TRANSPARENT);
    }
    
    private void removeSelectionRectangle(){
        drawingArea.getChildren().remove(selectionRectangle);
        selectionRectangle = null;
    }

    @FXML
    private void handleActionChangeDimensions(ActionEvent event) {
        if(changeDimensionsButton.defaultButtonProperty().getValue())
            changeDimensionsButton.setDefaultButton(false);
        else
            changeDimensionsButton.setDefaultButton(true);
    }
}


package seproject;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.event.Event;
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
    private ShapeModel temporaryShape = null;
    double startX, startY;
    
    private ShapeModel prova;
    
    
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
    private Button changeDimensionsButton;
    @FXML
    private Button rectangleButton;
    @FXML
    private Button moveButton;
    @FXML
    private Button cutButton;
    @FXML
    private Button copyButton;
    @FXML
    private Button pasteButton;
    @FXML
    private Button frontButton;
    @FXML
    private Button backButton;
    @FXML
    private Button undoButton;
    
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
        
        pasteButton.visibleProperty().bind(selectShapeCheckBox.selectedProperty());
        
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
            else if(moveButton.defaultButtonProperty().getValue() && selectedShape != null && selectShapeCheckBox.isSelected()){
            moveButton.setDefaultButton(false);
            MoveCommand mc = new MoveCommand(selectedShape, new Point2D(event.getX() - startPoint.getX(),event.getY()- startPoint.getY()));
            commandInvoker.execute(mc);
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
       
        if(pasteButton.defaultButtonProperty().getValue()){
            pasteButton.setDefaultButton(false);
            OperationCommand pc = new PasteCommand(drawingArea, startPoint, temporaryShape);
            commandInvoker.execute(pc);
        }

    }

    @FXML
    private void handleButtonActionRectangle(ActionEvent event) {
        shapeToInsert = new RectangleModel();
        toyShape = new RectangleModel();
        statusLabel.setText("Rectangle");
        moveButton.setDefaultButton(false);
       
    }

    @FXML
    private void handleButtonActionEllipse(ActionEvent event) {
        shapeToInsert = new EllipseModel();
        toyShape = new EllipseModel();
        statusLabel.setText("Ellipse");
        moveButton.setDefaultButton(false);
    }

    @FXML
    private void handleButtonActionLine(ActionEvent event) {
        shapeToInsert = new LineModel();
        toyShape = new LineModel();
        statusLabel.setText("Line"); 
        moveButton.setDefaultButton(false);
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
        moveButton.setDefaultButton(false);
    }


    @FXML
    private void handleSelectCheckBox(ActionEvent event) {
        if(selectShapeCheckBox.isSelected())
            statusLabel.setText("Select a shape");
        else
            statusLabel.setText("");
        shapeIsSelected.setValue(false);
        shapeToInsert = null;
        toyShape = null;
    }

    @FXML
    private void handleMouseClickeOnDrawingArea(MouseEvent event) {
        if(selectShapeCheckBox.isSelected()){
            Point2D selectPoint = new Point2D(event.getX(),event.getY());
            selectShape(selectPoint);
        }
    }
    
    @FXML
    private void handleMouseDraggedOnDrawingArea(MouseEvent event) {
        
        if(shapeIsSelected.getValue() == true && changeDimensionsButton.defaultButtonProperty().getValue()){
            Point2D endPoint = new Point2D(event.getX(),event.getY());
            startPoint = selectedShape.getStartPoint();
            selectionRectangle.changeDimensions(startPoint, endPoint);
        }
        if(toyShape != null){
            Point2D endPoint = new Point2D(event.getX(),event.getY());
            toyShape.changeDimensions(startPoint, endPoint);
        }
        if(moveButton.defaultButtonProperty().getValue()){
            Point2D translatePoint = new Point2D(event.getX()-startPoint.getX(),event.getY()-startPoint.getY());
            ((Shape)selectionRectangle).setTranslateX(translatePoint.getX());
            ((Shape)selectionRectangle).setTranslateY(translatePoint.getY());
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

    @FXML
    private void handleButtonActionMove(ActionEvent event) {
        moveButton.setDefaultButton(true);
    }

    @FXML
    private void handleButtonActionDelete(ActionEvent event) {
        DeleteCommand command = new DeleteCommand(drawingArea, selectedShape);
        shapeIsSelected.set(false);
        commandInvoker.execute(command);
    }

    @FXML
    private void handleButtonActionCut(ActionEvent event) {
        if(selectShapeCheckBox.isSelected() && selectedShape!=null){
            temporaryShape = selectedShape;
            DeleteCommand command = new DeleteCommand(drawingArea, selectedShape);
            commandInvoker.execute(command);  
            shapeIsSelected.set(false);
        }
    }

    @FXML
    private void handleButtonActionCopy(ActionEvent event) {
        if(selectShapeCheckBox.isSelected() && selectedShape!=null){
             temporaryShape = selectedShape;
             shapeIsSelected.set(false);
        }
    }

    @FXML
    private void handleButtonActionPaste(ActionEvent event) {
        if(selectShapeCheckBox.isSelected() && temporaryShape!=null){
            pasteButton.setDefaultButton(true);
        }
    }

    @FXML
    private void handleActionChangeOutlineColor(ActionEvent event) {
        if(shapeIsSelected.getValue()){
            ChangeColorCommand colorCommand = new ChangeColorCommand(selectedShape, outlineColor.getValue(), fillingColor.getValue());
            commandInvoker.execute(colorCommand); 
        }
    }

    @FXML
    private void handleActionChangeFillingColor(ActionEvent event) {
        if(shapeIsSelected.getValue()){
            ChangeColorCommand colorCommand = new ChangeColorCommand(selectedShape, outlineColor.getValue(), fillingColor.getValue());
            commandInvoker.execute(colorCommand); 
        }
    }

    @FXML
    private void handleButtonActionFront(ActionEvent event) {
        ToFrontCommand tfc = new ToFrontCommand(selectedShape);
        commandInvoker.execute(tfc);
    }

    @FXML
    private void handleButtonActionBack(ActionEvent event) {
        ToBackCommand tbc = new ToBackCommand(selectedShape);
        commandInvoker.execute(tbc);
    }

    @FXML
    private void handleActionUndoButton(ActionEvent event) {
        if(commandInvoker.commandIsInserted()){
            drawingArea.getChildren().remove(selectionRectangle);
            commandInvoker.undo();
        }
    }


}

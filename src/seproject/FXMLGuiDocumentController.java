
package seproject;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polyline;
import javafx.scene.shape.Shape;
import javafx.scene.transform.Scale;
import javafx.stage.FileChooser;

/**
 *
 * @author Group14
 */
public class FXMLGuiDocumentController implements Initializable {

    ///////////////// USER VARIABLES /////////////////
    
    private ShapeModel shapeToInsert;
    private ShapeModel insertionShape;
    private Polyline insertionPoly;
    private OperationExecutor commandInvoker;
    private FileChooser fc;
    private ShapeModel selectedShape;
    private BooleanProperty shapeIsSelected;
    private BooleanProperty gridIsOn;
    private RectangleModel selectionRectangle;
    private ShapeModel clipboardShape = null;
    private ArrayList<Point2D> points;
    private ArrayList<Point2D> insertionPoints;
    private double zoomFactor;
    private ChangeDimensionsBehaviour cdb;
    private List<RectangleModel> cornerShapes;
    private RectangleModel clickedVertex;
    
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
    @FXML
    private AnchorPane insertionArea;
    @FXML
    private AnchorPane editingArea;
    @FXML
    private AnchorPane changeDimensionsArea;
    @FXML
    private AnchorPane pasteArea;
    @FXML
    private AnchorPane moveArea;
    @FXML
    private AnchorPane parentArea;
    @FXML
    private Button gridButton;
    @FXML
    private Slider gridSizeSlider;
    @FXML
    private Label gridLabel;
    @FXML
    private Button polygonButton;
    @FXML
    private Button textButton;
    @FXML
    private Button rotateButton;
    @FXML
    private Button mirrorButton;
    @FXML
    private Button stretchButton;
    @FXML
    private Slider zoomSlider;
    @FXML
    private ScrollPane scrollArea;
    @FXML
    private Button lessZoomButton;
    @FXML
    private Button addZoomButton;
    @FXML
    private AnchorPane insertPolygonArea;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        commandInvoker = new OperationExecutor();
        points = new ArrayList<>();
        points.add(new Point2D(0,0));
        points.add(new Point2D(0,0));
        insertionPoints = new ArrayList<>();
        insertionPoints.add(new Point2D(0,0));
        insertionPoints.add(new Point2D(0,0));
        selectedShape = null;
        shapeIsSelected = new SimpleBooleanProperty(false);
        gridIsOn = new SimpleBooleanProperty(false);
        selectionRectangle = null;
        shapeToInsert = null;
        insertionShape = null;
        zoomFactor = 1;
        cdb = new ChangeDimensionsBehaviour();
        clickedVertex = null;
        insertionShape = null;
        insertPolygonArea.setDisable(true);
        zoomFactor = 1;
        
        // BINDINGS
        
        // The editBox is enabled when a shape is selected
        // The edit box is visible whene the checkBox is selected
        editBox.disableProperty().bind(shapeIsSelected.not());
        editBox.visibleProperty().bind(selectShapeCheckBox.selectedProperty());
        
        // When a shape is selected, the editingArea is enabled and the selectionRectangle appears
        // When a there isn't a shape selected the editing area is disabled and the selectionRectangle is removed
        shapeIsSelected.addListener((o, oldVal, newVal) -> {
            if(!newVal){
                editingArea.setDisable(true);
                removeSelectionRectangle();
                selectedShape = null;
            }
            else{
                editingArea.setDisable(false);
                insertSelectionRectangle();
            }
        });
        
        // This listener enable the insertionArea and automatically disable the checkBox and the shapeIsSelected property
        ChangeListener<Boolean> shapeButtonListener = (o, oldVal, newVal) -> {
            if(newVal.booleanValue()){
                selectShapeCheckBox.setSelected(false);
                shapeIsSelected.setValue(false);
                insertionArea.setDisable(false);
                insertPolygonArea.setDisable(true);
                moveButton.setDefaultButton(false);
                points.clear();
            }
            
        };        
        // when a shape button is armed the shapeButtonListener kikcs in
        rectangleButton.armedProperty().addListener(shapeButtonListener);
        ellipseButton.armedProperty().addListener(shapeButtonListener);
        lineButton.armedProperty().addListener(shapeButtonListener);
        
        // This listener enable the insertPolygonArea and automatically disable the checkBox and the shapeIsSelected property
        ChangeListener<Boolean> polygonButtonListener = (o, oldVal, newVal) -> {
            if(newVal.booleanValue()){
                selectShapeCheckBox.setSelected(false);
                shapeIsSelected.setValue(false);
                insertionArea.setDisable(true);
                insertPolygonArea.setDisable(false);
                moveButton.setDefaultButton(false);
                points.clear();
            }
            
        };        
        // when the polygon button is armed the polygonButtonListener kikcs in
        polygonButton.armedProperty().addListener(polygonButtonListener);
        
        // If a shape is selected, then the paste button is disabled (automatically enabled when a there isn't a shape selected)
        pasteButton.disableProperty().bind(shapeIsSelected);
        pasteButton.visibleProperty().bind(selectShapeCheckBox.selectedProperty());
        
        // For all the warking area (except the drawingArea) when they are disabled automatically their visible  property is set to false
        editingArea.visibleProperty().bind(editingArea.disableProperty().not());
        insertionArea.visibleProperty().bind(insertionArea.disableProperty().not());
        pasteArea.visibleProperty().bind(pasteArea.disableProperty().not());
        moveArea.visibleProperty().bind(moveArea.disableProperty().not());
        changeDimensionsArea.visibleProperty().bind(changeDimensionsArea.disableProperty().not());
        insertPolygonArea.visibleProperty().bind(insertPolygonArea.disableProperty().not());
        
        // When the insertionArea is enabled automatically the editingArea and the insertPolygonArea are set to disable
        insertionArea.disableProperty().addListener((o,oldVal,newVal) -> {
            if(!newVal){
                editingArea.setDisable(true);
                insertPolygonArea.setDisable(true);
            }
        });
        
        // When the insertPolygonArea is enabled automatically the editingArea and the insertionArea are set to disable
        insertPolygonArea.disableProperty().addListener((o,oldVal,newVal) -> {
            if(!newVal){
                editingArea.setDisable(true);
                insertionArea.setDisable(true);
            }
        });
        
        // When the  editingArea is enabled automatically the insertionArea is set to disable
        editingArea.disableProperty().addListener((o,oldVal,newVal) -> {
            if(!newVal){
                insertionArea.setDisable(true);
                insertPolygonArea.setDisable(true);
                points.clear();
                points.add(new Point2D(0,0));
                points.add(new Point2D(0,0));
            }
            else{
                changeDimensionsButton.setDefaultButton(false);
                moveButton.setDefaultButton(false);
            }
        });
        
        // When the checkBox is selected, automatically the insertionArea and the editingArea are set to disable
        selectShapeCheckBox.selectedProperty().addListener((o,oldVal,newVal) -> {
            if(newVal){
                insertionArea.setDisable(true);
                insertPolygonArea.setDisable(true);
                editingArea.setDisable(true);
                points.clear();
                points.add(new Point2D(0,0));
                points.add(new Point2D(0,0));
            }
        });
        
        changeDimensionsArea.disableProperty().addListener((o,oldVal,newVal) -> {
            if(newVal){
                //remove vertex
                cdb.removeVertex(changeDimensionsArea,cornerShapes);
            }
            else{
                cornerShapes = cdb.insertVertex(changeDimensionsArea,selectedShape);
            }
        });
        
        moveArea.disableProperty().bind(moveButton.defaultButtonProperty().not());
        moveButton.defaultButtonProperty().addListener((o,oldVal,newVal) -> {
            if(newVal)
                changeDimensionsButton.setDefaultButton(false);
        });
        changeDimensionsArea.disableProperty().bind(changeDimensionsButton.defaultButtonProperty().not());
        changeDimensionsButton.defaultButtonProperty().addListener((o,oldVal,newVal) -> {
            if(newVal)
                moveButton.setDefaultButton(false);
        });
        
        gridSizeSlider.disableProperty().bind(gridIsOn.not());
        gridSizeSlider.visibleProperty().bind(gridSizeSlider.disableProperty().not());
        gridLabel.visibleProperty().bind(gridIsOn);
        
        scrollArea.vvalueProperty().addListener((o,oldVal,newVal) -> {
            parentArea.setTranslateY(-newVal.doubleValue() * (zoomFactor-1));
        });
        scrollArea.hvalueProperty().addListener((o,oldVal,newVal) -> {
            parentArea.setTranslateX(-newVal.doubleValue() * (zoomFactor-1));
        }); 
    }

    @FXML
    private void handleButtonActionRectangle(ActionEvent event) {
        shapeToInsert = new RectangleModel();
        insertionShape = new RectangleModel();
    }

    @FXML
    private void handleButtonActionEllipse(ActionEvent event) {
        shapeToInsert = new EllipseModel();
        insertionShape = new EllipseModel();
    }

    @FXML
    private void handleButtonActionLine(ActionEvent event) {
        shapeToInsert = new LineModel();
        insertionShape = new LineModel();
    }

    @FXML
    private void handleActionSaveDrawing(ActionEvent event) {
        fc = new FileChooser();
        File selectedFile = fc.showSaveDialog(null);
        if(selectedFile != null){
            try{
                commandInvoker.execute(new SaveDrawingOnFileCommand(drawingArea,selectedFile.getAbsolutePath()));
            }catch(FileErrorDrawException ex){
            }
        }
    }

    @FXML
    private void handleActionLoadDrawing(ActionEvent event) {
        fc = new FileChooser();
        File selectedFile = fc.showOpenDialog(null);
        if(selectedFile != null){
            try{
                commandInvoker.execute(new LoadDrawingFromFileCommand(drawingArea,selectedFile.getAbsolutePath()));
            }catch(FileErrorDrawException | ShapeModelNotSupportedDrawException ex){
            }
        }
    }    

    @FXML
    private void handleClickedToolBox(MouseEvent event) {
        shapeToInsert = null;
        insertionShape = null;
        insertionArea.setDisable(true);
        moveButton.setDefaultButton(false);
    }


    @FXML
    private void handleSelectCheckBox(ActionEvent event) {
        shapeIsSelected.setValue(false);
        shapeToInsert = null;
        insertionShape = null;
    }

    @FXML
    private void handleMouseClickeOnDrawingArea(MouseEvent event) {
        Point2D selectPoint = new Point2D(event.getX(),event.getY());
        selectShape(selectPoint);  
    }
    
    private void selectShape(Point2D selectPoint){
        Node actualNode = null;
        if(!selectShapeCheckBox.selectedProperty().getValue())
            return;
        shapeIsSelected.setValue(false);
        for(int i = drawingArea.getChildren().size()-1; i>=0; i--){
            actualNode = drawingArea.getChildren().get(i);
            if(actualNode.contains(selectPoint)){
               selectedShape = (ShapeModel)actualNode; 
               shapeIsSelected.setValue(true);
               return;
            }
        }
    }
    

    private void insertSelectionRectangle(){
        selectionRectangle = new RectangleModel();
        ((Shape)selectionRectangle).getStrokeDashArray().addAll(5d);
        selectionRectangle.insert(editingArea, selectedShape.getBounds(), Color.GREY, Color.TRANSPARENT);
    }
    
    private void removeSelectionRectangle(){
        selectionRectangle.deleteShape(editingArea);
        selectionRectangle = null;
    }

    @FXML
    private void handleActionChangeDimensions(ActionEvent event) {
        if(changeDimensionsButton.defaultButtonProperty().getValue()){
            //changeDimensionsArea.setDisable(true);    
            changeDimensionsButton.setDefaultButton(false);
            //cdb.removeVertex(changeDimensionsArea, cornerShapes);
        }
        else{
            //cornerShapes = cdb.insertVertex(changeDimensionsArea, selectedShape);
            editingArea.toBack();
            drawingArea.toBack();
            changeDimensionsButton.setDefaultButton(true);
            //changeDimensionsArea.setDisable(false);
        }
    }

    @FXML
    private void handleButtonActionMove(ActionEvent event) {
        if(moveButton.defaultButtonProperty().getValue()){
            moveButton.setDefaultButton(false);
            //moveArea.setDisable(true);
        }
        else{
            moveButton.setDefaultButton(true);
            //moveArea.setDisable(false);
            editingArea.toBack();
            drawingArea.toBack();
        }
        
    }

    @FXML
    private void handleButtonActionDelete(ActionEvent event) {
        DeleteCommand command = new DeleteCommand(drawingArea, selectedShape);
        shapeIsSelected.set(false);
        commandInvoker.execute(command);
    }

    @FXML
    private void handleButtonActionCut(ActionEvent event) {
        clipboardShape = selectedShape;
        DeleteCommand command = new DeleteCommand(drawingArea, selectedShape);
        commandInvoker.execute(command);  
        shapeIsSelected.set(false);
    }

    @FXML
    private void handleButtonActionCopy(ActionEvent event) {
        clipboardShape = selectedShape;
        shapeIsSelected.set(false);
    }

    @FXML
    private void handleButtonActionPaste(ActionEvent event) {
        if(clipboardShape !=null){
            
            if(pasteButton.defaultButtonProperty().getValue()){
                pasteArea.setDisable(true);    
                editingArea.setDisable(false);
                pasteButton.setDefaultButton(false);
                drawingArea.toBack();
            }
            else{
                pasteButton.setDefaultButton(true);
                pasteArea.setDisable(false);
                editingArea.setDisable(true);
            }
        }
    }

    @FXML
    private void handleActionChangeOutlineColor(ActionEvent event) {
        if(shapeIsSelected.getValue()){
            ChangeColorCommand colorCommand = new ChangeColorCommand(selectedShape, outlineColor.getValue(), fillingColor.getValue());
            commandInvoker.execute(colorCommand); 
            shapeIsSelected.setValue(false);
        }
    }

    @FXML
    private void handleActionChangeFillingColor(ActionEvent event) {
        if(shapeIsSelected.getValue()){
            ChangeColorCommand colorCommand = new ChangeColorCommand(selectedShape, outlineColor.getValue(), fillingColor.getValue());
            commandInvoker.execute(colorCommand); 
            shapeIsSelected.setValue(false);
        }
    }

    @FXML
    private void handleButtonActionFront(ActionEvent event) {
        ToFrontCommand tfc = new ToFrontCommand(selectedShape);
        commandInvoker.execute(tfc);
        shapeIsSelected.set(false);
    }

    @FXML
    private void handleButtonActionBack(ActionEvent event) {
        ToBackCommand tbc = new ToBackCommand(selectedShape);
        commandInvoker.execute(tbc);
        shapeIsSelected.set(false);
    }

    @FXML
    private void handleActionUndoButton(ActionEvent event) {
        if(commandInvoker.commandIsInserted()){
            shapeIsSelected.set(false);
            commandInvoker.undo();
        }
    }
    
    
    
    //INSERTION AREA
    @FXML
    private void handleMouseReleasedOnInsertionArea(MouseEvent event) {
        points.add(new Point2D(event.getX(),event.getY()));    
        InsertCommand command = new InsertCommand(drawingArea, shapeToInsert,points, outlineColor.getValue(), fillingColor.getValue());
        commandInvoker.execute(command);
        insertionShape.deleteShape(insertionArea);
        shapeToInsert = shapeToInsert.nextDraw();
        points.clear();
    }

    @FXML
    private void handleMouseDraggedOnInsertionArea(MouseEvent event) {
        insertionPoints.set(1, new Point2D(event.getX(),event.getY()));
        insertionShape.changeDimensions(insertionPoints);
    }

    @FXML
    private void handleMousePressedOnInsertionArea(MouseEvent event) {
        points.add(new Point2D(event.getX(),event.getY()));
        insertionPoints.set(0, points.get(0));
        insertionPoints.set(1, points.get(0));
        insertionShape.insert(insertionArea, insertionPoints, outlineColor.getValue(), fillingColor.getValue());
    }
    
    
    //EDITING AREA
    @FXML
    private void handleMouseReleasedOnEditingArea(MouseEvent event) {
        Point2D selectPoint = new Point2D(event.getX(),event.getY());
        selectShape(selectPoint); 
        
    }
    
    // CHANGE DIMENSIONS AREA
    @FXML
    private void handleMouseReleasedOnCDArea(MouseEvent event) {
        Point2D endPoint = new Point2D(event.getX(),event.getY());

        if(clickedVertex != null){
            ArrayList<Point2D> vertexPoints = cdb.computePoints(clickedVertex,points.get(0),endPoint,selectedShape);
            OperationCommand c = new ChangeShapeDimensionsCommand(drawingArea,vertexPoints,selectedShape);
            commandInvoker.execute(c);
            shapeIsSelected.setValue(false);
            //changeDimensionsArea.setDisable(true);
            changeDimensionsButton.defaultButtonProperty().setValue(false);
            editingArea.toFront();
            clickedVertex = null;
        }
        
        /*OperationCommand c = new ChangeShapeDimensionsCommand(drawingArea,selectedShape.getLowerBound(),new Point2D(event.getX(),event.getY()),selectedShape);
        commandInvoker.execute(c);
        shapeIsSelected.setValue(false);
        changeDimensionsArea.setDisable(true);
        changeDimensionsButton.defaultButtonProperty().setValue(false);
        editingArea.toFront();*/
    }

    @FXML
    private void handleMouseDraggedOnCDArea(MouseEvent event) {
        
        Point2D endPoint = new Point2D(event.getX(),event.getY());
        //startPoint = selectedShape.getStartPoint();
        if(clickedVertex != null){
            ArrayList<Point2D> vertexPoints = cdb.computePoints(clickedVertex,points.get(0),endPoint,selectedShape);
            selectionRectangle.changeDimensions(vertexPoints);
            cdb.moveVertex(changeDimensionsArea,clickedVertex,endPoint);
        }
        
        /*points.set(1, new Point2D(event.getX(),event.getY()));
        points.set(0, selectedShape.getAllPoints().get(0));
        selectionRectangle.changeDimensions(points); 
        */
    }
    
    @FXML
    private void handleMousePressedOnCDArea(MouseEvent event) {
        Point2D selectPoint = new Point2D(event.getX(),event.getY());
        for(RectangleModel v:cornerShapes)
            if(v.contains(selectPoint)){
                clickedVertex = v;
                points.set(0, selectedShape.getBounds().get(0));
            }
        
    }
    
    // PASTE AREA
    @FXML
    private void handleMouseClickedOnPasteArea(MouseEvent event) {
        OperationCommand pasteCommand = new PasteCommand(drawingArea,new Point2D(event.getX(),event.getY()),clipboardShape);
        commandInvoker.execute(pasteCommand);
        shapeIsSelected.setValue(false);
        pasteButton.defaultButtonProperty().setValue(false);
        pasteArea.setDisable(true);
        editingArea.toFront();
    }
    
    // MOVE AREA
    @FXML
    private void handleMouseReleasedOnMoveArea(MouseEvent event) {
        
        Point2D endPoint = new Point2D(event.getX(),event.getY());
        MoveCommand mc = new MoveCommand(selectedShape, new Point2D(event.getX() - points.get(0).getX(),event.getY()- points.get(0).getY()));
        commandInvoker.execute(mc);
        shapeIsSelected.setValue(false);
        moveButton.defaultButtonProperty().setValue(false);
        //moveArea.setDisable(true);
        editingArea.toFront();
    }
    
    @FXML
    private void handleMouseDraggedOnMoveArea(MouseEvent event) {
        
        Point2D translatePoint = new Point2D(event.getX()-points.get(0).getX(),event.getY()-points.get(0).getY());
        ((Shape)selectionRectangle).setTranslateX(translatePoint.getX());
        ((Shape)selectionRectangle).setTranslateY(translatePoint.getY());
    }

    @FXML
    private void handleMousePressedOnMoveArea(MouseEvent event) {
        points.set(0, new Point2D(event.getX(),event.getY()));
    }
    
    @FXML
    private void handleActionGridButton(ActionEvent event) {
        if(gridButton.defaultButtonProperty().getValue()){
            gridIsOn.setValue(false);
            gridButton.defaultButtonProperty().setValue(false);
            drawingArea.setBackground(Background.EMPTY);
        }else{
            gridSizeSlider.setValue(gridSizeSlider.getMax()/3);
            gridIsOn.setValue(true);
            gridButton.defaultButtonProperty().setValue(true);
            drawingArea.setBackground(createGridBackground());
        }
    }
    
    private Background createGridBackground(){
        double w = gridSizeSlider.getValue();
        double h = gridSizeSlider.getValue();

        Canvas canvas = new Canvas(w, h);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        gc.setStroke(Color.BLACK);
        gc.setLineWidth(0.3);
        gc.setFill(Color.LIGHTGRAY.deriveColor(1, 1, 1, 0.2));
        gc.fillRect(0, 0, w, h);
        gc.strokeRect(0, 0, w, h);

        Image image = canvas.snapshot(new SnapshotParameters(), null);
        BackgroundImage backgroundImage = new BackgroundImage(image, 
                                             BackgroundRepeat.REPEAT, 
                                             BackgroundRepeat.REPEAT, 
                                             BackgroundPosition.DEFAULT, 
                                             BackgroundSize.DEFAULT);
        Background back = new Background(backgroundImage);

        return back;
    }

    @FXML
    private void handleActionGridSizeSlider(MouseEvent event) {
            drawingArea.setBackground(createGridBackground());
    }

    @FXML
    private void handleMouseDraggedOnZoomArea(MouseEvent event) {
        zoomManagement();
    }

    @FXML
    private void handleButtonLessZoomButton(ActionEvent event) {
        zoomSlider.setValue(zoomSlider.getValue()-1);
        zoomManagement();
    }

    @FXML
    private void handleButtonAddZoomButton(ActionEvent event) {
        zoomSlider.setValue(zoomSlider.getValue()+1);
        zoomManagement();
    }
    
    private void zoomManagement(){
        Scale s = new Scale();
        s.setPivotX(0);
        s.setPivotY(0);

        double zoomLvl = zoomSlider.getValue();
        zoomFactor = 1 + zoomLvl;

        s.setX(zoomFactor);
        s.setY(zoomFactor);
        
        parentArea.getTransforms().clear();
        parentArea.getTransforms().add(s);
        parentArea.setTranslateY(-scrollArea.getVvalue() * zoomSlider.getValue());
        parentArea.setTranslateX(-scrollArea.getHvalue() * zoomSlider.getValue());
    }

    @FXML
    private void handleMouseClickedOnInsertPolygonArea(MouseEvent event) {
        points.add(new Point2D(event.getX(),event.getY()));
        
        InsertCommand command = new InsertCommand(drawingArea, shapeToInsert,points, outlineColor.getValue(), fillingColor.getValue());
        commandInvoker.execute(command);
        
        if(shapeToInsert.getAllPoints().isEmpty()){
            if(insertionPoly.getPoints().isEmpty()){
                setInsertionPoly(insertionPoly);
                insertionPoly.getPoints().add(event.getX());
                insertionPoly.getPoints().add(event.getY());
            }
            insertionPoly.getPoints().add(event.getX());
            insertionPoly.getPoints().add(event.getY());
        }else{
            insertPolygonArea.getChildren().remove(insertionPoly);
            insertionPoly = new Polyline();
            points.clear();
        }
        shapeToInsert = shapeToInsert.nextDraw();
    }

    @FXML
    private void handleMouseMovedOnInsertPolygonArea(MouseEvent event) {
        if(!insertionPoly.getPoints().isEmpty()){
            int index = insertionPoly.getPoints().size();
            insertionPoly.getPoints().set(index-2, event.getX());
            insertionPoly.getPoints().set(index-1, event.getY());
        }
    }

    @FXML
    private void handleActionPolygonButton(ActionEvent event) {
        shapeToInsert = new PolygonModel();
        insertionPoly = new Polyline();
    }
    
    private void setInsertionPoly(Polyline insertionPoly){
        insertionPoly.setStrokeWidth(2.0);
        insertionPoly.setStroke(outlineColor.getValue());
        insertPolygonArea.getChildren().add(insertionPoly);
    }
    
}

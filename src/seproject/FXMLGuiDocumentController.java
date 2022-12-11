
package seproject;

import seproject.exceptions.*;
import seproject.commands.*;
import seproject.shapes.*;
import java.io.File;
import static java.lang.Math.abs;
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
import javafx.geometry.Bounds;
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
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
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
    
    ////////////////// FXML VARIABLES ////////////////
    
    @FXML
    private AnchorPane drawingArea;
    @FXML
    private Button ellipseButton;
    @FXML
    private Button lineButton;
    @FXML
    private ColorPicker outlineColor;
    @FXML
    private ColorPicker fillingColor;
    @FXML
    private CheckBox selectShapeCheckBox;
    @FXML
    private HBox editBox;
    @FXML
    private Button changeDimensionsButton;
    @FXML
    private Button rectangleButton;
    @FXML
    private Button moveButton;
    @FXML
    private Button pasteButton;
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
    private Button mirrorButton;
    @FXML
    private Slider zoomSlider;
    @FXML
    private ScrollPane scrollArea;
    @FXML
    private AnchorPane insertPolygonArea;
    @FXML
    private Button moreStretchHButton;
    @FXML
    private Button lessStretchHButton;
    @FXML
    private Button moreStretchVButton;
    @FXML
    private Button lessStretchVButton;
    @FXML
    private TextField textField;
    @FXML
    private VBox textBox;
    
    //////////////////////////////////////////////////
    
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
        
        setBinding();  
    }
    
    /////////////////////// EVENT HANDLER ON ANCHORPANE ////////////////////////
    
    //--------------------------- DRAWING AREA -------------------------------//
    
    @FXML
    private void handleMouseClickeOnDrawingArea(MouseEvent event) {
        Point2D selectPoint = new Point2D(event.getX(),event.getY());
        selectShape(selectPoint);  
    }

    //------------------------------------------------------------------------//
    
    //--------------------------- INSERTION AREA -----------------------------//
    
    private ShapeModel tempShape = null;
    @FXML
    private void handleMouseReleasedOnInsertionArea(MouseEvent event) {
        points.add(new Point2D(event.getX(),event.getY()));  
        shapeToInsert.deleteShape(insertionArea);
        InsertCommand command = new InsertCommand(drawingArea, shapeToInsert,points, outlineColor.getValue(), fillingColor.getValue());
        commandInvoker.execute(command);
        insertionShape.deleteShape(insertionArea);
        selectionRectangle.deleteShape(insertionArea);
        shapeToInsert = shapeToInsert.nextDraw();
        points.clear();
        cdb.removeVertex(insertionArea);
        cornerShapes = null;
    }

    @FXML
    private void handleMouseDraggedOnInsertionArea(MouseEvent event) {
        insertionPoints.set(1, new Point2D(event.getX(),event.getY()));
        ArrayList<Point2D> vertexPoints = cdb.computePoints(cornerShapes.get(3), insertionPoints.get(0), insertionPoints.get(1), shapeToInsert);
        insertionShape.deleteShape(insertionArea);
        selectionRectangle.changeDimensions(vertexPoints);
        insertionShape.insert(insertionArea, insertionPoints,outlineColor.getValue(), fillingColor.getValue());
        cdb.moveVertex(insertionArea, cornerShapes.get(3), insertionPoints.get(1));
    }

    @FXML
    private void handleMousePressedOnInsertionArea(MouseEvent event) {
        points.clear();
        selectionRectangle = new RectangleModel();
        points.add(new Point2D(event.getX(),event.getY()));
        insertionPoints.set(0, points.get(0));
        insertionPoints.set(1, new Point2D(points.get(0).getX() +1, points.get(0).getY() +1));
        selectionRectangle.insert(insertionArea, insertionPoints, Color.LIGHTGREY, Color.TRANSPARENT);
        insertionShape.insert(insertionArea, insertionPoints, outlineColor.getValue(), fillingColor.getValue());
        shapeToInsert.insert(insertionArea, insertionPoints, Color.TRANSPARENT, Color.TRANSPARENT);
        cornerShapes = cdb.insertVertex(insertionArea, shapeToInsert);
    }
    
    //------------------------------------------------------------------------//
    
    //--------------------------- EDITING AREA -------------------------------//

    @FXML
    private void handleMouseReleasedOnEditingArea(MouseEvent event) {
        Point2D selectPoint = new Point2D(event.getX(),event.getY());
        selectShape(selectPoint);   
    }
    
    //------------------------------------------------------------------------//
    
    //---------------------- CHANGE DIMENSION AREA ---------------------------//
    
    @FXML
    private void handleMouseReleasedOnCDArea(MouseEvent event) {
        Point2D endPoint = new Point2D(event.getX(),event.getY());

        if(clickedVertex != null){
            ArrayList<Point2D> vertexPoints = cdb.computePoints(clickedVertex,points.get(0),endPoint,selectedShape);
            OperationCommand c = new ChangeShapeDimensionsCommand(drawingArea,vertexPoints,selectedShape);
            commandInvoker.execute(c);
            shapeIsSelected.setValue(false);
            changeDimensionsButton.defaultButtonProperty().setValue(false);
            editingArea.toFront();
            clickedVertex = null;
        }
    }

    @FXML
    private void handleMouseDraggedOnCDArea(MouseEvent event) {
        Point2D endPoint = new Point2D(event.getX(),event.getY());
        if(clickedVertex != null){
            ArrayList<Point2D> vertexPoints = cdb.computePoints(clickedVertex,points.get(0),endPoint,selectedShape);
            selectionRectangle.changeDimensions(vertexPoints);
            cdb.moveVertex(changeDimensionsArea,clickedVertex,endPoint);
        }
    }
    
    @FXML
    private void handleMousePressedOnCDArea(MouseEvent event) {
        Point2D selectPoint = new Point2D(event.getX(),event.getY());
        for(RectangleModel v:cornerShapes)
            if(v.getBoundsInParent().contains(selectPoint)){
                clickedVertex = v;
                points.set(0, selectedShape.getBounds().get(0));
            }
    }
    
    //------------------------------------------------------------------------//
    
    //--------------------------- PASTE AREA ---------------------------------//

    @FXML
    private void handleMouseClickedOnPasteArea(MouseEvent event) {
        OperationCommand pasteCommand = new PasteCommand(drawingArea,new Point2D(event.getX(),event.getY()),clipboardShape);
        commandInvoker.execute(pasteCommand);
        shapeIsSelected.setValue(false);
        pasteButton.defaultButtonProperty().setValue(false);
        pasteArea.setDisable(true);
        editingArea.toFront();
    }
    
    //------------------------------------------------------------------------//
    
    //---------------------------- MOVE AREA ---------------------------------//

    @FXML
    private void handleMouseReleasedOnMoveArea(MouseEvent event) {
        Point2D endPoint = new Point2D(event.getX(),event.getY());
        MoveCommand mc = new MoveCommand(selectedShape, new Point2D(event.getX() - points.get(0).getX(),event.getY()- points.get(0).getY()));
        commandInvoker.execute(mc);
        shapeIsSelected.setValue(false);
        moveButton.defaultButtonProperty().setValue(false);
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
    
    //------------------------------------------------------------------------//
    
    //--------------------- INSERTION POLYGON AREA ---------------------------//
    
    @FXML
    private void handleMouseClickedOnInsertPolygonArea(MouseEvent event) {
        points.add(new Point2D(event.getX(),event.getY()));
        insertionPoly.getPoints().add(event.getX());
        insertionPoly.getPoints().add(event.getY());
        if(points.size() > 1){
            if(abs(points.get(0).getX()-points.get(points.size()-1).getX())<20 && abs(points.get(0).getY()-points.get(points.size()-1).getY())<20){
                InsertCommand command = new InsertCommand(drawingArea, shapeToInsert,points, outlineColor.getValue(), fillingColor.getValue());
                commandInvoker.execute(command);
                shapeToInsert = shapeToInsert.nextDraw();
                insertPolygonArea.getChildren().remove(insertionPoly);
                insertionPoly = new Polyline();
                setInsertionPoly(insertionPoly);
                points.clear();
            }
        }  
    }

    @FXML
    private void handleMouseMovedOnInsertPolygonArea(MouseEvent event) {
        if(insertionPoly.getPoints().size() > points.size()*2){
            insertionPoly.getPoints().remove(insertionPoly.getPoints().size()-1);
            insertionPoly.getPoints().remove(insertionPoly.getPoints().size()-1);            
        }
        insertionPoly.getPoints().add(event.getX());
        insertionPoly.getPoints().add(event.getY());
    }
    
    //------------------------------------------------------------------------//
    
    //--------------------------- ZOOM AREA ----------------------------------//
    
    @FXML
    private void handleMouseDraggedOnZoomArea(MouseEvent event) {
        zoomManagement();
    }
    
    //------------------------------------------------------------------------//
    
    ////////////////////////////////////////////////////////////////////////////
    
    /////////////////////// EVENT HANDLER ON BUTTONS ///////////////////////////
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
    private void handleButtonActionMove(ActionEvent event) {
        if(moveButton.defaultButtonProperty().getValue()){
            moveButton.setDefaultButton(false);
        }
        else{
            moveButton.setDefaultButton(true);
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
    
    @FXML
    private void handleButtonActionMirror(ActionEvent event) {
        ArrayList<Point2D> points = new ArrayList<>();
        Point2D startPoint = selectedShape.getBounds().get(0);
        Point2D endPoint = selectedShape.getBounds().get(1);
        MirrorShapeCommand msc = new MirrorShapeCommand(drawingArea,selectedShape);
        commandInvoker.execute(msc);
        removeSelectionRectangle();
        insertSelectionRectangle();
    }

    @FXML
    private void handleButtonActionMoreStretchH(ActionEvent event) {
        double increment = 25;
        StretchHorizontalCommand shc = new StretchHorizontalCommand(selectedShape,increment); 
        commandInvoker.execute(shc);
        removeSelectionRectangle();
        insertSelectionRectangle();
    }

    @FXML
    private void handleButtonActionLessStretchH(ActionEvent event) {
        double increment = -25;
        StretchHorizontalCommand shc = new StretchHorizontalCommand(selectedShape,increment); 
        commandInvoker.execute(shc);
        removeSelectionRectangle();
        insertSelectionRectangle();
        
    }

    @FXML
    private void handleButtonActionMoreStretchV(ActionEvent event) {
        double increment = 25;
        StretchVerticalCommand svc = new StretchVerticalCommand(selectedShape,increment); 
        commandInvoker.execute(svc);
        removeSelectionRectangle();
        insertSelectionRectangle();
    }

    @FXML
    private void handleButtonActionLessStretchV(ActionEvent event) {
        double increment = -25;
        StretchVerticalCommand svc = new StretchVerticalCommand(selectedShape,increment); 
        commandInvoker.execute(svc);
        removeSelectionRectangle();
        insertSelectionRectangle();
    }

    @FXML
    private void handleButtonActionRotate(ActionEvent event) {
        double angle = 45;
        changeDimensionsButton.setDefaultButton(false);
        RotateCommand r = new RotateCommand(selectedShape,angle);
        commandInvoker.execute(r);
        shapeIsSelected.setValue(false);
        shapeIsSelected.setValue(true);
    }

    @FXML
    private void handleButtonActionText(ActionEvent event) {
        if(textButton.defaultButtonProperty().getValue()){
            textButton.setDefaultButton(false);
        }
        else{
            textButton.setDefaultButton(true);
        }
    }

    @FXML
    private void handleButtonActionInsertText(ActionEvent event) {
        String text = textField.getText();
        if(textField.getText().isEmpty())
            return;
        TextModel t = new TextModel();
        points.clear();
        
        Point2D p = new Point2D(350,50);
        points.add(p);
        InsertCommand ic = new InsertCommand(drawingArea,t,points,outlineColor.getValue(),fillingColor.getValue());
        commandInvoker.execute(ic);
        t.setText(text);
        textButton.setDefaultButton(false);
    }
    
    @FXML
    private void handleActionPolygonButton(ActionEvent event) {
        shapeToInsert = new PolygonModel();
        insertionPoly = new Polyline();
        setInsertionPoly(insertionPoly);
        points.clear();
    }
    
    @FXML
    private void handleActionChangeDimensions(ActionEvent event) {
        if(changeDimensionsButton.defaultButtonProperty().getValue()){   
            changeDimensionsButton.setDefaultButton(false);
        }
        else{
            editingArea.toBack();
            drawingArea.toBack();
            changeDimensionsButton.setDefaultButton(true);
        }
    }
    
    ////////////////////////////////////////////////////////////////////////////
    
    /////////////////////// EVENT HANDLER ON MENU ITEMS ////////////////////////

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
    
    ////////////////////////////////////////////////////////////////////////////
    
    ////////////////////// EVENT HANDLER ON COLOR PICKED ///////////////////////
    
    @FXML
    private void handleActionChangeOutlineColor(ActionEvent event) {
        if(shapeIsSelected.getValue()){
            ChangeOutlineColorCommand colorOutlineCommand = new ChangeOutlineColorCommand(selectedShape, outlineColor.getValue());
            commandInvoker.execute(colorOutlineCommand); 
        }
    }

    @FXML
    private void handleActionChangeFillingColor(ActionEvent event) {
        if(shapeIsSelected.getValue()){
            ChangeFillingColorCommand colorFillingCommand = new ChangeFillingColorCommand(selectedShape, fillingColor.getValue());
            commandInvoker.execute(colorFillingCommand); 
        }
    }
    
    ////////////////////////////////////////////////////////////////////////////
    
     //////////////////////// EVENT HANDLER ON TOOLBOX /////////////////////////
    
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
    private void handleActionGridSizeSlider(MouseEvent event) {
            drawingArea.setBackground(createGridBackground());
    }
    
    ////////////////////////////////////////////////////////////////////////////
    
    /////////////////////////////// USER METHODS ///////////////////////////////
    
    private void selectShape(Point2D selectPoint){
        Node actualNode = null;
        if(!selectShapeCheckBox.selectedProperty().getValue())
            return;
        shapeIsSelected.setValue(false);
        for(int i = drawingArea.getChildren().size()-1; i>=0; i--){
            actualNode = drawingArea.getChildren().get(i);
            if(actualNode.getBoundsInParent().contains(selectPoint)){
               selectedShape = (ShapeModel)actualNode; 
               outlineColor.setValue(selectedShape.getOutlineColor());
               fillingColor.setValue(selectedShape.getFillingColor());
               shapeIsSelected.setValue(true);
               return;
            }
        }
    }
    
    private void insertSelectionRectangle(){
        selectionRectangle = new RectangleModel();
        ((Shape)selectionRectangle).getStrokeDashArray().addAll(5d);
        ArrayList<Point2D> points = new ArrayList<>();
        Bounds b = ((Shape)selectedShape).getBoundsInParent();
        points.add(new Point2D(b.getMinX(),b.getMinY()));
        points.add(new Point2D(b.getMaxX(),b.getMaxY()));
        selectionRectangle.insert(editingArea, points, Color.GREY, Color.TRANSPARENT);
    }
    
    private void removeSelectionRectangle(){
        selectionRectangle.deleteShape(editingArea);
        selectionRectangle = null;
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
    
    private void zoomManagement(){
        Scale s = new Scale();
        s.setPivotX(0);
        s.setPivotY(0);

        double zoomLvl = zoomSlider.getValue()/8;
        zoomFactor = 1 + zoomLvl;

        s.setX(zoomFactor);
        s.setY(zoomFactor);
        
        parentArea.getTransforms().clear();
        parentArea.getTransforms().add(s);
        parentArea.setTranslateY(-scrollArea.getVvalue() * zoomSlider.getValue()/8);
        parentArea.setTranslateX(-scrollArea.getHvalue() * zoomSlider.getValue()/8);
    }
    
    private void setInsertionPoly(Polyline insertionPoly){
        insertionPoly.setStrokeWidth(2.0);
        insertionPoly.setStroke(outlineColor.getValue());
        insertPolygonArea.getChildren().add(insertionPoly);
    }
    
    public void setBinding(){
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
                //selectedShape = null;
            }
            else{
                if(!(selectedShape.getRotation() == 0 || selectedShape.getRotation() == 180)){
                    changeDimensionsButton.setDisable(true);
                    moreStretchVButton.setDisable(true);
                    moreStretchHButton.setDisable(true);
                    lessStretchVButton.setDisable(true);
                    lessStretchHButton.setDisable(true);
                    mirrorButton.setDisable(true);
                }
                else{
                    moreStretchVButton.setDisable(false);
                    moreStretchHButton.setDisable(false);
                    lessStretchVButton.setDisable(false);
                    lessStretchHButton.setDisable(false);
                    if(selectedShape.getClass().getSimpleName().equals(TextModel.class.getSimpleName())){
                        moreStretchVButton.setDisable(true);
                        moreStretchHButton.setDisable(true);
                        lessStretchVButton.setDisable(true);
                        lessStretchHButton.setDisable(true);
                    }
                    changeDimensionsButton.setDisable(false);
                    mirrorButton.setDisable(false);
                }
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
        
        textButton.armedProperty().addListener((o,oldVal,newVal) -> {
            if(newVal.booleanValue()){
                insertionArea.setDisable(true);
                insertPolygonArea.setDisable(true);
                //insertionShape = null;
                selectShapeCheckBox.setSelected(false);
            }
        });
        
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
                textButton.setDefaultButton(false);
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
                cdb.removeVertex(changeDimensionsArea);
            }
            else{
                cornerShapes = cdb.insertVertex(changeDimensionsArea,selectionRectangle);
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
        
        textBox.disableProperty().bind(textButton.defaultButtonProperty().not());
        textBox.visibleProperty().bind(textBox.disableProperty().not());
        undoButton.disableProperty().bind(commandInvoker.commandIn().not());
    }
    
    ////////////////////////////////////////////////////////////////////////////
 
}

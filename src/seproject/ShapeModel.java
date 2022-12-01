
package seproject;

import javafx.geometry.Point2D;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;

/**
 *
 * @author Group14
 */
public interface ShapeModel {
    
    public Point2D getStartPoint();
    
    public Point2D getEndPoint();
    
    public Color getOutlineColor();
    
    public Color getFillingColor();
    
    public void insert (AnchorPane drawingArea,Point2D starPoint, Point2D endPoint, Color outlineColor, Color fillingColor);
    
    public ShapeModel nextDraw ();
    
    public String saveOnFileString(String separator);
    
    public void setShapeParameters(Point2D startPoint,Point2D endPoint);
    
    default void changeDimensions(AnchorPane drawingArea,Point2D startPoint,Point2D endPoint){
        setShapeParameters(startPoint,endPoint);
    }

    public void move(Point2D translatePoint);
    
    default void deleteShape(AnchorPane drawingArea){
        drawingArea.getChildren().remove(this);
    }
    
    public void changeColor(Color outlineColor, Color fillingColor);

    public void pasteShape(AnchorPane drawingArea, Point2D startPoint);
}

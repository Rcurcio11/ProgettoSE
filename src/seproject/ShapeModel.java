
package seproject;

import java.util.ArrayList;
import javafx.geometry.Point2D;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;

/**
 *
 * @author Group14
 */
public interface ShapeModel {
    
    public Point2D getLowerBound();
    
    public Point2D getUpperBound();
    
    public Color getOutlineColor();
    
    public Color getFillingColor();
    
    public void insert (AnchorPane drawingArea, ArrayList<Point2D> points, Color outlineColor, Color fillingColor);
    
    public ShapeModel nextDraw ();
    
    public String saveOnFileString(String separator);
    
    public void setShapeParameters(ArrayList<Point2D> points);
    
    default void changeDimensions(ArrayList<Point2D> points){
        setShapeParameters(points);
    }

    public void move(Point2D translatePoint);
    
    default void deleteShape(AnchorPane drawingArea){
        drawingArea.getChildren().remove(this);
    }
    
    public void changeColor(Color outlineColor, Color fillingColor);

    public ShapeModel pasteShape(AnchorPane drawingArea, Point2D startPoint);
    
    public ArrayList<Point2D> getAllPoints();
    
    public ArrayList<Point2D> getBounds();
    
}

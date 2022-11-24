
package seproject;

import javafx.geometry.Point2D;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;

/**
 *
 * @author Group14
 */
public interface ShapeModel {
    
    public void insert (AnchorPane drawingArea,Point2D starPoint, Point2D endPoint);
    
    public ShapeModel nextDraw ();
    
    public void setColor (Color outlineColor, Color fillingColor);
    
    public String saveOnFileString(String separator);
}

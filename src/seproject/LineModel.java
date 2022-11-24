
package seproject;


import javafx.geometry.Point2D;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;



/**
*
* @author Group14
*/
public class LineModel extends Line implements ShapeModel {
    
   public LineModel() {
        super();
    }

    @Override
    public ShapeModel nextDraw() {
        return new LineModel();
    }
    
   @Override
    public void insert(AnchorPane drawingPane, Point2D startPoint,Point2D endPoint) {
        this.setStartX(startPoint.getX());
        this.setStartY(startPoint.getY());
        this.setEndX(endPoint.getX());
        this.setEndY(endPoint.getY());

        drawingPane.getChildren().add(this);
    }

    @Override
    public void setColor(Color outlineColor, Color fillingColor) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
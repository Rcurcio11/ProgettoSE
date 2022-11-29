
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
    public void insert(AnchorPane drawingPane, Point2D startPoint,Point2D endPoint, Color outlineColor, Color fillingColor) {
        setShapeParameters(startPoint,endPoint);
        this.setStroke(outlineColor);

        drawingPane.getChildren().add(this);
    }


    @Override
    public String saveOnFileString(String separator) {
        return this.getClass().getSimpleName() + separator + this.getStartX() + separator + this.getStartY() + separator + this.getEndX() + separator + this.getEndY() + separator + this.getStroke() + separator + this.getStroke() + separator;
    }

    @Override
    public Point2D getStartPoint() {
        return new Point2D(this.getStartX(),this.getStartY());
    }

    @Override
    public Point2D getEndPoint() {
        return new Point2D(this.getEndX(),this.getEndY());
    }

    @Override
    public void setShapeParameters(Point2D startPoint, Point2D endPoint) {
        this.setStartX(startPoint.getX());
        this.setStartY(startPoint.getY());
        this.setEndX(endPoint.getX());
        this.setEndY(endPoint.getY());
    }
    
}
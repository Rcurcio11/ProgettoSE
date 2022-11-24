
package seproject;

import static java.lang.Math.abs;
import javafx.geometry.Point2D;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 *
 * @author Group14
 */
public class RectangleModel extends Rectangle implements ShapeModel{

    public RectangleModel() {
        super();
    }

    @Override
    public ShapeModel nextDraw() {
        return new RectangleModel();
    }

    @Override
    public void insert(AnchorPane drawingPane, Point2D startPoint,Point2D endPoint, Color outlineColor) {
        if(startPoint.getX() > endPoint.getX())
            this.setX(endPoint.getX());
        else
            this.setX(startPoint.getX());
       
        if(startPoint.getY() > endPoint.getY())
            this.setY(endPoint.getY());
        else
            this.setY(startPoint.getY());
        
        double width = abs(startPoint.getX()-endPoint.getX());
        double height = abs(startPoint.getY()- endPoint.getY());
        
        this.setWidth(width);
        this.setHeight(height);
        
        this.setStroke(outlineColor);
        
        drawingPane.getChildren().add(this);
    }


    @Override
    public String saveOnFileString(String separator) {
        return this.getClass().getSimpleName() + separator + this.getX() + separator + this.getY() + separator + (this.getX() + this.getWidth()) + separator + (this.getY() + this.getHeight()) + separator + this.getStroke() + separator;
    }
}

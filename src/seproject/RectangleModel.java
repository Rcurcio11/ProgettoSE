
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
    public void insert(AnchorPane drawingPane, Point2D startPoint,Point2D endPoint, Color outlineColor, Color fillingColor) {
        setShapeParameters(startPoint,endPoint);
        
        this.setStroke(outlineColor);
        this.setFill(fillingColor);
        
        drawingPane.getChildren().add(this);
    }


    @Override
    public String saveOnFileString(String separator) {
        return this.getClass().getSimpleName() + separator + this.getX() + separator + this.getY() + separator + (this.getX() + this.getWidth()) + separator + (this.getY() + this.getHeight()) + separator + this.getStroke() + separator + this.getFill() + separator;
    }

    @Override
    public Point2D getStartPoint() {
        return new Point2D(this.getX(),this.getY());
    }

    @Override
    public Point2D getEndPoint() {
        return new Point2D(this.getX() + this.getWidth(),this.getY() + this.getHeight());
    }
    
    @Override 
    public Color getOutlineColor(){
        return (Color) this.getStroke();
    }
    
    @Override
    public Color getFillingColor(){
        return (Color) this.getFill();
    }
    
    @Override
    public void setShapeParameters(Point2D startPoint, Point2D endPoint) {
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
        this.setStrokeWidth(2.0);
    }

    @Override
    public void move(Point2D translatePoint) {
        double newX = this.getX() + translatePoint.getX();
        double newY = this.getY() + translatePoint.getY();
        this.setX(newX);
        this.setY(newY);
    
    }

    @Override
    public void pasteShape(AnchorPane drawingArea, Point2D startPoint) {
        RectangleModel toInsert = new RectangleModel();
        double newEndX = abs(startPoint.getX() + this.getWidth());
        double newEndY = abs(startPoint.getY()+ this.getHeight());
        Point2D endPoint = new Point2D(newEndX, newEndY);
        
       toInsert.setShapeParameters(startPoint, endPoint);
        toInsert.setStroke(this.getStroke());
        toInsert.setFill(this.getFill());
        
        drawingArea.getChildren().add(toInsert);
    }

    @Override
    public void changeColor(Color outlineColor, Color fillingColor) {
        this.setStroke(outlineColor);
        this.setFill(fillingColor);    
    }
}

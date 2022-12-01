
package seproject;

import static java.lang.Math.abs;
import javafx.geometry.Point2D;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;

/**
 *
 * @author Group14
 */

public class EllipseModel extends Ellipse implements ShapeModel{
    private Point2D startPoint;
    private Point2D endPoint;
    
    public EllipseModel() {
        super();
        startPoint = new Point2D(0,0);
        endPoint = new Point2D(0,0);
    }

    @Override
    public ShapeModel nextDraw() {
        return new EllipseModel();
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
    public void insert(AnchorPane drawingPane, Point2D startPoint,Point2D endPoint, Color outlineColor, Color fillingColor) {
        
        setShapeParameters(startPoint,endPoint);
        this.setStroke(outlineColor);
        this.setFill(fillingColor);
        drawingPane.getChildren().add(this);
    }


    @Override
    public String saveOnFileString(String separator) {
        return this.getClass().getSimpleName() + separator + startPoint.getX() + separator + startPoint.getY() + separator + endPoint.getX() + separator + endPoint.getY() + separator + this.getStroke() + separator + this.getFill() + separator;
    }

    @Override
    public Point2D getStartPoint() {
        return startPoint;
    }

    @Override
    public Point2D getEndPoint() {
        return endPoint;
    }

    @Override
    public void setShapeParameters(Point2D startPoint, Point2D endPoint) {
        this.startPoint = startPoint;
        this.endPoint = endPoint;
        double width = abs(endPoint.getX()-startPoint.getX());
        double height = abs(endPoint.getY()-startPoint.getY());
        double centerX = (startPoint.getX()+endPoint.getX())/2;
        double centerY = (startPoint.getY()+endPoint.getY())/2;

        this.setCenterX(centerX);
        this.setCenterY(centerY);
        this.setRadiusX(width/2);
        this.setRadiusY(height/2);
    }
}

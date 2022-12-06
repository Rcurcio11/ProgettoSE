
package seproject;

import static java.lang.Math.abs;
import java.util.ArrayList;
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
    public void insert(AnchorPane drawingPane, ArrayList<Point2D> points, Color outlineColor, Color fillingColor) {
        
        setShapeParameters(points);
        this.setStroke(outlineColor);
        this.setFill(fillingColor);
        drawingPane.getChildren().add(this);
    }


    @Override
    public String saveOnFileString(String separator) {
        return this.getClass().getSimpleName() + separator + 2 + separator + startPoint.getX() + separator + startPoint.getY() + separator + endPoint.getX() + separator + endPoint.getY() + separator + this.getStroke() + separator + this.getFill() + separator;
    }

    @Override
    public Point2D getLowerBound() {
        return startPoint;
    }

    @Override
    public Point2D getUpperBound() {
        return endPoint;
    }

    @Override
    public void setShapeParameters(ArrayList<Point2D> points) {
        this.startPoint = points.get(0);
        this.endPoint = points.get(1);
        double width = abs(this.endPoint.getX()-this.startPoint.getX());
        double height = abs(this.endPoint.getY()-this.startPoint.getY());
        double centerX = (this.startPoint.getX()+this.endPoint.getX())/2;
        double centerY = (this.startPoint.getY()+this.endPoint.getY())/2;

        this.setCenterX(centerX);
        this.setCenterY(centerY);
        this.setRadiusX(width/2);
        this.setRadiusY(height/2);
        this.setStrokeWidth(2.0);
    }

    @Override
    public void move(Point2D translatePoint) {
        double newStartX = this.getLowerBound().getX() + translatePoint.getX();
        double newStartY = this.getLowerBound().getY() + translatePoint.getY();
        double newEndX = abs(newStartX + this.getRadiusX()*2);
        double newEndY = abs(newStartY + this.getRadiusY()*2);
        this.setCenterX((newStartX + newEndX)/2);
        this.setCenterY((newStartY + newEndY)/2);
        this.setTranslateX(0);
        this.setTranslateY(0);
        this.startPoint = new Point2D(newStartX, newStartY);
        this.endPoint = new Point2D(newEndX, newEndY);
    }

    @Override
    public ShapeModel pasteShape(AnchorPane drawingArea, Point2D startPoint) {
        EllipseModel toInsert = new EllipseModel();
        ArrayList<Point2D> newPoints = new ArrayList<>();
        newPoints.add(startPoint);
        double newEndX = abs(startPoint.getX() + this.getRadiusX()*2);
        double newEndY = abs(startPoint.getY()+ this.getRadiusY()*2);
        newPoints.add(new Point2D(newEndX, newEndY));
        toInsert.setShapeParameters(newPoints);
        toInsert.setStroke(this.getStroke());
        toInsert.setFill(this.getFill());
        drawingArea.getChildren().add(toInsert);
        return toInsert;
    }

    @Override
    public void changeColor(Color outlineColor, Color fillingColor) {
        this.setStroke(outlineColor);
        this.setFill(fillingColor);    
    }


    @Override
    public ArrayList<Point2D> getAllPoints() {
        ArrayList<Point2D> points = new ArrayList<>();
        points.add(this.getLowerBound());
        points.add(this.getUpperBound());
        return points;
    }

    @Override
    public ArrayList<Point2D> getBounds() {
        return this.getAllPoints();
    }
}

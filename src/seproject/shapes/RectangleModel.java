
package seproject.shapes;

import static java.lang.Math.abs;
import java.util.ArrayList;
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
    public Point2D getLowerBound() {
        return new Point2D(this.getX() + this.getWidth(),this.getY() + this.getHeight());
    }

    @Override
    public Point2D getUpperBound() {
        return new Point2D(this.getX(),this.getY());
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
    public void insert(AnchorPane drawingArea, ArrayList<Point2D> points, Color outlineColor, Color fillingColor) {
        setShapeParameters(points);
        
        this.setStroke(outlineColor);
        this.setFill(fillingColor);
        
        drawingArea.getChildren().add(this);
    }

    @Override
    public ShapeModel nextDraw() {
        return new RectangleModel();
    }

    @Override
    public String saveOnFileString(String separator) {
        return this.getClass().getSimpleName() + separator + 2 + separator + this.getRotation() + separator + this.getX() + separator + this.getY() + separator + (this.getX() + this.getWidth()) + separator + (this.getY() + this.getHeight()) + separator + this.getStroke() + separator + this.getFill() + separator;
    }
    
    @Override
    public void setShapeParameters(ArrayList<Point2D> points) {
        //checking the point with minimum values ​​that will represent the starting point
        
        if(points.get(0).getX() > points.get(1).getX())
            this.setX(points.get(1).getX());
        else
            this.setX(points.get(0).getX());
       
        if(points.get(0).getY() > points.get(1).getY())
            this.setY(points.get(1).getY());
        else
            this.setY(points.get(0).getY());
        
        double width = abs(points.get(0).getX()-points.get(1).getX());
        double height = abs(points.get(0).getY()- points.get(1).getY());
        
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
    public void changeOutlineColor(Color outlineColor) {
        this.setStroke(outlineColor);
    }

    @Override
    public void changeFillingColor(Color fillingColor) {
        this.setFill(fillingColor); 
    }

    @Override
    public ShapeModel pasteShape(AnchorPane drawingArea, Point2D startPoint) {
        ArrayList<Point2D> newPoints = new ArrayList<>();
        newPoints.add(startPoint);
        RectangleModel toInsert = new RectangleModel();
        double newEndX = abs(startPoint.getX() + this.getWidth());
        double newEndY = abs(startPoint.getY()+ this.getHeight());
        newPoints.add(new Point2D(newEndX, newEndY));
  
        toInsert.setShapeParameters(newPoints);
        toInsert.setStroke(this.getStroke());
        toInsert.setFill(this.getFill());
        
        drawingArea.getChildren().add(toInsert);
        return toInsert;
    }

    @Override
    public ArrayList<Point2D> getAllPoints() {
        ArrayList<Point2D> points = new ArrayList<>();
        points.add(new Point2D(this.getX(),this.getY()));
        points.add(new Point2D(this.getX() + this.getWidth(),this.getY() + this.getHeight()));
        return points;
    }

    @Override
    public ArrayList<Point2D> getBounds() {
        return this.getAllPoints();
    }
    
    @Override
    public void rotate(double angle) {
        this.setRotate((this.getRotate() + angle) % 360);
    }

    @Override
    public double getRotation() {
        return this.getRotate();
    }
}

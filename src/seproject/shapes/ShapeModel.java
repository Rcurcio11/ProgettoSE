
package seproject.shapes;

import java.util.ArrayList;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;

/**
 *
 * @author Group14
 */
public interface ShapeModel {
    //this interface represents all the possible figures that can be inserted in the drawing area
    
    //method to get the point that represents the ending point of the figure
    public Point2D getLowerBound();
    
    //method to get the point that represents the starting point of the figure
    public Point2D getUpperBound();
    
    public Color getOutlineColor();
    
    public Color getFillingColor();
    
    public void insert (AnchorPane drawingArea, ArrayList<Point2D> points, Color outlineColor, Color fillingColor);
    
    //method that allows to insert more shapes of the same type without reconfirming the decision to insert that specific shape
    public ShapeModel nextDraw ();
    
    public String saveOnFileString(String separator);
    
    public void setShapeParameters(ArrayList<Point2D> points);
    
    default void changeDimensions(ArrayList<Point2D> points){
        double deg = this.getRotation() % 360;
        this.rotate(-deg);
        //System.out.println("chdm" +deg);
        if(deg == 0 || deg == 90 || deg == 180 || deg == 270)
            setShapeParameters(points);
        else
            this.rotate(deg);
    }

    public void move(Point2D translatePoint);
    
    default void deleteShape(AnchorPane drawingArea){
        drawingArea.getChildren().remove(this);
    }
    
    public void changeOutlineColor(Color outlineColor);
    
    public void changeFillingColor(Color fillingColor);

    public ShapeModel pasteShape(AnchorPane drawingArea, Point2D startPoint);
    
    //method that returns the most important points of the figure that allow to represent it completely
    public ArrayList<Point2D> getAllPoints();
    
    //method that returns the points that enclose the figure
    public ArrayList<Point2D> getBounds();
    
    default void mirrorShape(){
        double deg = this.getRotation() % 360;
        this.rotate(-deg);
        ArrayList<Point2D> points = new ArrayList<>();
        Point2D startPoint = this.getBounds().get(0);
        Point2D endPoint = this.getBounds().get(1);
        double width = 0;
        double height = 0;
        if(deg == 180 || deg == 0){
            width = endPoint.getX() - startPoint.getX();
            points.add(new Point2D(endPoint.getX() + width,startPoint.getY()));
            points.add(new Point2D(endPoint.getX(),endPoint.getY()));
        
            this.changeDimensions(points);
            this.move(new Point2D(-width,-height));
        }
        
        this.rotate(deg);
        
    }    
    
    default void stretchHorizontal(double increment){
        ArrayList<Point2D> points = new ArrayList<>();
        Point2D startPoint = new Point2D(this.getUpperBound().getX() - increment/2,this.getUpperBound().getY());
        Point2D endPoint = new Point2D(this.getLowerBound().getX() + increment/2,this.getLowerBound().getY());
        if(startPoint.getX()  < endPoint.getX()){
            points.add(startPoint);
            points.add(this.getLowerBound());
            this.changeDimensions(points);
            points.clear();
            points.add(this.getUpperBound());
            points.add(endPoint);
            this.changeDimensions(points);
        }
    }
    
    default void stretchVertical(double increment){
        ArrayList<Point2D> points = new ArrayList<>();
        Point2D startPoint = new Point2D(this.getUpperBound().getX(),this.getUpperBound().getY() - increment/2);
        Point2D endPoint = new Point2D(this.getLowerBound().getX(),this.getLowerBound().getY() + increment/2);
        if(startPoint.getY()  < endPoint.getY()){
            points.add(startPoint);
            points.add(this.getLowerBound());
            this.changeDimensions(points);
            points.clear();
            points.add(this.getUpperBound());
            points.add(endPoint);
            this.changeDimensions(points);
        }
    }
    
    //method that returns the central point of the figure on which it is rotated
    default Point2D getCenterPoint() {
        double width = this.getLowerBound().getX() - this.getUpperBound().getX();
        double height = this.getLowerBound().getY() - this.getUpperBound().getY();
        
        return new Point2D(this.getUpperBound().getX() + width/2, this.getUpperBound().getY() + height/2);
    }
    
    public void rotate(double angle);
    
    public double getRotation();
}


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
    
    default void mirrorShape(){
        ArrayList<Point2D> points = new ArrayList<>();
        Point2D startPoint = this.getBounds().get(0);
        Point2D endPoint = this.getBounds().get(1);
        double width = endPoint.getX() - startPoint.getX();
        points.add(new Point2D(endPoint.getX() + width,startPoint.getY()));
        points.add(new Point2D(endPoint.getX(),endPoint.getY()));
        
        this.changeDimensions(points);
        this.move(new Point2D(-width,0));
    }    
    
    default void stretchHorizontal(double increment){
        ArrayList<Point2D> points = new ArrayList<>();
        Point2D startPoint = new Point2D(this.getUpperBound().getX() - increment/2,this.getUpperBound().getY());
        Point2D endPoint = new Point2D(this.getLowerBound().getX() + increment/2,this.getLowerBound().getY());
        if(startPoint.getX() + increment/2 < endPoint.getX()){
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
        if(startPoint.getY() + increment/2 < endPoint.getY()){
            points.add(startPoint);
            points.add(this.getLowerBound());
            this.changeDimensions(points);
            points.clear();
            points.add(this.getUpperBound());
            points.add(endPoint);
            this.changeDimensions(points);
        }
    }
}

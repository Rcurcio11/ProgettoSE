
package seproject.shapes;

import static java.lang.Math.abs;
import java.util.ArrayList;
import javafx.geometry.Point2D;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

/**
 *
 * @author Gruppo14
 */
public class PolygonModel extends Polygon implements ShapeModel{
    private ArrayList<Point2D> points;

    public PolygonModel() {
        points = new ArrayList<>();
    }

    @Override
    public Point2D getLowerBound() {
        double maxX = points.get(0).getX();
        double maxY = points.get(0).getY();
        for(Point2D point : this.points){
            if(point.getX()> maxX)
                maxX = point.getX();
            
            if(point.getY()> maxY)
                maxY = point.getY();      
        }
        return new Point2D(maxX, maxY);
    }

    @Override
    public Point2D getUpperBound() {
        double minX = points.get(0).getX();
        double minY = points.get(0).getY();
        for(Point2D point : this.points){
            if(point.getX()< minX)
                minX = point.getX();
            
            if(point.getY()< minY)
                minY = point.getY();    
        }
        return new Point2D(minX, minY);
    }

    @Override
    public Color getOutlineColor() {
        return (Color) this.getStroke();
    }

    @Override
    public Color getFillingColor() {
        return (Color) this.getFill();
    }

    @Override
    public void insert(AnchorPane drawingArea, ArrayList<Point2D> points, Color outlineColor, Color fillingColor) {
        //checking if there are enough points to build the polygon
        if(points.size() != 1){
            setShapeParameters(points);
            this.setStroke(outlineColor);
            this.setFill(fillingColor);
            drawingArea.getChildren().add(this);
        }
    }

    @Override
    public ShapeModel nextDraw() {
        if(this.points.isEmpty())
            return this;
        else
            return new PolygonModel();
    }

    @Override
    public String saveOnFileString(String separator) {
        int index = this.points.size();
        String fileString = this.getClass().getSimpleName() + separator + index + separator + this.getRotation() + separator;
        for(Point2D point : points){
            fileString += point.getX() + separator;
            fileString += point.getY() + separator;
        }
        fileString += this.getStroke() + separator + this.getFill() + separator;
        return fileString;
    }

    @Override
    public void setShapeParameters(ArrayList<Point2D> points) {
        this.getPoints().clear();
        this.points.clear();
        
        //checking if the end of the polygon has been reached
        if(abs(points.get(0).getX()-points.get(points.size()-1).getX())<20 && abs(points.get(0).getY()-points.get(points.size()-1).getY())<20){
            for(int i = 0; i < points.size()-1; i++){
                this.points.add(points.get(i));
                this.getPoints().add(points.get(i).getX());
                this.getPoints().add(points.get(i).getY());
            }
            this.points.add(points.get(0));
            this.getPoints().add(points.get(0).getX());
            this.getPoints().add(points.get(0).getY());
            this.setStrokeWidth(2.0);
        }
    }

    @Override
    public void move(Point2D translatePoint) {
        ArrayList<Point2D> newPoints = new ArrayList<>();
        for(Point2D point : this.points){
            double pointX = point.getX() + translatePoint.getX();
            double pointY = point.getY() + translatePoint.getY();
            newPoints.add(new Point2D(pointX, pointY));
        }
        this.setShapeParameters(newPoints);
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
        PolygonModel toInsert = new PolygonModel();
        ArrayList<Point2D> newPoints = new ArrayList<>();
        double offsetX = startPoint.getX() - this.points.get(0).getX();
        double offsetY = startPoint.getY() - this.points.get(0).getY();
        for(Point2D point : this.points){
            double pointX = point.getX() + offsetX;
            double pointY = point.getY() + offsetY;
            newPoints.add(new Point2D(pointX, pointY));
        }
        newPoints.add(startPoint);
        toInsert.setShapeParameters(newPoints);
        toInsert.setStroke(this.getStroke());
        toInsert.setFill(this.getFill());
        drawingArea.getChildren().add(toInsert);
        return toInsert;
    }

    @Override
    public ArrayList<Point2D> getAllPoints() {
        return (ArrayList<Point2D>) this.points.clone();
    }

    @Override
    public ArrayList<Point2D> getBounds() {
        ArrayList<Point2D> boundsPoint = new ArrayList<>();
        boundsPoint.add(this.getUpperBound());
        boundsPoint.add(this.getLowerBound());
        return boundsPoint;
    }
    
    @Override
    public void changeDimensions(ArrayList<Point2D> points){
        double deg = this.getRotation();       
        if(!(deg == 0 || deg == 180)){
            return;
        }
        this.rotate(-deg);
        
        ArrayList<Point2D> newPoints = new ArrayList<>();
        Point2D newStartPoint = points.get(0);
        Point2D newEndPoint = points.get(1);
        
        //saving the old parameters
        ArrayList<Point2D> oldBounds = this.getBounds();        
        double oldStartX = oldBounds.get(0).getX();
        double oldStartY = oldBounds.get(0).getY();
        double oldEndX = oldBounds.get(1).getX();
        double oldEndY = oldBounds.get(1).getY();
        
        double oldHeight = oldEndY - oldStartY;
        double oldWidth = oldEndX - oldStartX;
        double newWidth = newEndPoint.getX() - newStartPoint.getX();
        double newHeight = newEndPoint.getY() - newStartPoint.getY();
        for(Point2D p : this.getAllPoints()){
            Point2D newPoint;
            double oldXposition = p.getX() - oldStartX;
            double oldYposition = p.getY() - oldStartY;
            
            double newX = (oldXposition*newWidth)/oldWidth + newStartPoint.getX();
            double newY = (oldYposition*newHeight)/oldHeight + newStartPoint.getY();
            
            newPoint = new Point2D(newX,newY);
            newPoints.add(newPoint);
        }        
        this.setShapeParameters(newPoints);
        this.rotate(deg);
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

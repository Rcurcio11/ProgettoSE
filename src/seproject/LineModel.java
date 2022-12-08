
package seproject;


import java.util.ArrayList;
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
    public void insert(AnchorPane drawingPane, ArrayList<Point2D> points, Color outlineColor, Color fillingColor) {
        setShapeParameters(points);
        
        this.setStroke(outlineColor);
        this.setStrokeWidth(2.0);

        drawingPane.getChildren().add(this);
    }


    @Override
    public String saveOnFileString(String separator) {
        return this.getClass().getSimpleName() + separator + 2 + separator + this.getStartX() + separator + this.getStartY() + separator + this.getEndX() + separator + this.getEndY() + separator + this.getStroke() + separator + this.getStroke() + separator;
    }

    @Override
    public Point2D getLowerBound() {
        return new Point2D(this.getStartX(),this.getStartY());
    }

    @Override
    public Point2D getUpperBound() {
        return new Point2D(this.getEndX(),this.getEndY());
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
    public void setShapeParameters(ArrayList<Point2D> points) {
        if(points.get(0).getX() > points.get(1).getX()){
            this.setStartX(points.get(1).getX());
            this.setStartY(points.get(1).getY());
            this.setEndX(points.get(0).getX());
            this.setEndY(points.get(0).getY());
        }else{
            this.setStartX(points.get(0).getX());
            this.setStartY(points.get(0).getY());
            this.setEndX(points.get(1).getX());
            this.setEndY(points.get(1).getY());
        }
    }

    @Override
    public void move(Point2D translatePoint) {
        
        double newX = translatePoint.getX() + this.getStartX();
        double newY = translatePoint.getY() + this.getStartY();
        this.setStartX(newX);
        this.setStartY(newY);
        double endX = translatePoint.getX() + this.getEndX();
        double endY = translatePoint.getY() + this.getEndY();
        
  
        this.setEndX(endX);
        this.setEndY(endY);
        
        this.setTranslateX(0);
        this.setTranslateY(0);

    }

    @Override
    public ShapeModel pasteShape(AnchorPane drawingArea, Point2D startPoint) {
        ArrayList<Point2D> newPoints = new ArrayList<>();
        newPoints.add(startPoint);
        double endX = startPoint.getX() + this.getLayoutBounds().getWidth();
        double endY;
        if(this.getStartY() > this.getEndY())
            endY = startPoint.getY() - this.getLayoutBounds().getHeight();
        else 
            endY = startPoint.getY() + this.getLayoutBounds().getHeight();
        
        newPoints.add(new Point2D(endX, endY));
        LineModel toInsert = new LineModel();
        toInsert.setShapeParameters(newPoints); 
        toInsert.setStroke(this.getStroke());

        drawingArea.getChildren().add(toInsert);
        return toInsert;
    }

    @Override
    public void changeColor(Color outlineColor, Color fillingColor) {
        this.setStroke(outlineColor);    
    }   

    @Override
    public ArrayList<Point2D> getAllPoints() {
        ArrayList<Point2D> points = new ArrayList<>();
        points.add(new Point2D(this.getStartX(),this.getStartY()));
        points.add(new Point2D(this.getEndX(),this.getEndY()));
        return points;
    }

    @Override
    public ArrayList<Point2D> getBounds() {
        return this.getAllPoints();
    }
    
    
}
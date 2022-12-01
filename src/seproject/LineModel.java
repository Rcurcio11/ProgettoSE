
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
        if(startPoint.getX() > endPoint.getX()){
            setShapeParameters(endPoint,startPoint);
        }else{
            setShapeParameters(startPoint,endPoint);
        }
        
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
    public Color getOutlineColor(){
        return (Color) this.getStroke();
    }
    
    @Override
    public Color getFillingColor(){
        return (Color) this.getFill();
    }
    
    @Override
    public void setShapeParameters(Point2D startPoint, Point2D endPoint) {
        this.setStartX(startPoint.getX());
        this.setStartY(startPoint.getY());
        this.setEndX(endPoint.getX());
        this.setEndY(endPoint.getY());
        this.setStrokeWidth(2.0);
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
    public void pasteShape(AnchorPane drawingArea, Point2D startPoint) {

        double endX = startPoint.getX() + this.getLayoutBounds().getWidth();
        double endY;
        if(this.getStartY() > this.getEndY())
            endY = startPoint.getY() - this.getLayoutBounds().getHeight();
        else 
            endY = startPoint.getY() + this.getLayoutBounds().getHeight();
        
        Point2D endPoint = new Point2D(endX, endY);
        LineModel toInsert = new LineModel();
        toInsert.setShapeParameters(startPoint, endPoint);
        toInsert.setStroke(this.getStroke());

        drawingArea.getChildren().add(toInsert);
    }

    @Override
    public void changeColor(Color outlineColor, Color fillingColor) {
        this.setStroke(outlineColor);    
    }   
}
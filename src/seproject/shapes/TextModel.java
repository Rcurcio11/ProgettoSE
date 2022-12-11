
package seproject.shapes;

import java.util.ArrayList;
import javafx.geometry.Point2D;
import javafx.geometry.VPos;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 *
 * @author Group14
 */
public class TextModel extends Text implements ShapeModel {
    
    public TextModel(){
        super();
    }

    @Override
    public Point2D getLowerBound() {
        return new Point2D(this.getBoundsInParent().getMaxX(),this.getBoundsInParent().getMaxY());
    }

    @Override
    public Point2D getUpperBound() {
        return new Point2D(this.getBoundsInParent().getMinX(),this.getBoundsInParent().getMinY());
    }

    @Override
    public Color getOutlineColor() {
        return (Color)this.getStroke();
    }

    @Override
    public Color getFillingColor() {
        return (Color) this.getFill();
    }

    @Override
    public void insert(AnchorPane drawingArea, ArrayList<Point2D> points, Color outlineColor, Color fillingColor) {
        this.setFill(fillingColor);
        this.setStroke(outlineColor);
        drawingArea.getChildren().add(this);
        this.setShapeParameters(points);   
    }
    
    @Override
    public ShapeModel nextDraw() {
        return new TextModel();
    }

    @Override
    public String saveOnFileString(String separator) {
        return TextModel.class.getSimpleName()  + separator + 1 + separator + getRotation() + separator + this.getUpperBound().getX() + separator + this.getUpperBound().getY() + separator + this.getOutlineColor() + separator + this.getFillingColor() + separator + this.getText() + separator + this.getFont().getSize() + separator;
    }

    @Override
    public void setShapeParameters(ArrayList<Point2D> points) {
        double nX = points.get(0).getX();
        double nY = points.get(0).getY();
        if(points.size() > 1){
            Point2D endPoint = points.get(1);
            double width = this.getBoundsInParent().getWidth();
            double newWidth = endPoint.getX() - this.getX();
            double newFont = (this.getFont().getSize()*newWidth)/width;
            double scale = (newWidth/width);
            this.setFont(new Font(newFont));
        }
        this.setX(nX);
        this.setY(nY);
        this.setTextOrigin(VPos.TOP);
    }
    
    @Override
    public void changeDimensions(ArrayList<Point2D> points){
        double deg = this.getRotation() % 360;
        this.rotate(-deg);
        //System.out.println("chdm" +deg);
        if(deg == 0)
            setShapeParameters(points);
        else
            this.rotate(deg);
    }

    @Override
    public void move(Point2D translatePoint) {
        Point2D startPoint = this.getUpperBound();
        ArrayList<Point2D> points = new ArrayList<>();
        points.add(new Point2D(startPoint.getX() + translatePoint.getX(), startPoint.getY() + translatePoint.getY()));
        this.setShapeParameters(points);
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
        newPoints.add(0,startPoint);
        TextModel toInsert = new TextModel();
        toInsert.setShapeParameters(newPoints);
        toInsert.setStroke(this.getStroke());
        toInsert.setFill(this.getFill());
        toInsert.setText(this.getText());
        toInsert.setFont(this.getFont().getSize());
        drawingArea.getChildren().add(toInsert);
        return toInsert;
    }

    @Override
    public ArrayList<Point2D> getAllPoints() {
        return this.getBounds();
    }

    @Override
    public ArrayList<Point2D> getBounds() {
        ArrayList<Point2D> points = new ArrayList<>();
        points.add(this.getUpperBound());
        points.add(this.getLowerBound());
        return points;
    }
    
    @Override 
    public void mirrorShape(){
        this.setScaleX(this.getScaleX()*-1);
    }
    
    @Override
    public void stretchHorizontal(double increment){
     /*double scaleX = this.getScaleX() + increment/20;
        if(scaleX > 0)
            this.setScaleX(scaleX); */  
    }
    
    @Override
    public void stretchVertical(double increment){
        /*double scaleY = this.getScaleY() + increment/20;
        if(scaleY > 0)
            this.setScaleY(scaleY);*/
    }

    @Override
    public void rotate(double angle) {
        this.setRotate((this.getRotation() + angle)%360);
    }

    @Override
    public double getRotation() {
        return this.getRotate();
    }
    
    //// Additional methods specific to the TextModel 
    
    public void setContent(String text){
        this.setText(text);
    }
    
    public void setFont(double fontSize){
        this.setFont(new Font(fontSize));
    }
}

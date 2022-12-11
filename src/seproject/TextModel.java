
package seproject;

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
        //System.out.println("txt insert " +points);
        //  this.setText();
        //this.setBackground(Background.EMPTY);
        //Point2D endPoint = points.remove(1);
        drawingArea.getChildren().add(this);
        this.setShapeParameters(points);
        
        //System.out.println("insert: "+this.getBounds());
        //System.out.println(this.getBoundsInParent());
        //this.setEditable(true);
        //Text t = new Text();    }
        
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
        //double scaleX = this.getScaleX();
        //double scaleY = this.getScaleY();
        //this.setScaleX(1);
        //this.setScaleY(1);
        double nX = points.get(0).getX();
        double nY = points.get(0).getY();
        //System.out.println("points\n"+points);
        if(points.size() > 1){
            Point2D endPoint = points.get(1);
            
            double scale = this.getScaleX();
            double width = this.getBoundsInParent().getWidth();
            double newWidth = endPoint.getX() - this.getX();
            double newFont = (this.getFont().getSize()*newWidth)/width;
            //System.out.println("width&font" + newWidth + " " + width + " " + this.getFont().getSize() + " " + newFont);
            scale = (newWidth/width);
            /*if(newWidth < 0 && scale > 1)
                this.setScaleX(-scale);
            else
                this.setScaleX(+scale);*/
            //this.setScaleX(newFont/this.getFont().getSize());
            this.setFont(new Font(newFont));
            //this.setScaleY(((this.getBoundsInParent().getHeight())/(points.get(1).getY() - this.getY())));
        }
        this.setX(nX);
        this.setY(nY);
        //this.setFont(new Font((points.get(1).getX() - this.getX())/10));
        this.setTextOrigin(VPos.TOP);
        //System.out.println("font: " + this.getBounds());
        //this.setScaleX(scaleX);
        //this.setScaleY(scaleY);
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
        //double newEndX = abs(startPoint.getX() + this.getWidth());
        //double newEndY = abs(startPoint.getY()+ this.getHeight());
        //newPoints.add(new Point2D(newEndX, newEndY));
        toInsert.setShapeParameters(newPoints);
        //toInsert.setX(startPoint.getX());
        //toInsert.setY(startPoint.getY());
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
    public void rotate(double angle) {
        this.setRotate((this.getRotation() + angle)%360);
    }

    @Override
    public double getRotation() {
        return this.getRotate();
    }
    
    public void setContent(String text){
        this.setText(text);
    }
    
    public void setFont(double fontSize){
        this.setFont(new Font(fontSize));
    }
    
    @Override 
    public void mirrorShape(){
        this.setScaleX(this.getScaleX()*-1);
    }
    
    @Override
    public void stretchVertical(double increment){
        /*double scaleY = this.getScaleY() + increment/20;
        if(scaleY > 0)
            this.setScaleY(scaleY);*/
    }
    
    @Override
    public void stretchHorizontal(double increment){
     /*double scaleX = this.getScaleX() + increment/20;
        if(scaleX > 0)
            this.setScaleX(scaleX); */  
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
}

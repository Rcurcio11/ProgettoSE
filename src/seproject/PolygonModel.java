
package seproject;

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
        double minX = 663;      //change
        double minY = 479;      //change
        for(Point2D point : this.points){
            if(point.getX()< minX)
                minX = point.getX();
            
            if(point.getY()< minY)
                minY = point.getY();
            
        }
        return new Point2D(minX, minY);
    }

    @Override
    public Point2D getUpperBound() {
        double maxX = -1;
        double maxY = -1;
        for(Point2D point : this.points){
            if(point.getX()> maxX)
                maxX = point.getX();
            
            if(point.getY()> maxY)
                maxY = point.getY();
            
        }
        return new Point2D(maxX, maxY);
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
        //System.out.println(points.size());
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
        String fileString = this.getClass().getSimpleName() + separator + index + separator;
        for(Point2D point : points){
            fileString += point.getX() + separator;
            fileString += point.getY() + separator;
        }
        //fileString += points.get(0).getX() + separator + points.get(0).getY() + separator;
        fileString += this.getStroke() + separator + this.getFill() + separator;
        return fileString;
    }

    @Override
    public void setShapeParameters(ArrayList<Point2D> points) {
        //System.out.println("polyup: " +points.size());
        this.getPoints().clear();
        this.points.clear();
        //System.out.println("poly: " + points.size());
        //System.out.println("size: "+ this.getPoints().size());
        if(abs(points.get(0).getX()-points.get(points.size()-1).getX())<20 && abs(points.get(0).getY()-points.get(points.size()-1).getY())<20){
            for(int i = 0; i < points.size()-1; i++){
                //System.out.println("point: " + points.get(i));
                this.points.add(points.get(i));
                this.getPoints().add(points.get(i).getX());
                this.getPoints().add(points.get(i).getY());
            }
            //System.out.println("point: " + points.get(0));
            this.points.add(points.get(0));
            this.getPoints().add(points.get(0).getX());
            this.getPoints().add(points.get(0).getY());
            this.setStrokeWidth(2.0);
            //System.out.println("here");
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
        //System.out.println(this.points.size() + " " + newPoints.size());
        //newPoints.add(new Point2D((points.get(0).getX()+translatePoint.getX()), (points.get(0).getY()+translatePoint.getY())));
        //this.getPoints().clear();
        //this.points.clear();
        this.setShapeParameters(newPoints);
    }

    @Override
    public void changeColor(Color outlineColor, Color fillingColor) {
        this.setStroke(outlineColor);
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
        boundsPoint.add(this.getLowerBound());
        boundsPoint.add(this.getUpperBound());
        return boundsPoint;
    }
    
    @Override
    public void changeDimensions(ArrayList<Point2D> points){
        /*
        How to read
        o: old, n: new, p: position, P: point, h: height, w: width
        e.g. oh -> old height
        */
        ArrayList<Point2D> newPoints = new ArrayList<>();
        Point2D newStartPoint = points.get(0);
        Point2D newEndPoint = points.get(1);
        ArrayList<Point2D> oldBounds = this.getBounds();        
        double osX = oldBounds.get(0).getX();
        double osY = oldBounds.get(0).getY();
        double oeX = oldBounds.get(1).getX();
        double oeY = oldBounds.get(1).getY();
        
        double oh = oeY - osY;
        double ow = oeX - osX;
        double nw = newEndPoint.getX() - newStartPoint.getX();
        double nh = newEndPoint.getY() - newStartPoint.getY();
        //System.out.println(points.get(0) + " " + points.get(1));
        //System.out.println(newStartPoint + " " + newEndPoint);
        //System.out.println(oldBounds.get(0) + " " + oldBounds.get(1)+"\n");
                
        //System.out.println(osX + " " + osY + " " + oeX + " " + oeY);
        //System.out.println(oh + " " + ow + " " + nh + " " + nw);
        for(Point2D p:this.getAllPoints()){
            Point2D nP;
            
            double nX = p.getX(),nY = p.getY();
            //System.out.println("before: " + nX + " " + nY);
            double oxp = p.getX() - osX;
            double oyp = p.getY() - osY;
            //System.out.println(": " + oxp + " " + oyp);
            
            nX = (oxp*nw)/ow + newStartPoint.getX();
            nY = (oyp*nh)/oh + newStartPoint.getY();
            
            //System.out.println("after: " + nX + " " + nY);
            nP = new Point2D(nX,nY);
            newPoints.add(nP);
        }        
        this.setShapeParameters(newPoints);
        //System.out.println("chdim: " + this.points.size());
    }  
}

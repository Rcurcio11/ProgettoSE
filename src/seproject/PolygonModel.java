/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package seproject;

import static java.lang.Math.abs;
import java.util.ArrayList;
import javafx.geometry.Point2D;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

/**
 *
 * @author uondi
 */
public class PolygonModel extends Polygon implements ShapeModel{
    private ArrayList<Point2D> points;

    public PolygonModel() {
        points = new ArrayList<>();
    }

    @Override
    public Point2D getLowerBound() {
        double minX = 663;
        double minY = 479;
        for(Point2D point : this.points){
            if(point.getX()< minX)
                minX = point.getX();
            
            if(point.getY()< minY)
                minY = point.getY();
            
        }
        return new Point2D(minX-1, minY-1);
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
        return new Point2D(maxX+1, maxY+1);
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
        if(points.size() != 1){
            if(abs(points.get(0).getX()-points.get(points.size()-1).getX())<20 && abs(points.get(0).getY()-points.get(points.size()-1).getY())<20){
                setShapeParameters(points);
                this.setStroke(outlineColor);
                this.setFill(fillingColor);
        
                drawingArea.getChildren().add(this);
                
            }
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
        int index = this.points.size()+1;
        String fileString = this.getClass().getSimpleName() + separator + index + separator;
        for(Point2D point : points){
            fileString += point.getX() + separator;
            fileString += point.getY() + separator;
        }
        fileString += points.get(0).getX() + separator + points.get(0).getY() + separator;
        fileString += this.getStroke() + separator + this.getFill() + separator;
        return fileString;
    }

    @Override
    public void setShapeParameters(ArrayList<Point2D> points) {
        for(int i = 0; i < points.size()-1; i++){
            this.points.add(points.get(i));
            this.getPoints().add(points.get(i).getX());
            this.getPoints().add(points.get(i).getY());
        }
        this.setStrokeWidth(2.0);
    }

    @Override
    public void move(Point2D translatePoint) {
        ArrayList<Point2D> newPoints = new ArrayList<>();
        for(Point2D point : this.points){
            double pointX = point.getX() + translatePoint.getX();
            double pointY = point.getY() + translatePoint.getY();
            newPoints.add(new Point2D(pointX, pointY));
        }
        newPoints.add(new Point2D((points.get(0).getX()+translatePoint.getX()), (points.get(0).getY()+translatePoint.getY())));
        this.getPoints().clear();
        this.points.clear();
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
        return this.points;
    }

    @Override
    public ArrayList<Point2D> getBounds() {
        ArrayList<Point2D> boundsPoint = new ArrayList<>();
        boundsPoint.add(this.getLowerBound());
        boundsPoint.add(this.getUpperBound());
        return boundsPoint;
    }
    
}

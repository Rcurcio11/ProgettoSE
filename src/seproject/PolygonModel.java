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
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Point2D getUpperBound() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Color getOutlineColor() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Color getFillingColor() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
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
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
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
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void changeColor(Color outlineColor, Color fillingColor) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public ShapeModel pasteShape(AnchorPane drawingArea, Point2D startPoint) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public ArrayList<Point2D> getAllPoints() {
        return this.points;
    }

    @Override
    public ArrayList<Point2D> getBounds() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}

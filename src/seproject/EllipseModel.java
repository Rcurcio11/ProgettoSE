/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package seproject;

import static java.lang.Math.abs;
import javafx.geometry.Point2D;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;

/**
 *
 * @author Group14
 */

public class EllipseModel extends Ellipse implements ShapeModel{
    private Point2D startPoint;
    private Point2D endPoint;
    
    public EllipseModel() {
        super();
        startPoint = new Point2D(0,0);
        endPoint = new Point2D(0,0);
    }

    @Override
    public ShapeModel nextDraw() {
        return new EllipseModel();
    }

    @Override
    public void insert(AnchorPane drawingPane, Point2D startPoint,Point2D endPoint) {
        this.startPoint = startPoint;
        this.endPoint = endPoint;
        double width = abs(endPoint.getX()-startPoint.getX());
        double height = abs(endPoint.getY()-startPoint.getY());
        double centerX = (startPoint.getX()+endPoint.getX())/2;
        double centerY = (startPoint.getY()+endPoint.getY())/2;

        this.setCenterX(centerX);
        this.setCenterY(centerY);
        this.setRadiusX(width/2);
        this.setRadiusY(height/2);

        drawingPane.getChildren().add(this);
    }

    @Override
    public void setColor(Color outlineColor, Color fillingColor) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public String saveOnFileString(String separator) {
        return this.getClass().getSimpleName() + separator + startPoint.getX() + separator + startPoint.getY() + separator + endPoint.getX() + separator + endPoint.getY() + separator;
    }
}

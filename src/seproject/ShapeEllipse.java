/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package seproject;

import static java.lang.Math.abs;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Ellipse;

/**
 *
 * @author Group14
 */

public class ShapeEllipse extends DrawShape{
    
    private Ellipse ellipse;

    public ShapeEllipse() {
        ellipse = new Ellipse();
    }

    @Override
    public void nextDraw() {
        ellipse = new Ellipse();
    }

    @Override
    public void insert(AnchorPane drawingPane, double prevX, double prevY, double lastX, double lastY) {
        
        double width = abs(lastX-prevX);
        double height = abs(lastY-prevY);
        double centerX = (lastX+prevX)/2;
        double centerY = (lastY+prevY)/2;

        ellipse.setCenterX(centerX);
        ellipse.setCenterY(centerY);
        ellipse.setRadiusX(width/2);
        ellipse.setRadiusY(height/2);

        drawingPane.getChildren().add(ellipse);
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package seproject;

import javafx.geometry.Point2D;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author Rosario
 */
public class ChangeShapeDimensionsCommand implements OperationCommand{

    private AnchorPane drawingArea;
    private Point2D startPoint;
    private Point2D endPoint;
    private ShapeModel shape;  

    public ChangeShapeDimensionsCommand(AnchorPane drawingArea, Point2D startPoint, Point2D endPoint, ShapeModel shape) {
        this.drawingArea = drawingArea;
        this.startPoint = startPoint;
        this.endPoint = endPoint;
        this.shape = shape;
    }
    
    @Override
    public void execute() throws GenericDrawException {
        shape.changeDimensions(drawingArea, startPoint, endPoint);
    }
    
}

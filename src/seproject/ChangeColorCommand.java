/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package seproject;

import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;

/**
 *
 * @author Group14
 */
public class ChangeColorCommand implements OperationCommand {
    
    private ShapeModel shapeSelected;
    private Color outlineColor;
    private Color fillingColor;
    
    public ChangeColorCommand(Shape shapeSelected, Color outlineColor, Color fillingColor) {
        this.shapeSelected = (ShapeModel) shapeSelected;
        this.outlineColor = outlineColor;
        this.fillingColor = fillingColor;
    }
    
    @Override
    public void execute() throws GenericDrawException {
        shapeSelected.changeColor((Shape) shapeSelected, outlineColor, fillingColor);
    }
    
}

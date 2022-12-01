/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package seproject;

import javafx.geometry.Point2D;

/**
 *
 * @author giuseppefusco
 */
public class MoveCommand implements OperationCommand{
    
    private ShapeModel selectedShape;


    public MoveCommand(ShapeModel selectedShape) {
        this.selectedShape = selectedShape;
        
    }

    @Override
    public void execute() throws GenericDrawException {
        selectedShape.move();
    }
}

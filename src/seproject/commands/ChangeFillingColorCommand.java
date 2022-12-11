/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package seproject.commands;

import seproject.shapes.ShapeModel;
import javafx.scene.paint.Color;
import seproject.exceptions.GenericDrawException;

/**
 *
 * @author Group14
 */
public class ChangeFillingColorCommand implements OperationCommand{

    private ShapeModel shapeSelected;
    private Color fillingColor;
    private Color oldFillingColor;
    
    public ChangeFillingColorCommand(ShapeModel shapeSelected, Color fillingColor) {
        this.shapeSelected = shapeSelected;
        this.fillingColor = fillingColor;
        this.oldFillingColor = shapeSelected.getFillingColor();
    }
    
    @Override
    public void execute() throws GenericDrawException {
        shapeSelected.changeFillingColor(fillingColor);
    }

    @Override
    public void undo() {
        shapeSelected.changeFillingColor(oldFillingColor);
    }
    
}

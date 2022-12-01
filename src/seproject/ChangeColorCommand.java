
package seproject;

import javafx.scene.paint.Color;

/**
 *
 * @author Group14
 */
public class ChangeColorCommand implements OperationCommand {
    
    private ShapeModel shapeSelected;
    private Color outlineColor;
    private Color fillingColor;
    private Color oldOutlineColor;
    private Color oldFillingColor;
    
    public ChangeColorCommand(ShapeModel shapeSelected, Color outlineColor, Color fillingColor) {
        this.shapeSelected = shapeSelected;
        this.outlineColor = outlineColor;
        this.fillingColor = fillingColor;
        this.oldOutlineColor = shapeSelected.getOutlineColor();
        this.oldFillingColor = shapeSelected.getFillingColor();
    }
    
    @Override
    public void execute() throws GenericDrawException {
        shapeSelected.changeColor(outlineColor, fillingColor);
    }

    @Override
    public void undo() {
        shapeSelected.changeColor(oldOutlineColor, oldFillingColor);
    }
    
}

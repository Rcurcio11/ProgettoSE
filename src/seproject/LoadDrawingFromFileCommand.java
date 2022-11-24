/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package seproject;

import javafx.scene.layout.AnchorPane;

/**
 *
 * @author Rosario
 */
public class LoadDrawingFromFileCommand implements OperationCommand{
    
    private AnchorPane drawingArea;
    private String filePath;
    
    public LoadDrawingFromFileCommand(AnchorPane drawingArea,String filePath){
        this.drawingArea = drawingArea;
        this.filePath = filePath;
    }
    
    @Override
    public void execute() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}

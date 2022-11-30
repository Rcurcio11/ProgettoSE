/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */

import javafx.geometry.Point2D;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import seproject.ChangeShapeDimensionsCommand;
import seproject.OperationCommand;
import seproject.OperationExecutor;
import seproject.RectangleModel;
import seproject.ShapeModel;

/**
 *
 * @author Gruppo14
 */
public class ChangeDimensionsTest {
    
    private AnchorPane drawingArea;
    private ShapeModel shape;
    private OperationExecutor executor;
    
    public ChangeDimensionsTest() {
    }
    
    @Before
    public void setUp() {
        this.drawingArea = new AnchorPane();
        this.shape = new RectangleModel();
        shape.insert(drawingArea, new Point2D(50,50), new Point2D(100,100), Color.GREY, Color.GREY);
        executor = new OperationExecutor();
    }
    
    @After
    public void tearDown() {
    }
    
    @Test
    public void testChangeDimensions(){
        // startX,startY < endX,endY
        shape.changeDimensions(drawingArea, new Point2D(50,50), new Point2D(60,60));
        assertEquals(new Point2D(50,50),shape.getStartPoint());
        assertEquals(new Point2D(60,60),shape.getEndPoint());
        
        // endX,endY < startX,startY 
        shape.changeDimensions(drawingArea, new Point2D(50,50), new Point2D(25,20));
        assertEquals(new Point2D(25,20),shape.getStartPoint());
        assertEquals(new Point2D(50,50),shape.getEndPoint());
        
        // endX < startX
        // startY < endY
        shape.changeDimensions(drawingArea, new Point2D(50,20), new Point2D(25,30));
        assertEquals(new Point2D(25,20),shape.getStartPoint());
        assertEquals(new Point2D(50,30),shape.getEndPoint());
        
        // startX < endX
        // endY < startY
        shape.changeDimensions(drawingArea, new Point2D(20,30), new Point2D(25,20));
        assertEquals(new Point2D(20,20),shape.getStartPoint());
        assertEquals(new Point2D(25,30),shape.getEndPoint());
        
        
    }
    
    @Test 
    public void testChangeDimensionsCommand(){
        OperationCommand command = new ChangeShapeDimensionsCommand(drawingArea,new Point2D(12,43),new Point2D(60,70),shape);
        executor.execute(command);
        assertEquals(shape.getStartPoint(),new Point2D(12,43));
        assertEquals(shape.getEndPoint(),new Point2D(60,70));
    }
}

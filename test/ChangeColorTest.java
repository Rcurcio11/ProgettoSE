/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */

import javafx.geometry.Point2D;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import seproject.OperationExecutor;
import seproject.RectangleModel;
import seproject.ShapeModel;

/**
 *
 * @author vGroup14
 */
public class ChangeColorTest {
    
    private AnchorPane drawingArea;
    private ShapeModel shape;
    private OperationExecutor executor;
    
    public ChangeColorTest() {
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
    public void testChangeColor(){
        shape.changeColor(Color.ALICEBLUE, Color.GAINSBORO);
        assertEquals(Color.GAINSBORO, shape.getFillingColor());
        assertEquals(Color.ALICEBLUE, shape.getOutlineColor());
    }
}

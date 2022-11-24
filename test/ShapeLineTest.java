/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */

import static java.lang.Math.abs;
import java.util.Arrays;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;
import static org.hamcrest.CoreMatchers.instanceOf;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import seproject.ShapeLine;


/**
 *
 * @author Group14
 */
public class ShapeLineTest {
    
    private ShapeLine testShapeLine;
    
    public ShapeLineTest() {
        testShapeLine = new ShapeLine();
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void testInsert(){    
        AnchorPane drawingPane = new AnchorPane();
        double startX = Math.random()*663;
        double startY = Math.random()*479;
        double endX = Math.random()*663; 
        double endY = Math.random()*479;
        
        testShapeLine.insert(drawingPane, startX, startY, endX, endY);
        assertEquals(1,drawingPane.getChildren().size());
        assertEquals(javafx.scene.shape.Line.class,drawingPane.getChildren().get(0).getClass());
    }
    
    

}

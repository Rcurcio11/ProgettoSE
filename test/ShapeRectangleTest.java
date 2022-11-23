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
import seproject.RectangleModel;

/**
 *
 * @author uondi
 */
public class ShapeRectangleTest {
    private RectangleModel testShapeRectangle;
    
    public ShapeRectangleTest() {
        testShapeRectangle = new RectangleModel();
        
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
    public void testInsert() {
       AnchorPane testPane = new AnchorPane();     
       double startX = Math.random()*663;
       double startY = Math.random()*479;
       double endX = Math.random()*663; 
       double endY = Math.random()*479;
       
       testShapeRectangle.insert(testPane, startX, startY, endX, endY);
       
       assertEquals(1, testPane.getChildren().size());
       assertEquals(testPane.getChildren().get(0).getClass(), javafx.scene.shape.Rectangle.class);
    }   
}



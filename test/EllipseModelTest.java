/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */

import javafx.scene.layout.AnchorPane;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import seproject.EllipseModel;

/**
 *
 * @author giuseppefusco
 */
public class EllipseModelTest {
    private EllipseModel testShapeEllipse;
    
    public EllipseModelTest() {
        testShapeEllipse = new EllipseModel();
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
        AnchorPane drawingPane = new AnchorPane();
        double startX = Math.random()*663;
        double startY = Math.random()*479;
        double endX = Math.random()*663; 
        double endY = Math.random()*479;
        
        testShapeEllipse.insert(drawingPane, startX, startY, endX, endY);
        
        assertEquals(1,drawingPane.getChildren().size());
        assertEquals(javafx.scene.shape.Ellipse.class,drawingPane.getChildren().get(0).getClass());
    }
}


package seproject;

import java.util.ArrayList;
import java.util.List;
import javafx.geometry.Point2D;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;

/**
 *
 * @author Gruppo14
 */
public class ChangeDimensionsBehaviour {
    //private AnchorPane workingArea;
    private List<RectangleModel> cornerShapes;
    RectangleModel upperLeftVertex;
    RectangleModel lowerRightVertex;
    RectangleModel upperRightVertex;
    RectangleModel lowerLeftVertex;
    private double width;
    private double height;
    
    public ChangeDimensionsBehaviour(){
        //this.workingArea = workingArea;
        cornerShapes = new ArrayList<>();
        upperLeftVertex = null;
        upperRightVertex = null;
        lowerLeftVertex = null;
        lowerRightVertex = null;
        width = 10;
        height = 10;
    }
    
    public List<RectangleModel> insertVertex(AnchorPane workingArea,ShapeModel selectedShape){
        /*double rotationDegree = selectedShape.getRotationDegree();
        double deg = selectedShape.getRotationDegree();
        
        Rotate r = new Rotate();
        r.setPivotX(selectedShape.getCenterPoint().getX());
        r.setPivotY(selectedShape.getCenterPoint().getY());
        r.setAngle(deg);*/
        Point2D startPoint = selectedShape.getBounds().get(0);
        Point2D endPoint = selectedShape.getBounds().get(1);
        
        Point2D upperLeftCorner = startPoint; 
        Point2D lowerRightCorner = endPoint;
        Point2D upperRightCorner = new Point2D(lowerRightCorner.getX(),upperLeftCorner.getY());
        Point2D lowerLeftCorner = new Point2D(upperLeftCorner.getX(), lowerRightCorner.getY());
        
        ArrayList<Point2D> ulPoints = new ArrayList<>(); 
        ulPoints.add(0,new Point2D(upperLeftCorner.getX()-width/2,upperLeftCorner.getY()-width/2));
        ulPoints.add(1,new Point2D(upperLeftCorner.getX()+width/2,upperLeftCorner.getY()+width/2));
        
        
        
        ArrayList<Point2D> urPoints = new ArrayList<>();
        urPoints.add(0,new Point2D(upperRightCorner.getX()-width/2,upperRightCorner.getY()-width/2));
        urPoints.add(1, new Point2D(upperRightCorner.getX()+width/2,upperRightCorner.getY()+width/2));
        
        ArrayList<Point2D> llPoints = new ArrayList<>();
        llPoints.add(0, new Point2D(lowerLeftCorner.getX()-width/2,lowerLeftCorner.getY()-width/2));
        llPoints.add(1, new Point2D(lowerLeftCorner.getX()+width/2,lowerLeftCorner.getY()+width/2));
        
        ArrayList<Point2D> lrPoints = new ArrayList<>();
        lrPoints.add(0, new Point2D(lowerRightCorner.getX()-width/2,lowerRightCorner.getY()-width/2));
        lrPoints.add(1, new Point2D(lowerRightCorner.getX()+width/2,lowerRightCorner.getY()+width/2));
        
        
        cornerShapes = new ArrayList<>();
        upperLeftVertex = new RectangleModel();
        upperRightVertex = new RectangleModel();
        lowerLeftVertex = new RectangleModel();
        lowerRightVertex = new RectangleModel();
        
        upperLeftVertex.insert(workingArea,ulPoints , Color.BLACK, Color.WHITE);
        lowerRightVertex.insert(workingArea,lrPoints, Color.BLACK, Color.WHITE);
        //upperLeftVertex.getTransforms().add(r);
        //System.out.println(upperLeftVertex.getCenterPoint());
        //lowerRightVertex.getTransforms().add(r);
        if(!selectedShape.getClass().getSimpleName().equals(LineModel.class.getSimpleName())){
            lowerLeftVertex.insert(workingArea, llPoints, Color.BLACK, Color.WHITE);
            //lowerLeftVertex.getTransforms().add(r);
            upperRightVertex.insert(workingArea, urPoints, Color.BLACK, Color.WHITE);
            //upperRightVertex.getTransforms().add(r);
        }
        
        cornerShapes.add(upperLeftVertex);
        cornerShapes.add(upperRightVertex);
        cornerShapes.add(lowerLeftVertex);
        cornerShapes.add(lowerRightVertex);
        return cornerShapes;
    }
    
    public void removeVertex(AnchorPane workingArea){
        for(RectangleModel rm:cornerShapes)
            workingArea.getChildren().remove(rm);
        cornerShapes.clear();
        
    }
    
    public void moveVertex(AnchorPane workingArea,RectangleModel clickedVertex,Point2D draggingPoint){
        RectangleModel vertexX = null, vertexY = null;
        //for(RectangleModel rm:cornerShapes){
            //if(workingArea.getChildren().contains(clickedVertex)){
                clickedVertex.move(new Point2D(draggingPoint.getX() - clickedVertex.getBounds().get(0).getX() - width/2,draggingPoint.getY() - clickedVertex.getBounds().get(0).getY() - height/2));
                if(clickedVertex.equals(upperLeftVertex)){
                    vertexX = lowerLeftVertex;
                    vertexY = upperRightVertex;
                }
                else if(clickedVertex.equals(upperRightVertex)){
                    vertexX = lowerRightVertex;
                    vertexY = upperLeftVertex;
                }
                else if(clickedVertex.equals(lowerLeftVertex)){
                    vertexX = upperLeftVertex;
                    vertexY = lowerRightVertex;
                }
                else if(clickedVertex.equals(lowerRightVertex)){
                    vertexX = upperRightVertex;
                    vertexY = lowerLeftVertex;
                }
                vertexX.move(new Point2D(draggingPoint.getX() - vertexX.getBounds().get(0).getX() - width/2,0));
                vertexY.move(new Point2D(0,draggingPoint.getY() - vertexY.getBounds().get(0).getY() - height/2));
            //}
        //}
    }
    
    public ArrayList<Point2D> computePoints(RectangleModel clickedVertex,Point2D startPoint,Point2D endPoint, ShapeModel selectionRectangle){
        Point2D fixedPoint;
        ArrayList<Point2D> points = new ArrayList<>();
        if(clickedVertex.equals(upperLeftVertex)){
            //System.out.println("ulv");
            fixedPoint = selectionRectangle.getBounds().get(1); //endPoint of selectionRectangle
            points.add(0, endPoint);
            points.add(1,fixedPoint);
        }
        else if(clickedVertex.equals(upperRightVertex)){
            //System.out.println("urv");
            fixedPoint = new Point2D(selectionRectangle.getBounds().get(0).getX(),selectionRectangle.getBounds().get(1).getY());
            points.add(0, new Point2D(fixedPoint.getX(),endPoint.getY()));
            points.add(1, new Point2D(endPoint.getX(),fixedPoint.getY()));
        }
        else if(clickedVertex.equals(lowerLeftVertex)){
            //System.out.println("llv");
            fixedPoint = new Point2D(selectionRectangle.getBounds().get(1).getX(),selectionRectangle.getBounds().get(0).getY());
            points.add(0, new Point2D(endPoint.getX(),fixedPoint.getY()));
            points.add(1, new Point2D(fixedPoint.getX(),endPoint.getY()));
        }
        else if(clickedVertex.equals(lowerRightVertex)){
            //System.out.println("lrv");
            fixedPoint = selectionRectangle.getBounds().get(0);
            points.add(0, fixedPoint);
            points.add(1, endPoint);
        }
        return points;
    }
}

package hr.fer.zemris.graphics;

import hr.fer.zemris.renderer.Renderer;

import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * Created by filipgulan on 13/06/16.
 */
public class Triangle extends AbstractGraphicalObject {

   public Triangle() {
       super(new Point[] { new Point(0, 10), new Point(20, 10), new Point(10, 0)});
   }

    public Triangle(Point[] hotPoints) {
        super(hotPoints);
    }

    @Override
    public Rectangle getBoundingBox() {
        return null;
    }

    @Override
    public double selectionDistance(Point mousePoint) {
        return 0;
    }

    @Override
    public void render(Renderer r) {
        Point firstPoint = getHotPoint(0);
        Point seconndPoint = getHotPoint(1);
        Point thirdPoint = getHotPoint(2);

        r.drawLine(firstPoint, seconndPoint);
        r.drawLine(seconndPoint, thirdPoint);
        r.drawLine(thirdPoint, firstPoint);
    }

    @Override
    public String getShapeName() {
        return "Triangle";
    }

    @Override
    public GraphicalObject duplicate() {
        return new Triangle(new Point[] {new Point(getHotPoint(0)), new Point(getHotPoint(1)), new Point(getHotPoint(2))});
    }

    @Override
    public String getShapeID() {
        return null;
    }

    @Override
    public void load(Stack<GraphicalObject> stack, String data) {

    }

    @Override
    public void save(List<String> rows) {

    }
}

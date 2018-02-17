package hr.fer.zemris.graphics;

import hr.fer.zemris.geometry.GeometryUtil;
import hr.fer.zemris.renderer.Renderer;

import java.awt.*;
import java.util.*;

/**
 * @author Filip Gulan
 */
public class LineSegment extends AbstractGraphicalObject {

    public LineSegment() {
        super(new Point[]{ new Point(0, 0), new Point(10, 0) });
    }

    public LineSegment(Point startPoint, Point endPoint) {
        super(new Point[]{ startPoint, endPoint });
    }

    @Override
    public void render(Renderer r) {
        r.drawLine(getHotPoint(0), getHotPoint(1));
    }

    @Override
    public Rectangle getBoundingBox() {
        Point first = getHotPoint(0);
        Point second = getHotPoint(1);

        int x = Math.min(first.x, second.x);
        int y = Math.min(first.y, second.y);
        Point upperLeftPoint = new Point(x, y);
        int xDiff = Math.abs(first.x - second.x);
        int yDiff = Math.abs(first.y - second.y);
        Dimension dimension = new Dimension(xDiff, yDiff);

        return new Rectangle(upperLeftPoint, dimension);
    }

    @Override
    public double selectionDistance(Point mousePoint) {
        return GeometryUtil.distanceFromLineSegment(getHotPoint(0), getHotPoint(1), mousePoint);
    }

    @Override
    public String getShapeName() {
        return "Linija";
    }

    @Override
    public GraphicalObject duplicate() {
        return new LineSegment(new Point(getHotPoint(0)), new Point(getHotPoint(1)));
    }

    @Override
    public String getShapeID() {
        return "@LINE";
    }

    @Override
    public void save(java.util.List<String> rows) {
        Point start = getHotPoint(0);
        Point end = getHotPoint(1);
        rows.add(String.format("%s %d %d %d %d", getShapeID(), start.x, start.y, end.x, end.y));
    }

    @Override
    public void load(Stack<GraphicalObject> stack, String data) {
        String[] points = data.trim().split("\\s+");
        Point startPoint = new Point(Integer.parseInt(points[0]), Integer.parseInt(points[1]));
        Point endPoint = new Point(Integer.parseInt(points[2]), Integer.parseInt(points[3]));
        stack.push(new LineSegment(startPoint, endPoint));
    }
}

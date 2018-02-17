package hr.fer.zemris.graphics;

import hr.fer.zemris.geometry.GeometryUtil;
import hr.fer.zemris.renderer.Renderer;

import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * @author Filip Gulan
 */
public class Oval extends AbstractGraphicalObject {

    private final static int NUMBER_OF_POINTS = 100;

    public Oval() {
        super(new Point[]{new Point(0, 10), new Point(10, 0)});
    }

    public Oval(Point lowerHotPoint, Point rightHotPoint) {
        super(new Point[]{lowerHotPoint, rightHotPoint});
    }

    @Override
    public void render(Renderer r) {
        r.fillPolygon(getPoints(NUMBER_OF_POINTS));
    }

    @Override
    public Rectangle getBoundingBox() {
        Point lowerPoint = getHotPoint(0);
        Point rightPoint = getHotPoint(1);
        int x = lowerPoint.x - (rightPoint.x - lowerPoint.x);
        int y = rightPoint.y - (lowerPoint.y - rightPoint.y);

        int width = 2 * (rightPoint.x - lowerPoint.x);
        int height = 2 * (lowerPoint.y - rightPoint.y);
        return new Rectangle(new Point(x, y), new Dimension(width, height));
    }

    private double getEquation(int x, int y) {
        Point lowerPoint = getHotPoint(0);
        Point rightPoint = getHotPoint(1);
        int a = rightPoint.x - lowerPoint.x;
        int b = lowerPoint.y - rightPoint.y;
        int pointX = x - lowerPoint.x;
        int pointY = y - rightPoint.y;
        return (pointX * pointX) / (a * a) + (pointY * pointY) / (b * b);
    }

    @Override
    public double selectionDistance(Point mousePoint) {
        if (getEquation(mousePoint.x, mousePoint.y) <= 0) {
            return 0;
        }
        Point[] points = getPoints(NUMBER_OF_POINTS);
        return Arrays
                .stream(points)
                .mapToDouble((point) -> GeometryUtil.distanceFromPoint(point, mousePoint))
                .min()
                .getAsDouble();
    }

    @Override
    public String getShapeName() {
        return "Oval";
    }

    @Override
    public GraphicalObject duplicate() {
        return new Oval(new Point(getHotPoint(0)), new Point(getHotPoint(1)));
    }

    @Override
    public String getShapeID() {
        return "@OVAL";
    }


    @Override
    public void load(Stack<GraphicalObject> stack, String data) {
        String[] points = data.trim().split("\\s+");
        Point rightPoint = new Point(Integer.parseInt(points[0]), Integer.parseInt(points[1]));
        Point lowerPoint = new Point(Integer.parseInt(points[2]), Integer.parseInt(points[3]));
        stack.push(new Oval(lowerPoint, rightPoint));
    }

    @Override
    public void save(List<String> rows) {
        Point lowerPoint = getHotPoint(0);
        Point rightPoint = getHotPoint(1);
        rows.add(String.format("%s %d %d %d %d", getShapeID(),
                rightPoint.x, rightPoint.y,
                lowerPoint.x, lowerPoint.y));
    }

    private Point[] getPoints(int numOfPoints) {
        Point lowerPoint = getHotPoint(0);
        Point rightPoint = getHotPoint(1);
        Point center = new Point(lowerPoint.x, rightPoint.y);
        int a = rightPoint.x - lowerPoint.x;
        int b = lowerPoint.y - rightPoint.y;

        Point[] points = new Point[numOfPoints];
        for (int i = 0; i < numOfPoints; i++) {
            double t = (2 * Math.PI / numOfPoints) * i;
            int x = (int) (a * Math.cos(t)) + center.x;
            int y = (int) (b * Math.sin(t)) + center.y;
            points[i] = new Point(x, y);
        }
        return points;
    }
}

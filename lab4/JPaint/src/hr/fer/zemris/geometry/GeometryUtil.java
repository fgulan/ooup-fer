package hr.fer.zemris.geometry;

import java.awt.*;
import java.awt.geom.Line2D;

/**
 * @author Filip Gulan
 */
public class GeometryUtil {
    public static double distanceFromPoint(Point point1, Point point2) {
        return Math.hypot(Math.abs(point2.x - point1.x), Math.abs(point2.y - point1.y));
    }

    public static double distanceFromLineSegment(Point s, Point e, Point p) {
        return Line2D.ptSegDist(s.x, s.y, e.x, e.y, p.x, p.y);
    }
}

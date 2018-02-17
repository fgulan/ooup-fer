package hr.fer.zemris.renderer;

import java.awt.*;

public interface Renderer {
    void drawLine(Point s, Point e);
	void fillPolygon(Point[] points);
}
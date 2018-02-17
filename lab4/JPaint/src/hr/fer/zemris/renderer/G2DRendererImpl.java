package hr.fer.zemris.renderer;

import java.awt.*;

public class G2DRendererImpl implements Renderer {

	private Graphics2D g2d;
	
	public G2DRendererImpl(Graphics2D g2d) {
		this.g2d = g2d;
	}
	
	@Override
	public void drawLine(Point start, Point end) {
		g2d.setColor(Color.BLUE);
		g2d.drawLine(start.x, start.y, end.x, end.y);
	}

	@Override
	public void fillPolygon(Point[] points) {
        int[] xPoints = new int[points.length];
		int[] yPoints = new int[points.length];
		for (int i = 0; i < points.length; i++) {
            xPoints[i] = points[i].x;
            yPoints[i] = points[i].y;
		}

		g2d.setColor(Color.BLUE);
		g2d.fillPolygon(xPoints, yPoints, points.length);
		g2d.setColor(Color.RED);
		g2d.drawPolygon(xPoints, yPoints, points.length);
	}
}
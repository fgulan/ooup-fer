package hr.fer.zemris.state;

import hr.fer.zemris.graphics.GraphicalObject;
import hr.fer.zemris.model.DocumentModel;
import hr.fer.zemris.renderer.Renderer;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class EraserState extends IdleState {

	private DocumentModel model;
	private List<Point> points;
	
	public EraserState(DocumentModel model) {
		this.model = model;
		points = new ArrayList<>();
	}
	
	@Override
	public void mouseDragged(Point mousePoint) {
		points.add(mousePoint);
		model.notifyListeners();
	}
	
	@Override
	public void mouseUp(Point mousePoint, boolean shiftDown, boolean ctrlDown) {
		points.add(mousePoint);
		Set<GraphicalObject> removeObjects = new HashSet<>();
		points.forEach((point) -> {
			GraphicalObject object = model.findSelectedGraphicalObject(point);
			if (object != null) {
				removeObjects.add(object);
			}
		});
		removeObjects.forEach(object -> model.removeGraphicalObject(object));
		points.clear();
		model.notifyListeners();
	}
	
	@Override
	public void afterDraw(Renderer r) {
		int length = points.size();
		for (int i = 0; i < length - 1; i++) {
			r.drawLine(points.get(i), points.get(i + 1));
		}
	}
}
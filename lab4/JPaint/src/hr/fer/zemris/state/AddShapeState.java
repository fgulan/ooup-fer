package hr.fer.zemris.state;

import hr.fer.zemris.graphics.GraphicalObject;
import hr.fer.zemris.model.DocumentModel;

import java.awt.*;

public class AddShapeState extends IdleState {

	private GraphicalObject prototype;
	private DocumentModel model;

	public AddShapeState(DocumentModel model, GraphicalObject prototype) {
		this.prototype = prototype;
		this.model = model;
	}
	
	@Override
	public void mouseDown(Point mousePoint, boolean shiftDown, boolean ctrlDown) {
			GraphicalObject object = prototype.duplicate();
			object.translate(mousePoint);
			model.addGraphicalObject(object);
	}
}
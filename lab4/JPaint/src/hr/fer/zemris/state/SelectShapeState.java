package hr.fer.zemris.state;

import hr.fer.zemris.graphics.CompositeShape;
import hr.fer.zemris.graphics.GraphicalObject;
import hr.fer.zemris.model.DocumentModel;
import hr.fer.zemris.renderer.Renderer;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

public class SelectShapeState extends IdleState {

    private DocumentModel model;
    private int hotPointIndex;
    private final static int HB_SIZE = 3;

    public SelectShapeState(DocumentModel model) {
        this.model = model;
        this.hotPointIndex = -1;
    }

    @Override
    public void mouseDown(Point mousePoint, boolean shiftDown, boolean ctrlDown) {
        GraphicalObject object = model.findSelectedGraphicalObject(mousePoint);

        if (object == null) {
            model.deselectAll();
            return;
        }

        if (ctrlDown) {
            object.setSelected(!object.isSelected());
        } else {
            model.deselectAll();
            object.setSelected(true);
        }
    }

    @Override
    public void mouseDragged(Point mousePoint) {
        if (model.getSelectedObjects().size() != 1) return;
        GraphicalObject object = model.getSelectedObjects().get(0);

        if (hotPointIndex >= 0) {
            if (object.isHotPointSelected(hotPointIndex)) {
                object.setHotPoint(hotPointIndex, mousePoint);
            }
        }
        hotPointIndex = model.findSelectedHotPoint(object, mousePoint);
        if (hotPointIndex != -1) {
            object.setHotPointSelected(hotPointIndex, true);
            object.setHotPoint(hotPointIndex, mousePoint);
        }
    }

    @Override
    public void mouseUp(Point mousePoint, boolean shiftDown, boolean ctrlDown) {
        if (model.getSelectedObjects().size() != 1) return;
        GraphicalObject object = model.getSelectedObjects().get(0);
        if (hotPointIndex >= 0 && hotPointIndex < object.getNumberOfHotPoints()) {
            object.setHotPointSelected(hotPointIndex, false);
            hotPointIndex = -1;
        }
    }

    @Override
    public void keyPressed(int keyCode) {
        switch (keyCode) {
            case KeyEvent.VK_LEFT:
                model.getSelectedObjects().forEach(object -> object.translate(new Point(-1, 0)));
                break;
            case KeyEvent.VK_RIGHT:
                model.getSelectedObjects().forEach(object -> object.translate(new Point(1, 0)));
                break;
            case KeyEvent.VK_UP:
                model.getSelectedObjects().forEach(object -> object.translate(new Point(0, -1)));
                break;
            case KeyEvent.VK_DOWN:
                model.getSelectedObjects().forEach(object -> object.translate(new Point(0, 1)));
                break;
            case KeyEvent.VK_ADD:
            case KeyEvent.VK_PLUS:
            case KeyEvent.VK_EQUALS:
                if(model.getSelectedObjects().size() == 1) {
                    model.increaseZ(model.getSelectedObjects().get(0));
                }
                break;
            case KeyEvent.VK_SUBTRACT:
            case KeyEvent.VK_MINUS:
            case KeyEvent.VK_SLASH:
                if(model.getSelectedObjects().size() == 1) {
                    model.decreaseZ(model.getSelectedObjects().get(0));
                }
                break;
            case KeyEvent.VK_G:
                groupObjects();
                break;
            case KeyEvent.VK_U:
                unGroupObjects();
                break;
            default:
                break;
        }
    }

    private void groupObjects() {
        if (model.getSelectedObjects().size() <= 1) {
            return;
        }
        List<GraphicalObject> newObjects = new ArrayList<>(model.getSelectedObjects());
        newObjects.forEach(object -> model.removeGraphicalObject(object));
        model.deselectAll();
        CompositeShape shape = new CompositeShape(newObjects);
        model.addGraphicalObject(shape);
        shape.setSelected(true);
    }

    private void unGroupObjects() {
        if (model.getSelectedObjects().size() != 1 || !(model.getSelectedObjects().get(0) instanceof CompositeShape)) {
            return;
        }
        CompositeShape composite = (CompositeShape)model.getSelectedObjects().get(0);
        model.deselectAll();
        composite.getObjects().forEach((object) -> {
            object.setSelected(true);
            model.addGraphicalObject(object);
        });
        model.removeGraphicalObject(composite);
    }

    @Override
    public void afterDraw(Renderer renderer, GraphicalObject object) {
        if (!object.isSelected()) {
            return;
        }
        Rectangle box = object.getBoundingBox();

        Point opperLeftPoint = box.getLocation();
        Point upperRightPoint = new Point((int) box.getMaxX(), box.y);
        Point lowerLeftPoint = new Point(box.x, (int) box.getMaxY());
        Point lowerRightPoint = new Point((int) box.getMaxX(), (int) box.getMaxY());
        renderer.drawLine(opperLeftPoint, upperRightPoint);
        renderer.drawLine(opperLeftPoint, lowerLeftPoint);
        renderer.drawLine(lowerLeftPoint, lowerRightPoint);
        renderer.drawLine(upperRightPoint, lowerRightPoint);
    }

    @Override
    public void afterDraw(Renderer r) {
        if (model.getSelectedObjects().size() != 1) return;
        GraphicalObject object = model.getSelectedObjects().get(0);
        int numOfHotPoints = object.getNumberOfHotPoints();
        for (int i = 0; i < numOfHotPoints; i++) {
            Point point = object.getHotPoint(i);
            r.drawLine(new Point(point.x - HB_SIZE, point.y - HB_SIZE),
                    new Point(point.x + HB_SIZE, point.y - HB_SIZE));
            r.drawLine(new Point(point.x - HB_SIZE, point.y - HB_SIZE),
                    new Point(point.x - HB_SIZE, point.y + HB_SIZE));
            r.drawLine(new Point(point.x - HB_SIZE, point.y + HB_SIZE),
                    new Point(point.x + HB_SIZE, point.y + HB_SIZE));
            r.drawLine(new Point(point.x + HB_SIZE, point.y - HB_SIZE),
                    new Point(point.x + HB_SIZE, point.y + HB_SIZE));
        }

    }

    @Override
    public void onLeaving() {
        this.hotPointIndex = -1;
        model.deselectAll();
    }
}

package hr.fer.zemris;

import hr.fer.zemris.actions.LoadAction;
import hr.fer.zemris.actions.SVGExportAction;
import hr.fer.zemris.actions.SaveAction;
import hr.fer.zemris.graphics.*;
import hr.fer.zemris.gui.JCanvas;
import hr.fer.zemris.model.DocumentModel;
import hr.fer.zemris.state.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JPaint extends JFrame {

    private DocumentModel model;
    private JCanvas canvas;
    private IState currentState;
    private Map<String, GraphicalObject> prototypes;


    public JPaint(List<GraphicalObject> objects) {
        this.currentState = new IdleState();
        this.model = new DocumentModel();
        this.canvas = new JCanvas(this);
        this.prototypes = new HashMap<>();

        setSize(640, 480);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setupKeyListeners();
        model.addDocumentModelListener(canvas);

        objects.forEach(object -> prototypes.put(object.getShapeID(), object));
        CompositeShape compPrototype = new CompositeShape();
        prototypes.put(compPrototype.getShapeID(), compPrototype);
        initGUI(objects);
    }

    private void setupKeyListeners() {
        canvas.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int keyCode = e.getKeyCode();
                switch (keyCode) {
                    case KeyEvent.VK_ESCAPE:
                        currentState.onLeaving();
                        currentState = new IdleState();
                        break;
                    default:
                        currentState.keyPressed(keyCode);
                }
            }
        });

        canvas.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                currentState.mouseDown(e.getPoint(), e.isShiftDown(), e.isControlDown());
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                currentState.mouseUp(e.getPoint(), e.isShiftDown(), e.isControlDown());
            }
        });

        canvas.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                currentState.mouseDragged(e.getPoint());
            }
        });
    }

    private void initGUI(List<GraphicalObject> objects) {
        getContentPane().add(canvas, BorderLayout.CENTER);
        setupToolbar(objects);
    }

    private void setupToolbar(List<GraphicalObject> objects) {
        JToolBar toolbar = new JToolBar();
        objects.forEach((object) -> {
            Action action = new AbstractAction(object.getShapeName()) {
                @Override
                public void actionPerformed(ActionEvent e) {
                    currentState.onLeaving();
                    currentState = new AddShapeState(model, object);
                }
            };
            toolbar.add(action);
        });

        Action action = new AbstractAction("Selektiraj") {

            private static final long serialVersionUID = 1L;

            @Override
            public void actionPerformed(ActionEvent e) {
                currentState.onLeaving();
                currentState = new SelectShapeState(model);
            }
        };
        toolbar.add(action);

        action = new AbstractAction("Briši") {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentState.onLeaving();
                currentState = new EraserState(model);
            }
        };
        toolbar.add(action);

        toolbar.add(new SVGExportAction("SVG Export", model));
        toolbar.add(new SaveAction("Pohrani", model));
        toolbar.add(new LoadAction("Ucitaj", model, prototypes));

        toolbar.setFloatable(false);
        add(toolbar, BorderLayout.PAGE_START);
    }

    public IState getCurrentState() {
        return currentState;
    }

    public DocumentModel getModel() {
        return model;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            List<GraphicalObject> objects = new ArrayList<>();

            objects.add(new Triangle());
            objects.add(new LineSegment());
            objects.add(new Oval());

            JFrame frame = new JPaint(objects);
            frame.setVisible(true);
        });
    }
}

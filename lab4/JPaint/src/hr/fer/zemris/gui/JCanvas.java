package hr.fer.zemris.gui;

import hr.fer.zemris.JPaint;
import hr.fer.zemris.model.DocumentModelListener;
import hr.fer.zemris.renderer.G2DRendererImpl;
import hr.fer.zemris.state.IState;

import javax.swing.*;
import java.awt.*;

/**
 * @author Filip Gulan
 */
public class JCanvas extends JComponent implements DocumentModelListener {

    private JPaint paint;

    public JCanvas(JPaint paint) {
        this.paint = paint;
        setFocusable(true);
        setBackground(Color.WHITE);
        requestFocusInWindow();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_QUALITY);
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        g2.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING,
                RenderingHints.VALUE_COLOR_RENDER_QUALITY);

        g2.setColor(getBackground());
        g2.fillRect(0, 0, getWidth(), getHeight());
        g2.setColor(getForeground());
        IState currentState = paint.getCurrentState();
        G2DRendererImpl renderer = new G2DRendererImpl(g2);

        paint.getModel().list().forEach((object) -> {
            object.render(renderer);
            currentState.afterDraw(renderer, object);
        });
        currentState.afterDraw(renderer);
        requestFocusInWindow();
    }

    @Override
    public void documentChange() {
        repaint();
    }
}

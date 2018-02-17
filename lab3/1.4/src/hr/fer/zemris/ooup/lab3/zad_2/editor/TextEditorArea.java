package hr.fer.zemris.ooup.lab3.zad_2.editor;

import hr.fer.zemris.ooup.lab3.zad_2.position.Location;
import hr.fer.zemris.ooup.lab3.zad_2.position.LocationRange;

import javax.swing.*;
import java.awt.*;
import java.util.Iterator;

/**
 * @author Filip Gulan
 */
public class TextEditorArea extends JComponent {

    // Editor UI constants
    private static final int LEFT_MARGIN = 5;
    private static final int RIGHT_MARGIN = 5;
    private static final int TOP_MARGIN = 0;
    private static final int BOTTOM_MARGIN = 5;
    private static final int LINE_BOTTOM_MARGIN = 3;

    private TextEditorModel model;

    public TextEditorArea(TextEditorModel model) {
        this.model = model;
        setFocusTraversalKeysEnabled(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        requestFocusInWindow();

        Graphics2D g2d = (Graphics2D)g;
        drawLines(g2d);
        drawCursor(g2d);
        drawSelection(g2d);
    }

    private void drawCursor(Graphics2D g2d) {
        Location cursorLocation = model.getCursorLocation();
        String line = "";
        if(!model.getLines().isEmpty()) {
            line = model.getLine(cursorLocation.getLine()).substring(0, cursorLocation.getColumn());
        }

        FontMetrics fontMetrics = g2d.getFontMetrics();
        int lineHeight = fontMetrics.getHeight();

        int x1 = fontMetrics.stringWidth(line) + LEFT_MARGIN;
        int y1 = (cursorLocation.getLine() + 1) * lineHeight + TOP_MARGIN + LINE_BOTTOM_MARGIN;
        int y2 = y1 - lineHeight;

        g2d.drawLine(x1, y1, x1, y2);
    }

    private void drawSelection(Graphics2D g2d) {
        if (!model.hasSelection()) return;

        Color oldColor = g2d.getColor();
        Color fillColor = new Color(62/255f, 96/255f, 182/255f, 0.2f);
        g2d.setColor(fillColor);

        LocationRange range = model.getSelectionRange();
        FontMetrics fontMetrics = g2d.getFontMetrics();
        int lineHeight = fontMetrics.getHeight();
        if (range.numberOfLines() < 0) {
            range.swap();
        } else if (range.numberOfLines() == 0
                && range.getStart().getColumn() > range.getEnd().getColumn()) {
            range.swap();
        }

        Location start = range.getStart();
        Location end = range.getEnd();
        int linesCount = range.numberOfLines();

        String line = model.getLine(start.getLine());

        if (linesCount == 0) {
            int xStart = LEFT_MARGIN + fontMetrics.stringWidth(line.substring(0, start.getColumn()));
            int width = fontMetrics.stringWidth(line.substring(start.getColumn(), end.getColumn()));
            g2d.fillRect(xStart, start.getLine() * lineHeight + LINE_BOTTOM_MARGIN, width, lineHeight);
        } else {
            int width = fontMetrics.stringWidth(line.substring(start.getColumn()));
            int xStart = LEFT_MARGIN + fontMetrics.stringWidth(line.substring(0, start.getColumn()));
            g2d.fillRect(xStart, start.getLine() * lineHeight + LINE_BOTTOM_MARGIN, width, lineHeight);

            for (int i = start.getLine() + 1, last = end.getLine(); i < last; i++) {
                line = model.getLine(i);
                width = fontMetrics.stringWidth(line);
                g2d.fillRect(LEFT_MARGIN, i * lineHeight + LINE_BOTTOM_MARGIN, width, lineHeight);
            }

            line = model.getLine(end.getLine());
            if (start.getColumn() < line.length()) {
                // TODO : Cursor location
                width = fontMetrics.stringWidth(line.substring(0, end.getColumn()));
            } else {
                width = fontMetrics.stringWidth(line.substring(0, end.getColumn()));
            }
            g2d.fillRect(LEFT_MARGIN, end.getLine() * lineHeight + LINE_BOTTOM_MARGIN, width, lineHeight);
        }

        // Restore old color
        g2d.setColor(oldColor);
    }

    private void drawLines(Graphics2D g2d) {
        FontMetrics fontMetrics = g2d.getFontMetrics();

        int lineCounter = 1, height, width = 0;
        int lineHeight = fontMetrics.getHeight();
        Iterator<String> iterator = model.allLines();
        while (iterator.hasNext()) {
            String line = iterator.next();

            int lineWidth = (int)fontMetrics.getStringBounds(line, g2d).getWidth();
            if (lineWidth > width) {
                width = lineWidth;
            }
            g2d.drawString(line, LEFT_MARGIN, lineCounter*lineHeight + TOP_MARGIN);
            lineCounter++;
        }

        // Sets preferred component size (it can be nested inside JScrollPane)
        height = (lineCounter - 1) * lineHeight + TOP_MARGIN + BOTTOM_MARGIN;
        width += LEFT_MARGIN + RIGHT_MARGIN;
        setPreferredSize(new Dimension(width, height));

        if (!model.shouldFolowCaret) {
            revalidate();
            return;
        }
        model.shouldFolowCaret = false;

        int areaWidth = (int) getVisibleRect().getWidth();
        int areaHeight = (int) getVisibleRect().getHeight();
        int areaX = (int) getVisibleRect().getX();
        int areaY = (int) getVisibleRect().getY();
        Location cursor = model.getCursorLocation();

        int cursorY = (cursor.getLine() + 1) * lineHeight + LINE_BOTTOM_MARGIN + TOP_MARGIN;
        int cursorX = fontMetrics.stringWidth(model.getLine(cursor.getLine()).substring(0, cursor.getColumn()));
        int xOffset = areaX;
        int yOffset = areaY;

        if (cursorX - areaX > areaWidth) {
            xOffset = cursorX - areaWidth + LEFT_MARGIN + RIGHT_MARGIN;
        } else if (cursorX < areaX) {
            xOffset = cursorX;
        }

        if (cursorY - areaY > areaHeight)  {
            yOffset = cursorY - areaHeight;
        } else if (cursorY - lineHeight - (LINE_BOTTOM_MARGIN + TOP_MARGIN) < areaY) {
            yOffset = cursorY - lineHeight - (LINE_BOTTOM_MARGIN + TOP_MARGIN);
        }

        scrollRectToVisible(new Rectangle(xOffset, yOffset, areaWidth, areaHeight));
        revalidate();
    }
}

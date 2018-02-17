package hr.fer.zemris.ooup.lab3.zad_2.editor;

import hr.fer.zemris.ooup.lab3.zad_2.observers.cursor.ICursorObserver;
import hr.fer.zemris.ooup.lab3.zad_2.observers.text.ITextObserver;
import hr.fer.zemris.ooup.lab3.zad_2.position.Location;

import javax.swing.*;
import javax.swing.border.EtchedBorder;

/**
 * @author Filip Gulan
 */
public class JStatusBar extends JPanel implements ITextObserver, ICursorObserver {

    private JLabel cursorLocationLabel;
    private JLabel linesCounterLabel;
    private TextEditorModel model;

    public JStatusBar(TextEditorModel model) {
        this.model = model;
        initGUI();
    }

    private void initGUI() {
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

        Location cursorLocation = model.getCursorLocation();
        cursorLocationLabel = new JLabel("Ln: " + (cursorLocation.getLine() + 1) +
                " Col: " + cursorLocation.getColumn());
        linesCounterLabel = new JLabel("Lines: " + model.getLines().size());

        add(linesCounterLabel);
        add(Box.createHorizontalGlue());
        add(cursorLocationLabel);
        add(Box.createHorizontalGlue());
        //setBackground(Color.DARK_GRAY);
        setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
    }

    @Override
    public void updateCursorLocation(Location location) {
        cursorLocationLabel.setText("Ln: " + (location.getLine() + 1) +
                " Col: " + location.getColumn());
    }

    @Override
    public void updateText() {
        linesCounterLabel.setText("Lines: " + model.getLines().size());
    }
}

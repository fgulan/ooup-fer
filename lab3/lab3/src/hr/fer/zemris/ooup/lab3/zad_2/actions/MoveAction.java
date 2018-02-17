package hr.fer.zemris.ooup.lab3.zad_2.actions;

import hr.fer.zemris.ooup.lab3.zad_2.editor.TextEditorModel;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * @author Filip Gulan
 */
public class MoveAction extends AbstractAction {

    private TextEditorModel model;
    private boolean toEnd;

    public MoveAction(String name, Icon icon, TextEditorModel model, boolean toEnd) {
        super(name, icon);
        this.model = model;
        this.toEnd = toEnd;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (toEnd) {
            model.moveCursorToEnd();
        } else {
            model.moveCursorToStart();
        }
    }
}

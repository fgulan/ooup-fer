package hr.fer.zemris.ooup.lab3.zad_2.actions;

import hr.fer.zemris.ooup.lab3.zad_2.editor.TextEditorModel;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * @author Filip Gulan
 */
public class ClearAction extends AbstractAction {

    private TextEditorModel model;

    public ClearAction(String name, Icon icon, TextEditorModel model) {
        super(name, icon);
        this.model = model;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        model.clear();
    }
}

package hr.fer.zemris.ooup.lab3.zad_2.actions;

import hr.fer.zemris.ooup.lab3.zad_2.editor.TextEditor;
import hr.fer.zemris.ooup.lab3.zad_2.editor.TextEditorModel;
import hr.fer.zemris.ooup.lab3.zad_2.observers.selection.ISelectionObserver;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * @author Filip Gulan
 */
public class DeleteSelectionAction extends AbstractAction implements ISelectionObserver {

    private TextEditor editor;

    public DeleteSelectionAction(String name, Icon icon, TextEditor editor) {
        super(name, icon);
        this.editor = editor;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        TextEditorModel model = editor.getModel();
        model.deleteRange(model.getSelectionRange());
    }

    @Override
    public void selectionUpdate(boolean hasSelection) {
        setEnabled(hasSelection);
    }
}

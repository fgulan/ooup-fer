package hr.fer.zemris.ooup.lab3.zad_2.actions;

import hr.fer.zemris.ooup.lab3.zad_2.editor.TextEditor;
import hr.fer.zemris.ooup.lab3.zad_2.editor.TextEditorModel;
import hr.fer.zemris.ooup.lab3.zad_2.observers.selection.ISelectionObserver;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * @author Filip Gulan
 */
public class CopyAction extends AbstractAction implements ISelectionObserver {

    private TextEditor editor;

    public CopyAction(String name, Icon icon, TextEditor editor) {
        super(name, icon);
        this.editor = editor;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        TextEditorModel model = editor.getModel();
        String text = model.getSelectionText();
        if (text != null) {
            editor.getClipboard().push(text);
        }
    }

    @Override
    public void selectionUpdate(boolean hasSelection) {
        setEnabled(hasSelection);
    }
}
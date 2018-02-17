package hr.fer.zemris.ooup.lab3.zad_2.actions;

import hr.fer.zemris.ooup.lab3.zad_2.editor.TextEditor;
import hr.fer.zemris.ooup.lab3.zad_2.observers.clipboard.ClipboardObserver;
import hr.fer.zemris.ooup.lab3.zad_2.observers.clipboard.ClipboardStack;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * @author Filip Gulan
 */
public class PasteAction extends AbstractAction implements ClipboardObserver {

    private TextEditor editor;

    public PasteAction(String name, Icon icon, TextEditor editor) {
        super(name, icon);
        this.editor = editor;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        ClipboardStack stack = editor.getClipboard();
        editor.getModel().insert(stack.peek());
    }

    @Override
    public void updateClipboard() {
        setEnabled(editor.getClipboard().hasText());
    }
}

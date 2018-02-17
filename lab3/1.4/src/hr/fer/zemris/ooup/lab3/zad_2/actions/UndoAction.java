package hr.fer.zemris.ooup.lab3.zad_2.actions;

import hr.fer.zemris.ooup.lab3.zad_2.editor.TextEditor;
import hr.fer.zemris.ooup.lab3.zad_2.observers.stack.StackObserver;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * @author Filip Gulan
 */
public class UndoAction extends AbstractAction implements StackObserver {

    private TextEditor editor;

    public UndoAction(String name, Icon icon, TextEditor editor) {
        super(name, icon);
        this.editor = editor;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        editor.getUndoManager().undo();
    }

    @Override
    public void updateStack(boolean isEmpty) {
        setEnabled(!isEmpty);
    }
}

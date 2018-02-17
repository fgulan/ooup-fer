package hr.fer.zemris.ooup.lab3.zad_2.plugins;

import hr.fer.zemris.ooup.lab3.zad_2.editor.TextEditorModel;
import hr.fer.zemris.ooup.lab3.zad_2.observers.clipboard.ClipboardStack;
import hr.fer.zemris.ooup.lab3.zad_2.observers.stack.UndoManager;

/**
 * @author Filip Gulan
 */
public interface IPlugin {
    public String getName();
    public String getDescription();
    public void execute(TextEditorModel model, UndoManager undoManager, ClipboardStack clipboardStack);
}

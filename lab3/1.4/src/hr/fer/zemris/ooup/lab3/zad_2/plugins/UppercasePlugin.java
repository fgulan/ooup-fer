package hr.fer.zemris.ooup.lab3.zad_2.plugins;

import hr.fer.zemris.ooup.lab3.zad_2.actions.edit.IEditAction;
import hr.fer.zemris.ooup.lab3.zad_2.editor.TextEditorModel;
import hr.fer.zemris.ooup.lab3.zad_2.observers.clipboard.ClipboardStack;
import hr.fer.zemris.ooup.lab3.zad_2.observers.stack.UndoManager;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Filip Gulan
 */
public class UppercasePlugin implements IPlugin, IEditAction {

    private TextEditorModel model;
    private List<String> undoLines;

    @Override
    public void executeDo() {
        List<String> lines = model.getLines();
        undoLines = new ArrayList<>(lines);

        List<String> newLines = new ArrayList<>();
        lines.forEach((line) -> {
            String uppercaseLine = "";
            if (line.length() > 0) {
                uppercaseLine = upperCaseAllFirst(line);
            }
            newLines.add(uppercaseLine);
        });
        model.setLines(newLines);
        model.notifyTextObservers();
    }

    private String upperCaseAllFirst(String value) {
        char[] array = value.toCharArray();
        array[0] = Character.toUpperCase(array[0]);

        for (int i = 1; i < array.length; i++) {
            if (Character.isWhitespace(array[i - 1])) {
                array[i] = Character.toUpperCase(array[i]);
            }
        }
        return new String(array);
    }

    @Override
    public void executeUndo() {
        model.setLines(undoLines);
        model.notifyTextObservers();
    }

    @Override
    public String getName() {
        return "Uppercase word";
    }

    @Override
    public String getDescription() {
        return "Makes every word first letter uppercase";
    }

    @Override
    public void execute(TextEditorModel model, UndoManager undoManager, ClipboardStack clipboardStack) {
        this.model = model;
        executeDo();
        undoManager.push(this);
    }
}

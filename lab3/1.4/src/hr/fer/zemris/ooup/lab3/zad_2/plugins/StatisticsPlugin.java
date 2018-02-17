package hr.fer.zemris.ooup.lab3.zad_2.plugins;

import hr.fer.zemris.ooup.lab3.zad_2.editor.TextEditorModel;
import hr.fer.zemris.ooup.lab3.zad_2.observers.clipboard.ClipboardStack;
import hr.fer.zemris.ooup.lab3.zad_2.observers.stack.UndoManager;

import javax.swing.*;
import java.util.Arrays;

/**
 * @author Filip Gulan
 */
public class StatisticsPlugin implements IPlugin {
    @Override
    public String getName() {
        return "Statistics";
    }

    @Override
    public String getDescription() {
        return "Current document number of words, rows and letters.";
    }

    @Override
    public void execute(TextEditorModel model, UndoManager undoManager, ClipboardStack clipboardStack) {
        int numberOfLines = model.getLines().size();

        int numberOfWords = model.getLines().stream().mapToInt((line) -> {
            return line.split("\\s+").length;
        }).sum();

        int numberOfLetters = model.getLines().stream().mapToInt((line) -> {
            String[] words = line.split("\\s+");
            return Arrays.stream(words).mapToInt(String::length).sum();
        }).sum();

        StringBuilder builder = new StringBuilder();
        builder.append("Words: " + numberOfWords).append(System.lineSeparator());
        builder.append("Lines: " + numberOfLines).append(System.lineSeparator());
        builder.append("Letters: " + numberOfLetters).append(System.lineSeparator());

        JOptionPane.showMessageDialog(null, builder.toString());

    }
}

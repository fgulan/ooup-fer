package hr.fer.zemris.ooup.lab3.zad_2.actions;

import hr.fer.zemris.ooup.lab3.zad_2.editor.TextEditor;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

/**
 * @author Filip Gulan
 */
public class OpenAction extends AbstractAction {

    private TextEditor editor;

    public OpenAction(String name, Icon icon, TextEditor editor) {
        super(name, icon);
        this.editor = editor;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JFileChooser fc = new JFileChooser();
        fc.setDialogTitle("Open file");
        if (fc.showOpenDialog(editor) != JFileChooser.APPROVE_OPTION) {
            editor.requestFocusInWindow();
            return;
        }
        Path file = fc.getSelectedFile().toPath();
        if (!Files.isReadable(file)) {
            JOptionPane.showMessageDialog(editor,
                    "Unable to open given file", "Error",
                    JOptionPane.ERROR_MESSAGE);
            editor.requestFocusInWindow();
            return;
        }
        editor.requestFocusInWindow();
        List<String> lines = null;
        try {
            lines = Files.readAllLines(file);
        } catch (IOException e1) {
        }
        editor.getModel().reset(lines);
        editor.setFilePath(file);
    }
}

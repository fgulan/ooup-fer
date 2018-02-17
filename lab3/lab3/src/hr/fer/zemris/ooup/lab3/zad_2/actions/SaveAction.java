package hr.fer.zemris.ooup.lab3.zad_2.actions;

import hr.fer.zemris.ooup.lab3.zad_2.editor.TextEditor;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * @author Filip Gulan
 */
public class SaveAction extends AbstractAction {

    private TextEditor editor;

    public SaveAction(String name, Icon icon, TextEditor editor) {
        super(name, icon);
        this.editor = editor;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Path filePath = editor.getFilePath();
        if (filePath == null) {
            JFileChooser fc = new JFileChooser();
            fc.setDialogTitle("Save");
            if (fc.showSaveDialog(editor) != JFileChooser.APPROVE_OPTION) {
                editor.requestFocusInWindow();
                return;
            }
            Path file = fc.getSelectedFile().toPath();

            if (Files.exists(file)) {
                int r = JOptionPane
                        .showConfirmDialog(editor,
                                "Are you sure thath you want to overwrite file.",
                                "Warning",
                                JOptionPane.YES_NO_OPTION,
                                JOptionPane.QUESTION_MESSAGE);
                if (r != JOptionPane.YES_OPTION) {
                    editor.requestFocusInWindow();
                    return;
                }
            }
            filePath = file;
        }
        editor.requestFocusInWindow();
        editor.setFilePath(filePath);
        byte[] data = editor.getModel().getDocument().getBytes(StandardCharsets.UTF_8);
        try {
            Files.write(filePath, data);
        } catch (Exception e1) {
        }
    }
}

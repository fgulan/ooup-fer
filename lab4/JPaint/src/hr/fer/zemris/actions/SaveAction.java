package hr.fer.zemris.actions;

import hr.fer.zemris.model.DocumentModel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by filipgulan on 12/06/16.
 */
public class SaveAction extends AbstractAction {

    private DocumentModel model;

    public SaveAction(String name, DocumentModel model) {
        super(name);
        this.model = model;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Save");
        if(fileChooser.showSaveDialog(null) != JFileChooser.APPROVE_OPTION) {
            return;
        }
        String path = fileChooser.getSelectedFile().getPath();
        if(!path.endsWith(".jpaint")) {
            path += ".jpaint";
        }
        List<String> rows = new ArrayList<>();
        model.list().forEach(object -> object.save(rows));

        try (BufferedWriter writer = new BufferedWriter(
                new FileWriter(path))) {
            for (String line : rows) {
                writer.write(line);
                writer.newLine();
            }
            writer.flush();
        } catch (IOException exception) {
            JOptionPane.showMessageDialog(null, "Unable to save file",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}

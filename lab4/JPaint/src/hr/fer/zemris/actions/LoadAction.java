package hr.fer.zemris.actions;

import com.sun.javaws.exceptions.InvalidArgumentException;
import hr.fer.zemris.graphics.GraphicalObject;
import hr.fer.zemris.model.DocumentModel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.List;
import java.util.Map;
import java.util.Stack;

/**
 * Created by filipgulan on 12/06/16.
 */
public class LoadAction extends AbstractAction {

    private DocumentModel model;
    private Map<String, GraphicalObject> prototypes;

    public LoadAction(String name, DocumentModel model, Map<String, GraphicalObject> prototypes) {
        super(name);
        this.model = model;
        this.prototypes = prototypes;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Load");
        if(fileChooser.showOpenDialog(null) != JFileChooser.APPROVE_OPTION) {
            return;
        }
        File file = fileChooser.getSelectedFile();
        try {
            parse(file);
        } catch (Exception e1) {
            JOptionPane.showMessageDialog(null, "Invalid input file",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void parse(File file) throws Exception {
        List<String> lines = Files.readAllLines(file.toPath(), StandardCharsets.UTF_8);
        Stack<GraphicalObject> objects = new Stack<>();

        int index;
        for (String line : lines) {
            line = line.trim();
            if (line.isEmpty()) continue;
            if (!line.startsWith("@")) {
                throw new RuntimeException("Invalid input file");
            }
            index = line.indexOf(' ');
            if (index == -1) {
                throw new RuntimeException("Invalid input file");
            }
            prototypes.get(line.substring(0, index))
                    .load(objects, line.substring(index + 1));
        }
        model.clear();
        objects.forEach(object -> model.addGraphicalObject(object));
    }
}

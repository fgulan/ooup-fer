package hr.fer.zemris.actions;

import hr.fer.zemris.model.DocumentModel;
import hr.fer.zemris.renderer.SVGRendererImpl;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.IOException;

/**
 * Created by filipgulan on 12/06/16.
 */
public class SVGExportAction extends AbstractAction {

    private DocumentModel model;

    public SVGExportAction(String name, DocumentModel model) {
        super(name);
        this.model = model;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("SVG export");
        if(fileChooser.showSaveDialog(null) != JFileChooser.APPROVE_OPTION) {
            return;
        }
        String path = fileChooser.getSelectedFile().getPath();
        if(!path.endsWith(".svg")) {
            path += ".svg";
        }
        SVGRendererImpl svgRenderer = new SVGRendererImpl(path);
        model.list().forEach(object -> object.render(svgRenderer));
        try {
            svgRenderer.close();
        } catch (IOException e1) {
            JOptionPane.showMessageDialog(null, "Unable to save file",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}

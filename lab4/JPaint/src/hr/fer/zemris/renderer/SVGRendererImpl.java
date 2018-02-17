package hr.fer.zemris.renderer;

import java.awt.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SVGRendererImpl implements Renderer {

    private List<String> lines;
    private String fileName;

    public SVGRendererImpl(String fileName) {
        this.fileName = fileName;
        this.lines = new ArrayList<>();
        lines.add("<svg xmlns=\"http://www.w3.org/2000/svg\" \n" +
                "\txmlns:xlink=\"http://www.w3.org/1999/xlink\">");

    }

    public void close() throws IOException {
        lines.add("</svg>");

        try (BufferedWriter writer = new BufferedWriter(
                new FileWriter(fileName))) {
            for (String line : lines) {
                writer.write(line);
                writer.newLine();
            }
            writer.flush();
            writer.close();
        }
    }

    @Override
    public void drawLine(Point s, Point e) {
        lines.add(String.format("<line x1=\"%d\" y1=\"%d\" " +
                "x2=\"%d\" y2=\"%d\" " +
                "style=\"stroke:#0000ff;\"/>", s.x, s.y, e.x, e.y));
    }

    @Override
    public void fillPolygon(Point[] points) {
        StringBuilder builder = new StringBuilder();
        builder.append("<polygon points=\"");
        for (Point point : points) {
            builder.append(point.x).append(',').append(point.y).append(' ');
        }
        builder.deleteCharAt(builder.length() - 1);
        builder.append("\" style=\"stroke:#ff0000; fill:#0000ff;\"/>");
        lines.add(builder.toString());
    }
}

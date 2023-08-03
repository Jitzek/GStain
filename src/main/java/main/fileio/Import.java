package main.fileio;

import javafx.scene.paint.Color;
import main.Canvas;
import main.canvasElements.CanvasElement;
import main.canvasElements.Compound;
import main.canvasElements.decorators.border.EllipseBorderDecorator;
import main.canvasElements.decorators.border.RectangleBorderDecorator;
import main.canvasElements.decorators.border.border.BorderStyle;
import main.canvasElements.shapes.Ellipse;
import main.canvasElements.shapes.Rectangle;
import main.models.Model;
import main.util.Pair;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class Import {
    private final Canvas canvas;
    private final File file;
    private final Model model;
    private List<Pair<Compound, Integer>> groups = new ArrayList<Pair<Compound, Integer>>();

    public Import(Canvas canvas, Model model, File file) {
        this.canvas = canvas;
        this.file = file;
        this.model = model;
    }

    public void importProject() {
        try {
            FileReader fileReader = new FileReader(this.file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line = null;

            while (((line = bufferedReader.readLine())) != null) {
                String[] currentInfo = getInfo(line);
                String currentType = currentInfo[0].toLowerCase().trim();

                switch (currentType) {
                    case "canvas":
                        handleCanvas(currentInfo);
                        break;
                    case "group":
                        handleGroup(currentInfo);
                        break;
                    case "rectangle":
                        handleRectangle(currentInfo);
                        break;
                    case "ellipse":
                        handleEllipse(currentInfo);
                        break;
                }
            }

            bufferedReader.close();
        } catch (Exception e) {
            e.getStackTrace();
            System.out.println("Failed to open project");
        }
    }

    private String[] getInfo(String line) {
        return line.split(" ", 0);
    }

    public File getFile() {
        return file;
    }

    private BorderStyle convertStringtoBorderStyle(String style) {
        switch (style) {
            case "SOLID":
                return BorderStyle.SOLID;
            case "DASH":
                return BorderStyle.DASH;
            case "DOT":
                return BorderStyle.DOT;
            default:
                return null;
        }
    }

    private void handleCanvas(String[] currentInfo) {
        double canvasWidth = Double.parseDouble(currentInfo[2]);
        double canvasHeigth = Double.parseDouble(currentInfo[3]);
        model.createCanvas(file.getPath(), canvasWidth, canvasHeigth);
    }

    private void handleGroup(String[] currentInfo) {
        Compound group = new Compound(canvas);

        // add group to parent group or canvas and remove parent from list if full
        if (this.groups.isEmpty()) {
            this.canvas.addCanvasElement(group);
        } else {
            Pair<Compound, Integer> parentGroupPair = this.groups.get(this.groups.size() - 1);
            parentGroupPair.getKey().addChild(group);
            parentGroupPair.setValue(parentGroupPair.getValue() - 1);
            if (parentGroupPair.getValue() == 0) {
                this.groups.remove(this.groups.size() - 1);
            }
        }
        // add group to list
        int totalElements = Integer.parseInt(currentInfo[1]);
        this.groups.add(new Pair<Compound, Integer>(group, totalElements));
    }

    private void handleRectangle(String[] currentInfo) {
        CanvasElement result;
        Double x = Double.parseDouble(currentInfo[1]);
        Double y = Double.parseDouble(currentInfo[2]);
        Color color = Color.web(currentInfo[3]);
        Double width = Double.parseDouble(currentInfo[4]);
        Double height = Double.parseDouble(currentInfo[5]);

        result = new Rectangle(this.canvas, x, y, color, width, height);
        if (currentInfo.length > 6) {
            BorderStyle style = convertStringtoBorderStyle(currentInfo[6]);
            Double thickness = Double.parseDouble(currentInfo[7]);
            Color borderColor = Color.web(currentInfo[8]);
            result = new RectangleBorderDecorator(result, style, thickness, borderColor);
        }
        if (this.groups.isEmpty()) {
            this.canvas.addCanvasElement(result);
        } else {
            this.groups.get(this.groups.size() - 1).getKey().addChild(result);
            this.groups.get(this.groups.size() - 1).setValue(this.groups.get(this.groups.size() - 1).getValue() - 1);

            if (this.groups.get(this.groups.size() - 1).getValue() == 0) {
                this.groups.remove(this.groups.size() - 1);
            }
        }
    }

    private void handleEllipse(String[] currentInfo) {
        CanvasElement result;
        Double x = Double.parseDouble(currentInfo[1]);
        Double y = Double.parseDouble(currentInfo[2]);
        Color color = Color.web(currentInfo[3]);
        Double width = Double.parseDouble(currentInfo[4]);
        Double height = Double.parseDouble(currentInfo[5]);

        result = new Ellipse(canvas, x, y, color, width, height);

        if (currentInfo.length > 6) {
            BorderStyle style = convertStringtoBorderStyle(currentInfo[6]);
            Double thickness = Double.parseDouble(currentInfo[7]);
            Color borderColor = Color.web(currentInfo[8]);
            result = new EllipseBorderDecorator(result, style, thickness, borderColor);
        }
        if (this.groups.isEmpty()) {
            this.canvas.addCanvasElement(result);
        } else {
            this.groups.get(this.groups.size() - 1).getKey().addChild(result);
            this.groups.get(this.groups.size() - 1).setValue(this.groups.get(this.groups.size() - 1).getValue() - 1);

            if (this.groups.get(this.groups.size() - 1).getValue() == 0) {
                this.groups.remove(this.groups.size() - 1);
            }
        }
    }

    public void drawAll() {
        for (CanvasElement canvasElement : canvas.getCanvasElements()) {
            canvasElement.draw();
        }
    }
}

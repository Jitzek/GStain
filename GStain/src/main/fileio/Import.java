package main.fileio;

import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import main.Canvas;
import main.canvasElements.CanvasElement;
import main.canvasElements.Compound;
import main.canvasElements.decorators.border.EllipseBorderDecorator;
import main.canvasElements.decorators.border.RectangleBorderDecorator;
import main.canvasElements.decorators.border.border.BorderStyle;
import main.canvasElements.shapes.Ellipse;
import main.canvasElements.shapes.Rectangle;
import main.models.Model;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Import {
    private List<String> indexer = new ArrayList<>();
    private Canvas canvas;
    private Stage stage;
    private String filename;
    private int currentline = 0;
    public Import(Canvas canvas, Stage stage, Model model){
        this.canvas = canvas;
        this.stage = stage;
        String c = fillIndexer();
        double cwidth = Double.parseDouble(getInfo(c)[2]);
        double cheigth = Double.parseDouble(getInfo(c)[3]);
        model.createCanvas(filename, cwidth, cheigth);
        model.createSelectionBox();
        traverseGroup(canvas.getCanvasElements());
        for(CanvasElement canvasElement : canvas.getCanvasElements()){
            canvasElement.draw();
        }
    }
    private void traverseGroup(ArrayList<CanvasElement> canvasElements){
        int total = Integer.parseInt(getInfo(indexer.get(currentline))[1]);
        for(int i = 0; i < total; i++){
            currentline++;
            switch (getType(indexer.get(currentline))[0].toLowerCase()){
                case "group":
                    Compound subgroup = new Compound(canvas);
                    traverseGroup(subgroup.getChildren());
                    canvasElements.add(subgroup);
                    break;
                case "rectangle":
                    Rectangle rectangle = new Rectangle(canvas, Double.parseDouble(getInfo(indexer.get(currentline))[1]), Double.parseDouble(getInfo(indexer.get(currentline))[2]), Color.web(getInfo(indexer.get(currentline))[3]), Double.parseDouble(getInfo(indexer.get(currentline))[4]), Double.parseDouble(getInfo(indexer.get(currentline))[5]));
                    if(getInfo(indexer.get(currentline)).length > 6){
                        RectangleBorderDecorator rectangleBorderDecorator = new RectangleBorderDecorator(rectangle, convertStringtoBorderStyle(getInfo(indexer.get(currentline))[6]), Double.parseDouble(getInfo(indexer.get(currentline))[7]), Color.web(getInfo(indexer.get(currentline))[8]));
                        canvasElements.add(rectangleBorderDecorator);
                    }else{
                        canvasElements.add(rectangle);
                    }
                    break;
                case "ellipse":
                    Ellipse ellipse = new Ellipse(canvas, Double.parseDouble(getInfo(indexer.get(currentline))[1]), Double.parseDouble(getInfo(indexer.get(currentline))[2]), Color.web(getInfo(indexer.get(currentline))[3]), Double.parseDouble(getInfo(indexer.get(currentline))[4]), Double.parseDouble(getInfo(indexer.get(currentline))[5]));
                    if(getInfo(indexer.get(currentline)).length > 6){
                        EllipseBorderDecorator ellipseBorderDecorator = new EllipseBorderDecorator(ellipse, convertStringtoBorderStyle(getInfo(indexer.get(currentline))[6]), Double.parseDouble(getInfo(indexer.get(currentline))[7]), Color.web(getInfo(indexer.get(currentline))[8]));
                        canvasElements.add(ellipseBorderDecorator);
                    }else{
                        canvasElements.add(ellipse);
                    }
                    break;
                case "triangle":
                    //Triangle triangle = new Triangle(Double.parseDouble(getInfo(indexer.get(currentline))[1]), Double.parseDouble(getInfo(indexer.get(currentline))[2]), Color.BLACK, Double.parseDouble(getInfo(indexer.get(currentline))[4]), Double.parseDouble(getInfo(indexer.get(currentline))[5]));
                    //canvasElements.add(triangle);
                    break;
            }
        }
    }
    private String[] getType(String line){
        String[] arr = line.split(" ", 0);
        String fixedType = arr[0].replace("\t", "");
        arr[0] = fixedType;
        return arr;
    }
    //FixMe
    //set dialog in model
    private String fillIndexer(){
        try{
            FileChooser fileChooser = new FileChooser();
            File file = fileChooser.showOpenDialog(stage);
            filename = file.getPath();
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()){
                String data = scanner.nextLine();
                if(!data.equals("")){
                    indexer.add(data);
                }
            }
            scanner.close();
            return indexer.get(0);
        } catch (FileNotFoundException e){
            System.out.println("Error occurred");
            e.printStackTrace();
            return null;
        }
    }
    private String[] getInfo(String line){
        return line.split(" ", 0);
    }

    public String getFilename() {
        return filename;
    }

    private BorderStyle convertStringtoBorderStyle(String style){
        switch (style){
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
}

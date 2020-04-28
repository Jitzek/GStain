package main.fileio;

import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import main.Canvas;
import main.canvasElements.CanvasElement;
import main.canvasElements.Compound;
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
    private List<String> indexer = new ArrayList<String>();
    private Canvas canvas;
    private Stage stage;
    private String filename;
    private Model model;
    private int currentline = 0;
    public Import(Canvas canvas, Stage stage, Model model){
        this.canvas = canvas;
        this.stage = stage;
        this.model = model;
        String c = fillIndexer();
        double cwidth = Double.parseDouble(getInfo(c)[2]);
        double cheigth = Double.parseDouble(getInfo(c)[3]);
        model.createCanvas(filename, cwidth, cheigth);
        traverseGroup(canvas.getCanvasElements());
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
                    Rectangle rectangle = new Rectangle(canvas, Double.parseDouble(getInfo(indexer.get(currentline))[1]), Double.parseDouble(getInfo(indexer.get(currentline))[2]), Color.BLACK, Double.parseDouble(getInfo(indexer.get(currentline))[4]), Double.parseDouble(getInfo(indexer.get(currentline))[5]));
                    canvasElements.add(rectangle);
                    break;
                case "circle":
                    Ellipse circle = new Ellipse(canvas, Double.parseDouble(getInfo(indexer.get(currentline))[1]), Double.parseDouble(getInfo(indexer.get(currentline))[2]), Color.BLACK, Double.parseDouble(getInfo(indexer.get(currentline))[4]), Double.parseDouble(getInfo(indexer.get(currentline))[5]));
                    canvasElements.add(circle);
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

    private String fillIndexer(){
        try{
            FileChooser fileChooser = new FileChooser();
            File file = fileChooser.showOpenDialog(stage);
            filename = file.getName();
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
}

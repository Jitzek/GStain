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
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * This class imports .gurbe files
 */
public class Import {
    private final Canvas canvas;
    private final File file;
    private final Model model;
    private final List<String> indexer = new ArrayList<>();
    private String filename;
    private int currentline = 0;

    public Import(Canvas canvas, Model model, File file){
        this.canvas = canvas;
        this.file = file;
        this.model = model;
    }

    /**
     * this method goes trough all lines of the file, converts the lines into groups and shapes and hold the hierarchy in place
     * @param canvasElements empty array that will be filled with imported canvaselements
     */
    public void traverseGroup(ArrayList<CanvasElement> canvasElements){
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
                    Rectangle rectangle = new Rectangle(
                            canvas,
                            Double.parseDouble(getInfo(indexer.get(currentline))[1]),
                            Double.parseDouble(getInfo(indexer.get(currentline))[2]),
                            Color.web(getInfo(indexer.get(currentline))[3]),
                            Double.parseDouble(getInfo(indexer.get(currentline))[4]),
                            Double.parseDouble(getInfo(indexer.get(currentline))[5]));
                    if(getInfo(indexer.get(currentline)).length > 6){
                        RectangleBorderDecorator rectangleBorderDecorator = new RectangleBorderDecorator(rectangle,
                                convertStringtoBorderStyle(getInfo(indexer.get(currentline))[6]),
                                Double.parseDouble(getInfo(indexer.get(currentline))[7]),
                                Color.web(getInfo(indexer.get(currentline))[8]));
                        canvasElements.add(rectangleBorderDecorator);
                    }else{
                        canvasElements.add(rectangle);
                    }
                    break;
                case "ellipse":
                    Ellipse ellipse = new Ellipse(
                            canvas,
                            Double.parseDouble(getInfo(indexer.get(currentline))[1]),
                            Double.parseDouble(getInfo(indexer.get(currentline))[2]),
                            Color.web(getInfo(indexer.get(currentline))[3]),
                            Double.parseDouble(getInfo(indexer.get(currentline))[4]),
                            Double.parseDouble(getInfo(indexer.get(currentline))[5]));
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

    /**
     * fills an array with all lines from a file, so the lines have an index and can be accessed easier
     * @return imported canvas info
     */
    private String fillIndexer(){
        try{
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

    public void createCanvas(){
        String c = fillIndexer();
        double cwidth = Double.parseDouble(getInfo(c)[2]);
        double cheigth = Double.parseDouble(getInfo(c)[3]);
        model.createCanvas(filename, cwidth, cheigth);
    }
    public void drawAll(){
        for(CanvasElement canvasElement : canvas.getCanvasElements()){
            canvasElement.draw();
        }
    }
}

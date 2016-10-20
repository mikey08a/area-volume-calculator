package areacalc;   

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import javafx.application.Application;
import javafx.beans.InvalidationListener;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import static javafx.collections.FXCollections.observableList;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class AreaCalc extends Application {

    private Stage stage;
    private VBox root;
    private StackPane resultBox;
    private String opType;
    private String shapeType;
    private static ObservableList<String> typeList,areaList,volumeList;
    Double lengthValue,widthValue,heightValue,baseValue,radiusValue;
    
    @Override
    public void start(Stage primaryStage) {
        stage = primaryStage;
        
        //Create layout
        root = new VBox();
        HBox first = new HBox();
        first.setSpacing(5);
        
        HBox second = new HBox();
        second.setSpacing(5);
        
        VBox third = new VBox();
        third.setSpacing(5);
        third.setAlignment(Pos.CENTER);
        
        resultBox = new StackPane();
        
        root.getChildren().addAll(first,second,third,resultBox);
        root.setPadding(new Insets(10,10,10,10));
        root.setSpacing(5);
        root.setAlignment(Pos.CENTER);
        
        //Create elements for first layout
        Label typeLabel = new Label("Type of Calculation");
        ComboBox typeSelector = new ComboBox();
        typeSelector.setItems(typeList);
        
        //Add nodes to layout
        first.getChildren().addAll(typeLabel,typeSelector);
        
        //Add change listener for comboBox
        typeSelector.valueProperty().addListener(new ChangeListener<String>(){
            @Override
            public void changed(ObservableValue<? extends String> observable, 
                    String oldValue, String newValue) {
                opType = newValue;
                secondStage(second,third, newValue);
            }
        });
        
        //Start Window
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    
    public static void main(String[] args) {
        //Create Shape arrayLists
        ArrayList area = new ArrayList();
        area.add("Circle");
        area.add("Rectangle");
        area.add("Triangle");
        
        ArrayList volume = new ArrayList();
        volume.add("Rectangular Prism");
        volume.add("Sphere");
        volume.add("Cylinder");
        volume.add("Pyramid");
        
        ArrayList type = new ArrayList();
        type.add("Area");
        type.add("Volume");
        
        //Create Observable lists for ComboBoxes
        areaList = FXCollections.observableList(area);
        volumeList = FXCollections.observableList(volume);
        typeList = FXCollections.observableList(type);
        
        //This line fixes Windows error with comboBoxes
        System.setProperty("glass.accessible.force", "false");
        
        
        launch(args);
    }
    
    private void secondStage(HBox second,VBox third,String value) {
        
        //Make sure duplicates aren't added
        second.getChildren().clear();
        
        //Create shape selection dialog
        Label shapeLabel = new Label("Shape of Object");
        ComboBox shapeSelector = new ComboBox();
        
        //Set list options based on the type of calculation selected
        if(value.equals("Area")){
            shapeSelector.setItems(areaList);
        }else{
            shapeSelector.setItems(volumeList);
        }
        
        //Add listener to show the correct fields for calculation based on selection
        shapeSelector.valueProperty().addListener(new ChangeListener<String>(){
            @Override
            public void changed(ObservableValue<? extends String> observable, 
                    String oldValue, String newValue) {
                ArrayList fields = new ArrayList();
                switch (newValue) {
                    case "Circle":
                        fields.add("radius");
                        break;
                    case "Rectangle":
                        fields.add("length");
                        fields.add("width");
                        break;
                    case "Triangle":
                        fields.add("base");
                        fields.add("height");
                        break;
                    case "Rectangular Prism":
                        fields.add("length");
                        fields.add("width");
                        fields.add("height");
                        break;
                    case "Sphere":
                        fields.add("radius");
                        break;
                    case "Cylinder":
                        fields.add("radius");
                        fields.add("height");
                        break;
                    case "Pyramid":
                        fields.add("length");
                        fields.add("width");
                        fields.add("height");
                        break;
                    default:
                        break;
                }
               shapeType = newValue;
               thirdStage(third,fields,newValue);
            }
            
        });
        
        //Add nodes and resize window
        second.getChildren().addAll(shapeLabel,shapeSelector);
        update();
    }
    
    private void thirdStage(VBox third, ArrayList fields,String shape){
        
        //Make sure doplicates aren't added
        third.getChildren().clear();
        
        //Create submit button
        Button submit = new Button("Submit");
        
        //Create dialog for length value
        HBox length = new HBox();
        length.setSpacing(5);
        Label lengthLabel = new Label("Length");
        TextField lengthField = new TextField("0");
            //Make enter launch submit()
        lengthField.setOnKeyPressed(e -> {
            if(e.getCode() == KeyCode.ENTER){
                submit();
            }
        });
            //validation
        lengthField.textProperty().addListener(new ChangeListener<String>(){
            @Override
            public void changed(ObservableValue<? extends String> observable, 
                    String oldValue, String newValue) {
                try{
                    double length = Double.parseDouble(newValue);
                    lengthValue = length;
                    submit.setDisable(false);
                }catch(Exception e){
                    submit.setDisable(true);
                }
            }
        });
        length.getChildren().addAll(lengthLabel,lengthField);
        
        //Create dialog for width value
        HBox width = new HBox();
        width.setSpacing(5);
        Label widthLabel = new Label("Width");
        TextField widthField = new TextField("0");
            //Make enter launch submit()
        widthField.setOnKeyPressed(e -> {
            if(e.getCode() == KeyCode.ENTER){
                submit();
            }
        });
            //Validation
        widthField.textProperty().addListener(new ChangeListener<String>(){
            @Override
            public void changed(ObservableValue<? extends String> observable, 
                    String oldValue, String newValue) {
                try{
                    double length = Double.parseDouble(newValue);
                    widthValue = length;
                    submit.setDisable(false);
                }catch(Exception e){
                    submit.setDisable(true);
                }
            }
        });
        width.getChildren().addAll(widthLabel,widthField);
        
        //Create dialog for height value
        HBox height = new HBox();
        height.setSpacing(5);
        Label heightLabel = new Label("Height");
        TextField heightField = new TextField("0");
            //Make enter launch submit()
        heightField.setOnKeyPressed(e -> {
            if(e.getCode() == KeyCode.ENTER){
                submit();
            }
        });
            //Validation
        heightField.textProperty().addListener(new ChangeListener<String>(){
            @Override
            public void changed(ObservableValue<? extends String> observable, 
                    String oldValue, String newValue) {
                try{
                    double length = Double.parseDouble(newValue);
                    heightValue = length;
                    submit.setDisable(false);
                }catch(Exception e){
                    submit.setDisable(true);
                }
            }
        });
        height.getChildren().addAll(heightLabel,heightField);
        
        //Create dialog for radius value
        HBox radius = new HBox();
        radius.setSpacing(5);
        Label radiusLabel = new Label("Radius");
        TextField radiusField = new TextField("0");
            //Make enter launch submit()
        radiusField.setOnKeyPressed(e -> {
            if(e.getCode() == KeyCode.ENTER){
                submit();
            }
        });
            //Validation
        radiusField.textProperty().addListener(new ChangeListener<String>(){
            @Override
            public void changed(ObservableValue<? extends String> observable, 
                    String oldValue, String newValue) {
                try{
                    double length = Double.parseDouble(newValue);
                    radiusValue = length;
                    submit.setDisable(false);
                }catch(Exception e){
                    submit.setDisable(true);
                }
            }
        });        
        radius.getChildren().addAll(radiusLabel,radiusField);
        
        //Create dialog for base value
        HBox base = new HBox();
        base.setSpacing(5);
        Label baseLabel = new Label("Base");
        TextField baseField = new TextField("0");
            //Make enter launch submit()
        baseField.setOnKeyPressed(e -> {
            if(e.getCode() == KeyCode.ENTER){
                submit();
            }
        });
            //Validation
        baseField.textProperty().addListener(new ChangeListener<String>(){
            @Override
            public void changed(ObservableValue<? extends String> observable, 
                    String oldValue, String newValue) {
                try{
                    double length = Double.parseDouble(newValue);
                    baseValue = length;
                    submit.setDisable(false);
                }catch(Exception e){
                    submit.setDisable(true);
                }
            }
        });
        base.getChildren().addAll(baseLabel,baseField);
        
        //Show Dialogs based on shape selected
        for(int i=0;i<fields.size();i++){
            if(fields.get(i).equals("length")){
                third.getChildren().add(length);
            }else if(fields.get(i).equals("width")){
                third.getChildren().add(width);
            }else if(fields.get(i).equals("height")){
                third.getChildren().add(height);
            }else if(fields.get(i).equals("radius")){
                third.getChildren().add(radius);
            }else if(fields.get(i).equals("base")){
                third.getChildren().add(base);
            }
        }
        
        //launch submit() and add dialogs to window
        submit.setOnMouseClicked(e -> submit());
        third.getChildren().add(submit);
        update();
    }
    
    private void submit(){
        
        double result;
        
        //Get area/volume value based on shape
        switch(shapeType){
            case "Circle":
                result = Calculation.getArea(shapeType,radiusValue);
                break;
            case "Rectangle":
                result = Calculation.getArea(shapeType,lengthValue,widthValue);
                break;
            case "Triangle":
                result = Calculation.getArea(shapeType,baseValue,heightValue);
                break;
            case "Rectangular Prism":
                result = Calculation.getVolume(shapeType,lengthValue,widthValue,
                        heightValue);
                break;
            case "Sphere":
                result = Calculation.getVolume(shapeType,radiusValue);
                break;
            case "Cylinder":
                result = Calculation.getVolume(shapeType,radiusValue,heightValue);
                break;
            default:
                result = Calculation.getVolume(shapeType,lengthValue,widthValue,
                        heightValue);
                break;
        }
        
        //clear pervious answers and display current answer
        resultBox.getChildren().clear();
        showResult(result);
    }
    
    private void update(){
        //resize window to show content
        stage.sizeToScene();
    }

    private void showResult(double result) {
        //format result to show answer to 2 decimal places
        String text = String.format("%.2f", result);
        
        //create label to show answer
        Label valueLabel = new Label(text);
        valueLabel.setFont(new Font(18));
        valueLabel.setPadding(new Insets(5));
        
        //add border to answer to make it easier to see
        valueLabel.setBorder(new Border(new BorderStroke(Color.GREY,
                BorderStrokeStyle.SOLID,new CornerRadii(10),null)));
        resultBox.getChildren().add(valueLabel);
        
        //resize window
        update();
    }
}

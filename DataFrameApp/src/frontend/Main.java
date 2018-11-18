package frontend;
import backend.*;
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.Axis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.beans.value.*;
import javafx.util.*;

import javafx.scene.control.cell.PropertyValueFactory;

import java.awt.*;
import javafx.scene.control.TextField;
import java.io.File;
import java.util.ArrayList;

public class Main extends Application {

    public static TableView<ArrayList<Value>> table = new TableView<>();
    public static File selectedFile = null;
    public static Stage stage;
    public static DataFrame df;
    public static ObservableList<ArrayList<Value>> data = null;
    public Scene scene;
    private Column column;
    private DataFrame.DFGroup dfGroup;

    final HBox hb = new HBox();

    @Override
    public void start(Stage primaryStage) throws Exception{

        scene = new Scene(new Group());
        //Text text = new Text();
        //text.setFont(new Font(45));
        //Parent root = FXMLLoader.load(getClass().getResource("frontend.fxml"));
        //StackPane root = new StackPane();


        primaryStage.setTitle("DF");

        Group root = new Group();

        Button fileButton = new Button("File...");
        fileButton.setOnAction((event) -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open Resource File");
            Main.selectedFile = fileChooser.showOpenDialog(Main.stage);
            ArrayList<Class<? extends Value>> typy2 = new ArrayList<>();
            typy2.add(new StringValue("s").getClass());
            typy2.add(new StringValue("1999-01-01").getClass());
            typy2.add(new FloatValue(0.1).getClass());
            typy2.add(new DoubleValue(1.0).getClass());
            DataFrame tmpDF = null;
            try {
                tmpDF = new DataFrame(Main.selectedFile.getAbsolutePath(), typy2, true);
            }
            catch (Exception e) {
            }
            df = tmpDF;
            table = tmpDF.makeTableView();
            System.out.println(table.getItems().get(0).get(0));
            String [] ss = new String[0];
            dfGroup = df.groupby(ss);
        });

        fileButton.setLayoutX(500);
        fileButton.setLayoutY(375);
        root.getChildren().add(fileButton);

        Button printTable = new Button("Print.");
        printTable.setOnAction((event) -> {
            System.out.println(table.getItems());
            System.out.println(table.getItems().get(0).get(0).getClass().toString());
            System.out.println(table.getItems().get(0).get(1).getClass().toString());
            System.out.println(table.getItems().get(0).get(2).getClass().toString());
        });

        printTable.setLayoutX(500);
        printTable.setLayoutY(350);
        root.getChildren().add(printTable);

        TextField min = new TextField();
        min.setLayoutX(400);
        min.setLayoutY(200);
        root.getChildren().add(min);
        Text minT = new Text();
        minT.setText("Minimum: ");
        minT.setX(335);
        minT.setY(215);
        root.getChildren().add(minT);

        TextField max = new TextField();
        max.setLayoutX(400);
        max.setLayoutY(225);
        root.getChildren().add(max);
        Text maxT = new Text();
        maxT.setText("Maksimum: ");
        maxT.setX(335);
        maxT.setY(240);
        root.getChildren().add(maxT);

        TextField sum = new TextField();
        sum.setLayoutX(400);
        sum.setLayoutY(250);
        root.getChildren().add(sum);
        Text sumT = new Text();
        sumT.setText("Suma: ");
        sumT.setX(335);
        sumT.setY(265);
        root.getChildren().add(sumT);

        TextField std = new TextField();
        std.setLayoutX(400);
        std.setLayoutY(275);
        root.getChildren().add(std);
        Text stdT = new Text();
        stdT.setText("Odchylenie: ");
        stdT.setX(335);
        stdT.setY(290);
        root.getChildren().add(stdT);

        TextField var = new TextField();
        var.setLayoutX(400);
        var.setLayoutY(300);
        root.getChildren().add(var);
        Text varT = new Text();
        varT.setText("Wariancja: ");
        varT.setX(335);
        varT.setY(315);
        root.getChildren().add(varT);

        TextField colNameField = new TextField();
        colNameField.setLayoutX(335);
        colNameField.setLayoutY(150);
        root.getChildren().add(colNameField);
        colNameField.setText("Column name");
        TextField colNameField2 = new TextField();
        colNameField2.setLayoutX(335);
        colNameField2.setLayoutY(175);
        root.getChildren().add(colNameField2);
        colNameField2.setText("Column name");

        NumberAxis xAxis = new NumberAxis();
        NumberAxis yAxis = new NumberAxis();

        LineChart<Number,Number> chartDF = new LineChart<Number,Number>(xAxis,yAxis);
        chartDF.setLayoutX(700);
        chartDF.setLayoutY(50);
        root.getChildren().add(chartDF);


        Button column1 = new Button("Calculate!");
        column1.setOnAction((event -> {
            try {
                column = df.get(colNameField.getText());
                Column columnX = df.get(colNameField2.getText());
                try {
                    min.setText(column.min().toString());
                    max.setText(column.max().toString());
                    sum.setText(column.sum().toString());
                }
                catch (ArithmeticException e){
                    min.setText("0");
                    max.setText("0");
                    sum.setText("0");
                    System.out.println("Wrong data type! (min, max, sum)");
                }
                try {
                    std.setText(column.std().toString());
                    var.setText(column.var().toString());

                }
                catch (ArithmeticException e){
                    std.setText("0");
                    var.setText("0");
                    System.out.println("Wrong data type! (std, var)");
                }
                try{
                    XYChart.Series series = new XYChart.Series();
                    for(int i = 0; i< column.obj.size(); i++){
                        series.getData().add(new XYChart.Data(Double.parseDouble(columnX.obj.get(i).toString()),Double.parseDouble(column.obj.get(i).toString() )));
                    }
                    chartDF.getData().add(series);
                }
                catch (ArithmeticException e){
                    System.out.println("Wrong data type! (chart)");
                }
            }
            catch (NullPointerException e) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Error");
                alert.setHeaderText("Column Error");
                alert.setContentText("Column named: " + colNameField.getText() + " does not exist!");

                alert.showAndWait();
                System.out.println("Wrong column name");
            }


        }));
        column1.setLayoutY(150);
        column1.setLayoutX(470);
        root.getChildren().add(column1);




        TableColumn col1= new TableColumn("id");
        col1.setCellValueFactory(c-> new SimpleStringProperty(new String("123")));

        TableColumn col2= new TableColumn("date");
        col2.setCellValueFactory(c-> new SimpleStringProperty(new String("123")));

        TableColumn col3= new TableColumn("total");
        col3.setCellValueFactory(c-> new SimpleStringProperty(new String("123")));

        TableColumn col4= new TableColumn("val");
        col4.setCellValueFactory(c-> new SimpleStringProperty(new String("123")));

        System.out.println(table.getItems());

        table.setItems(data);
        table.getColumns().addAll(col1,col2,col3,col4);

        VBox vBox = new VBox();
        vBox.setSpacing(10);
        vBox.getChildren().add(table);

        root.getChildren().add(vBox);

        primaryStage.setScene(new Scene(root, 1300, 500));
        primaryStage.show();




    }


    public static void main(String[] args) {
        launch(args);
    }
}

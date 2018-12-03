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
import java.sql.*;

import javafx.scene.control.cell.PropertyValueFactory;

import java.awt.*;
import javafx.scene.control.TextField;
import java.io.File;
import java.util.ArrayList;

public class Main extends Application {

    private Connection conn = null;
    public static TableView<ArrayList<Value>> table = new TableView<>();
    public static File selectedFile = null;
    public static Stage stage;
    public static DataFrame df;
    public static ObservableList<ArrayList<Value>> data = null;
    public Scene scene;
    private Column column;
    private DataFrame.DFGroup dfGroup;

    final HBox hb = new HBox();

    public void connect(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
            conn =
                    DriverManager.getConnection("jdbc:mysql://mysql.agh.edu.pl/wdolata",
                            "wdolata","JhDsC5RFxymJpLW6");

        } catch (SQLException ex) {
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }catch(Exception e){e.printStackTrace();}
    }

    @Override
    public void start(Stage primaryStage) throws Exception{

        scene = new Scene(new Group());
        //connect();
        DataFrameDB mojedb = new DataFrameDB("wdolata");
         df = mojedb.toDF("books");
        primaryStage.setTitle("DFApplication");

        Group root = new Group();

        Button fileButton = new Button("File...");
        fileButton.setOnAction((event) -> {

            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open Resource File");
            Main.selectedFile = fileChooser.showOpenDialog(Main.stage);
            DataFrame tmpDF = null;
            DataFrame tdf = new DataFrame();
            try {
                tmpDF = new DataFrame(Main.selectedFile.getAbsolutePath(), tdf.typeFromFile(Main.selectedFile.getAbsolutePath()), true);
            }
            catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Error");
                alert.setHeaderText("File Error");
                alert.setContentText("Error encountered while opening file.");

                alert.showAndWait();
            }
            df = tmpDF;
            for (Class<? extends Value> a : tdf.typeFromFile(Main.selectedFile.getAbsolutePath())){
                System.out.println(a.getName());
            }
            table = tmpDF.makeTableView();
            String [] ss = new String[0];
            dfGroup = df.groupby(ss);
        });

        fileButton.setLayoutX(500);
        fileButton.setLayoutY(420);
        fileButton.setPrefSize(50.0, 50.0);
        root.getChildren().add(fileButton);

        Button printTable = new Button("Print.");
        printTable.setOnAction((event) -> {
            System.out.println(table.getItems());
            System.out.println(table.getItems().get(0).get(0).getClass().toString());
            System.out.println(table.getItems().get(0).get(1).getClass().toString());
            System.out.println(table.getItems().get(0).get(2).getClass().toString());
        });

        printTable.setLayoutX(600);
        printTable.setLayoutY(470);
        root.getChildren().add(printTable);

        Text chartT = new Text();
        chartT.setText("Chart: ");
        chartT.setFont(new Font(20.0));
        chartT.setX(400);
        chartT.setY(30);
        root.getChildren().add(chartT);

        TextField colYNameField = new TextField();
        colYNameField.setLayoutX(400);
        colYNameField.setLayoutY(50);
        root.getChildren().add(colYNameField);
        colYNameField.setText("Column for Y-Axis");

        TextField colXNameField = new TextField();
        colXNameField.setLayoutX(400);
        colXNameField.setLayoutY(80);
        root.getChildren().add(colXNameField);
        colXNameField.setText("Column for X-Axis");

        NumberAxis xAxis = new NumberAxis();
        NumberAxis yAxis = new NumberAxis();

        LineChart<Number,Number> chartDF = new LineChart<Number,Number>(xAxis,yAxis);
        chartDF.setLayoutX(700);
        chartDF.setLayoutY(50);
        root.getChildren().add(chartDF);

        Button chartButton = new Button("Make chart!");
        chartButton.setLayoutX(470);
        chartButton.setLayoutY(110);
        chartButton.setOnAction((event -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText("Column Error");
            try{
                chartDF.getData().clear();
                Column columnY = df.get(colYNameField.getText());
                Column columnX = df.get(colXNameField.getText());
                xAxis.setLabel(columnX.name);
                yAxis.setLabel(columnY.name);
                XYChart.Series series = new XYChart.Series();
                for(int i = 0; i< columnY.obj.size(); i++){
                    series.getData().add(new XYChart.Data(Double.parseDouble(columnX.obj.get(i).toString()),Double.parseDouble(columnY.obj.get(i).toString() )));
                }
                chartDF.getData().add(series);
            }
            catch (NumberFormatException e){
                alert.setContentText("Wrong data type, could not draw chart!");
                alert.showAndWait();
                System.out.println("Wrong data type! (chart)");
            }
            catch (NullPointerException e){
                alert.setContentText("Wrong column(s)!");
                alert.showAndWait();
                System.out.println("Wrong column name!");
            }
        }));
        root.getChildren().add(chartButton);

        Text calcT = new Text();
        calcT.setText("Statistics for column: ");
        calcT.setFont(new Font(20.0));
        calcT.setX(370);
        calcT.setY(190);
        root.getChildren().add(calcT);

        TextField min = new TextField();
        min.setLayoutX(400);
        min.setLayoutY(250);
        root.getChildren().add(min);
        Text minT = new Text();
        minT.setText("Min: ");
        minT.setX(335);
        minT.setY(265);
        root.getChildren().add(minT);

        TextField max = new TextField();
        max.setLayoutX(400);
        max.setLayoutY(275);
        root.getChildren().add(max);
        Text maxT = new Text();
        maxT.setText("Max: ");
        maxT.setX(335);
        maxT.setY(290);
        root.getChildren().add(maxT);

        TextField sum = new TextField();
        sum.setLayoutX(400);
        sum.setLayoutY(300);
        root.getChildren().add(sum);
        Text sumT = new Text();
        sumT.setText("Sum: ");
        sumT.setX(335);
        sumT.setY(315);
        root.getChildren().add(sumT);

        TextField std = new TextField();
        std.setLayoutX(400);
        std.setLayoutY(325);
        root.getChildren().add(std);
        Text stdT = new Text();
        stdT.setText("Std: ");
        stdT.setX(335);
        stdT.setY(340);
        root.getChildren().add(stdT);

        TextField var = new TextField();
        var.setLayoutX(400);
        var.setLayoutY(350);
        root.getChildren().add(var);

        Text varT = new Text();
        varT.setText("Var: ");
        varT.setX(335);
        varT.setY(365);
        root.getChildren().add(varT);

        Text colToStatsT = new Text();
        colToStatsT.setFont(new Font(15.0));
        colToStatsT.setText("Column: ");
        colToStatsT.setX(335);
        colToStatsT.setY(210);
        root.getChildren().add(colToStatsT);

        TextField colNameField = new TextField();
        colNameField.setLayoutX(335);
        colNameField.setLayoutY(215);
        root.getChildren().add(colNameField);
        colNameField.setText("Column name");


        Button calcButton = new Button("Calculate!");
        calcButton.setOnAction((event -> {
            boolean alert2 = false;
            try {
                column = df.get(colNameField.getText());

                try {
                    min.setText(column.min().toString());
                    max.setText(column.max().toString());
                    sum.setText(column.sum().toString());
                }
                catch (ArithmeticException e){
                    alert2 = true;
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
                    alert2 = true;
                    std.setText("0");
                    var.setText("0");
                    System.out.println("Wrong data type! (std, var)");
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
            System.out.println(alert2);
            if(alert2){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Error");
                alert.setHeaderText("Column Error");
                alert.setContentText("Wrong data type, could not perform action!");

                alert.showAndWait();
            }

        }));
        calcButton.setLayoutY(215);
        calcButton.setLayoutX(480);
        root.getChildren().add(calcButton);




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

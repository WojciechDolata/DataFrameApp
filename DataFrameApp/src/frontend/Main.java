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
        fileButton.setLayoutY(300);
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

        TextField max = new TextField();
        max.setLayoutX(400);
        max.setLayoutY(225);
        root.getChildren().add(max);

        TextField std = new TextField();
        std.setLayoutX(400);
        std.setLayoutY(250);
        root.getChildren().add(std);

        TextField sum = new TextField();
        sum.setLayoutX(400);
        sum.setLayoutY(275);
        root.getChildren().add(sum);

        Button column1 = new Button("Id");
        column1.setOnAction((event -> {
            column = df.get("val");
            try {
                System.out.println(dfGroup.min().get(column.name).obj.get(0).toString());
                min.setText(column.min().toString());
                max.setText(column.max().toString());
                sum.setText(column.sum().toString());
                std.setText(column.std().toString());
            }
            catch (backend.DataFrameException e){
                System.out.println("Coś nie wyszło");
            }

        }));
        column1.setLayoutY(150);
        column1.setLayoutX(400);
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

        primaryStage.setScene(new Scene(root, 700, 400));
        primaryStage.show();




    }


    public static void main(String[] args) {
        launch(args);
    }
}

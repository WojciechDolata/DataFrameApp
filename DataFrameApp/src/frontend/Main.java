package frontend;
import backend.*;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import javafx.scene.control.ScrollBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Button;
import javafx.scene.control.cell.PropertyValueFactory;

import java.awt.*;
import java.io.File;
import java.util.ArrayList;

public class Main extends Application {

    public static TableView<ArrayList<Value>> table = new TableView<>();
    public static File selectedFile = null;
    public static Stage stage;
    public static ScrollPane pane;
    public static DataFrame df;
    public static ObservableList<ArrayList<Value>> data = null;
    public Scene scene;

    final HBox hb = new HBox();

    @Override
    public void start(Stage primaryStage) throws Exception{

        scene = new Scene(new Group());
        //Text text = new Text();
        //text.setFont(new Font(45));
        //Parent root = FXMLLoader.load(getClass().getResource("frontend.fxml"));
        StackPane root = new StackPane();
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
        });

        final Label label = new Label("DF");

        table.setEditable(true);
        TableColumn idCol = new TableColumn("Id");
        idCol.setMinWidth(100);
        idCol.setCellValueFactory(
                new PropertyValueFactory<ArrayList<Value>, String>("id"));

        TableColumn dateCol = new TableColumn("dateCol Name");
        dateCol.setMinWidth(100);
        dateCol.setCellValueFactory(
                new PropertyValueFactory<ArrayList<Value>, String>("datecol"));

        TableColumn totalCol = new TableColumn("totalCol Name");
        totalCol.setMinWidth(100);
        totalCol.setCellValueFactory(
                new PropertyValueFactory<ArrayList<Value>, String>("totalcol"));

        TableColumn valCol = new TableColumn("valCol Name");
        valCol.setMinWidth(100);
        valCol.setCellValueFactory(
                new PropertyValueFactory<ArrayList<Value>, String>("valcol"));

        table.setItems(data);
        table.getColumns().addAll(idCol,dateCol,totalCol,valCol);

        final VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 0, 0, 10));
        vbox.getChildren().addAll(table);

        ((Group) scene.getRoot().getChildren().addAll(vbox));

        primaryStage.setTitle("Hello World");
        primaryStage.setScene(scene);
        primaryStage.show();
        stage = primaryStage;

    }


    public static void main(String[] args) {
        launch(args);
    }
}

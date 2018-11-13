package frontend;

import backend.*;
import javafx.fxml.FXML;
import javafx.stage.FileChooser;
import javafx.stage.Window;

import java.util.ArrayList;
import java.util.Observable;

public class Controller {


    @FXML
    public void chooseFile() throws Exception{
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        Main.selectedFile = fileChooser.showOpenDialog(Main.stage);
        ArrayList<Class<? extends Value>> typy2 = new ArrayList<>();
        typy2.add(new StringValue("s").getClass());
        typy2.add(new StringValue("1999-01-01").getClass());
        typy2.add(new FloatValue(0.1).getClass());
        typy2.add(new DoubleValue(1.0).getClass());
        DataFrame tmpDF = new DataFrame(Main.selectedFile.getAbsolutePath(),typy2,true);
        Main.df = tmpDF;
        Main.table = tmpDF.makeTableView();
    }
}

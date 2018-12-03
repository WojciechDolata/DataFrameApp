package backend;
import java.sql.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.io.*;
import java.util.*;

public class DataFrameDB {
    private String name;
    private Connection conn = null;
    private Statement stmt = null;
    private ResultSet rs = null;

    public void connect(String name) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
            conn =
                    DriverManager.getConnection("jdbc:mysql://mysql.agh.edu.pl/" + name,
                            "wdolata", "JhDsC5RFxymJpLW6");

        } catch (SQLException ex) {
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    DataFrameDB(String name){
        this.name = name;
        connect(name);
    }

    public DataFrame toDF(String tableName){
        DataFrame returnable;
        try{
            stmt = conn.createStatement();
            ResultSet tmpRS = stmt.executeQuery("SELECT * FROM " + name + ".INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME = N'" + tableName + "'");
            //for (String str : tmpRS.)
            rs = stmt.executeQuery("SELECT * FROM " + tableName);
            while(rs.next()){

            }


        } catch (SQLException ex){

        }


        return returnable;
    }
}

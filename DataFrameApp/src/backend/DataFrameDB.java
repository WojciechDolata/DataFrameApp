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
    private ResultSetMetaData rsmd = null;

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



    public DataFrameDB(String name){
        this.name = name;
        connect(name);
    }

    public DataFrame toDF(String tableName){
        DataFrame returnable = new DataFrame();
        try{

            stmt = conn.createStatement();
            //ResultSet tmpRS = stmt.executeQuery("SELECT * FROM " + name + ".INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME = N'" + tableName + "'");
            //for (String str : tmpRS.)
            rs = stmt.executeQuery("SELECT * FROM " + tableName);
            rsmd = rs.getMetaData();
            String colNames[] = new String[rsmd.getColumnCount()];
            for(int i=1; i<= rsmd.getColumnCount(); i++){
                colNames[i-1] = rsmd.getColumnName(i);
            }

            ArrayList<Class<? extends Value>> types = new ArrayList<>();

            rs.next();
                for(int j = 1; j<=rsmd.getColumnCount(); j++){
                    types.add(returnable.typeFromString(rs.getString(j)));
                }


            returnable = new DataFrame(colNames, types);
            int i=0;

            rs = stmt.executeQuery("SELECT * FROM " + tableName);
            while(rs.next()){
                String[] currentItem = new String[rsmd.getColumnCount()+1];
                ArrayList<Value> tmpItem = new ArrayList<>();
                for(int j = 1; j<=rsmd.getColumnCount(); j++){
                    currentItem[j] = rs.getString(j);
                    tmpItem.add(Value.getInstance(returnable.types.get(j-1)));
                    tmpItem.get(j-1).set(currentItem[j]);
                }
                returnable.add(tmpItem);
            }
            returnable.print();


        } catch (SQLException ex){
            System.out.println("Problem przy wykonwyaniu polecenia");
        }


        return returnable;
    }
}

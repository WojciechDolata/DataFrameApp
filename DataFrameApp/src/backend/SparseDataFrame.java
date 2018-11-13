package backend;

import java.util.ArrayList;

public class SparseDataFrame extends DataFrame{
    public boolean isNumeric(String str)
    {
        try
        {
            double d = Double.parseDouble(str);
        }
        catch(NumberFormatException nfe)
        {
            return false;
        }
        return true;
    }
    private int objectIterator = 0;
    public ArrayList<SparseColumn> table = new ArrayList<>();
    private Value elementToHide;

    public SparseDataFrame(String[] columns, ArrayList<Class<? extends Value>> dataTypes, Value hide) {
        elementToHide = hide;
//        if(hide.contains(".")) {
//            elementToHide = Double.parseDouble(hide);
//        }
//        else if(isNumeric(hide)){
//            elementToHide = Integer.parseInt(hide);
//        }
//        else{
//            elementToHide = hide;
//        }
        for(int i=0; i<columns.length; i++) {
            table.add(new SparseColumn(columns[i], dataTypes.get(i), elementToHide));
        }

    }
    public void add(ArrayList<Value> item){
        int k=0;
        for(Value currentObj : item) {
            System.out.println(currentObj + " " +elementToHide);
            if(currentObj!=elementToHide) {
                table.get(k).obj.add(new CooValue(objectIterator, currentObj));
            }
            k++;
        }
        objectIterator++;
    }

    public void print(){
        for(SparseColumn currentCol : table) {
            System.out.println(currentCol.name + " " + currentCol.type);
            for(CooValue currentVal : currentCol.obj){
                System.out.println(currentVal.id + " " + currentVal.val);
            }
        }
    }

    public SparseDataFrame(DataFrame unconvertedFrame) {
        int numCol=0;
        for(Column currentCol : unconvertedFrame.table){
            numCol++;
        }
        String[] columns = new String[numCol];
        ArrayList<Class<? extends Value>> dataTypes = new ArrayList<Class<? extends Value>>(numCol);
        int k=0;
        for(Column currentCol : unconvertedFrame.table){
            columns[k] = currentCol.name;
            dataTypes.add(currentCol.type);
            k++;
        }
        k=0;
        //backend.Value hide = "0"; // to mozna brac z mostCommon z dataFrame
       // elementToHide = hide;
//        if(hide.contains(".")) {
//            elementToHide = Double.parseDouble(hide);
//        }
//        else if(isNumeric(hide)){
//            elementToHide = Integer.parseInt(hide);
//        }
//        else{
//            elementToHide = hide;
//        }
        for(int i=0; i<columns.length; i++) {
            table.add(new SparseColumn(columns[i], dataTypes.get(i), elementToHide));
        }

        for(int i=0; i<unconvertedFrame.size(); i++){
            this.add(unconvertedFrame.getItem(i));
        }
    }

    public ArrayList<Value> getItem(int id) {
        ArrayList<Value> returnable = new ArrayList<>();
        for(SparseColumn currentCol : this.table){
            returnable.add(currentCol.copyItemId(id));
        }

        return returnable;
    }

    public DataFrame toDense(){
        int i=0;
        for(SparseColumn currentCol : this.table)
            i++;
        String[] columns = new String[i];
        ArrayList<Class<? extends Value>> dataTypes = new  ArrayList<Class<? extends Value>>(i);
        int k=0;
        for(SparseColumn currentCol : this.table) {
            columns[k] = currentCol.name;
            dataTypes.add(currentCol.type);
            k++;
        }
        DataFrame returnable = new DataFrame(columns,dataTypes);
        for(i = 0; i<objectIterator; i++) {
            returnable.add(this.getItem(i));
        }

        return returnable;
    }
}
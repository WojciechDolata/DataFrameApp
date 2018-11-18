package backend;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.io.*;
import java.util.*;

public class DataFrame{

    public Value createValObj(String s){
        Value returnable;
        if(s.equals("backend.IntegerValue")){
            returnable = new IntegerValue();
        }
        else if(s.equals("backend.FloatValue")){
            returnable = new FloatValue("1.0");
        }
        else if(s.equals("backend.DoubleValue")){
            returnable = new DoubleValue("1.0");
        }
        else if(s.equals("backend.DateTimeValue")){
            returnable = new DateTimeValue();
        }
        else if(s.equals("backend.StringValue")){
            returnable = new StringValue();
        }
        else {
            returnable = new IntegerValue();
        }

        return returnable;
    }

    public ArrayList<Class<? extends Value>> typeFromFile(String path){
        ArrayList<Class<? extends Value>> returnable = new ArrayList<>();
        try {
            FileInputStream fstream = new FileInputStream(path);
            BufferedReader br = new BufferedReader(new InputStreamReader(fstream));

            String strLine;
            strLine = br.readLine();
            strLine = br.readLine();
            String[] currentItem = strLine.split("[,]");
            for(String element: currentItem){
                if (element.contains(".")){
                    returnable.add(createValObj("backend.DoubleValue").getClass());
                }
                else if (element.contains("-")){
                    returnable.add(createValObj("backend.DateTimeValue").getClass());
                }
                else if (element.matches("[0-9]+")){
                    returnable.add(createValObj("backend.IntegerValue").getClass());
                }
                else{
                    returnable.add(createValObj("backend.StringValue").getClass());
                }
            }

            br.close();
        }
        catch (Exception e){
            System.out.println("Wrong file path!");
        }


        return returnable;
    }

    public class DFGroup implements Groupby{
        public DataFrame frame;
        String [] colToSort;
        LinkedList<DataFrame> separatedDFs;
        DFGroup(String[] kolumny, ArrayList<Class<? extends Value>> typy, String[] colnames){
            colToSort = colnames;
            frame = new DataFrame(kolumny, typy);
        }



        public Value createValObj(String s){
            Value returnable;
            if(s.equals("backend.IntegerValue")){
                returnable = new IntegerValue();
            }
            else if(s.equals("backend.FloatValue")){
                returnable = new FloatValue("1.0");
            }
            else if(s.equals("backend.DoubleValue")){
                returnable = new DoubleValue("1.0");
            }
            else {
                returnable = new IntegerValue();
            }

            return returnable;
        }

        @Override
        public DataFrame max() throws DataFrameException{
            ArrayList<Integer> colIds = new ArrayList<>();
            for( int i = 0; i< colToSort.length; i++){
                colIds.add(getColNumberInRow(colToSort[i]));
            }

            DataFrame returnable = new DataFrame(frame.getColNames(),frame.getTypes());

            for(DataFrame df : separatedDFs){
                ArrayList<Value> itemToAdd = new ArrayList<>();
                for (int i=0; i<frame.getColNum(); i++){
                    if(!colIds.contains(i)){
                        Value tmpMax = df.getItem(0).get(i);
                        //System.out.println();
                        for(int j = 1; j<df.size(); j++){
                            if(!df.getItem(j).get(i).getClass().toString().equals(tmpMax.getClass().toString())){
                                throw new DataFrameException(df.table.get(i).name,j);
                            }
                            if(df.getItem(j).get(i).gte(tmpMax)){
                                tmpMax = df.getItem(j).get(i);
                            }
                        }

                        itemToAdd.add(tmpMax);
                    }
                    else {
                        itemToAdd.add(df.getItem(0).get(i));
                    }
                }
                returnable.add(itemToAdd);
            }
            return returnable;
        }

        @Override
        public DataFrame min() throws DataFrameException {
            ArrayList<Integer> colIds = new ArrayList<>();
            for( int i = 0; i< colToSort.length; i++){
                colIds.add(getColNumberInRow(colToSort[i]));
            }

            DataFrame returnable = new DataFrame(frame.getColNames(),frame.getTypes());

            for(DataFrame df : separatedDFs){
                ArrayList<Value> itemToAdd = new ArrayList<>();
                for (int i=0; i<frame.getColNum(); i++){
                    if(!colIds.contains(i)){
                        Value tmpMin = df.getItem(0).get(i);
                        //System.out.println();
                        for(int j = 1; j<df.size(); j++){
                            if(!df.getItem(j).get(i).getClass().toString().equals(tmpMin.getClass().toString())){
                                throw new DataFrameException(df.table.get(i).name,j);
                            }
                            if(df.getItem(j).get(i).lte(tmpMin)){
                                tmpMin = df.getItem(j).get(i);
                            }
                        }

                        itemToAdd.add(tmpMin);
                    }
                    else {
                        itemToAdd.add(df.getItem(0).get(i));
                    }
                }
                returnable.add(itemToAdd);
            }
            return returnable;
        }

        private LinkedList<DataFrame> removingSpareCols(){
            LinkedList<DataFrame> tmpSeparatedDFs = new LinkedList<>();
            for(DataFrame df : separatedDFs) tmpSeparatedDFs.add(df.iloc(0,df.size()-1));
            DataFrame tmpFrame = frame.iloc(0,size()-1);
            ArrayList<Integer> colIds = new ArrayList<>();
            for( int j = 0; j< colToSort.length; j++){
                colIds.add(getColNumberInRow(colToSort[j]));
            }
            int i=0;
            for(Column curCol : tmpFrame.table){
                if(!(colIds.contains(tmpFrame.getColNumberInRow(curCol.name)) || curCol.type.getName().equals("backend.IntegerValue") || curCol.type.getName().equals("backend.FloatValue") || curCol.type.getName().equals("backend.DoubleValue"))){
                    int k=0;
                    for(k=0;k<tmpSeparatedDFs.size(); k++){
                        tmpSeparatedDFs.get(k).table.remove(i);
                    }
                }
                else i++;
            }
            return tmpSeparatedDFs;
        }

        @Override
        public DataFrame mean() throws DataFrameException {
            LinkedList<DataFrame> tmpSeparatedDFs = removingSpareCols();
            DataFrame tmpFrame = frame.iloc(0,size()-1);
            ArrayList<Integer> colIds = new ArrayList<>();
            for( int j = 0; j< colToSort.length; j++){
                colIds.add(getColNumberInRow(colToSort[j]));
            }
            DataFrame returnable = new DataFrame(tmpSeparatedDFs.get(0).getColNames(),tmpSeparatedDFs.get(0).getTypes());

            for(DataFrame df : tmpSeparatedDFs){
                ArrayList<Value> itemToAdd = new ArrayList<>();
                for(Column curCol : df.table){
                    if(colIds.contains(tmpFrame.getColNumberInRow(curCol.name))){
                        itemToAdd.add(curCol.obj.get(0));
                    }
                    else {
                        Value tmpVal = curCol.obj.get(0);
                        for (int j = 1; j < curCol.obj.size(); j++) {
                            if(tmpVal.getClass().toString().equals(curCol.obj.get(j).getClass().toString())){
                                throw new DataFrameException(curCol.name, j);
                            }
                            tmpVal = tmpVal.add(curCol.obj.get(j));
                        }
                        Value devisor = createValObj(curCol.type.getName());
                        devisor.set(String.valueOf(curCol.obj.size()));
                        tmpVal = tmpVal.div(devisor);
                        itemToAdd.add(tmpVal);
                    }
                }
                returnable.add(itemToAdd);
            }
            return returnable;
        }

        @Override
        public DataFrame std() throws DataFrameException{
            DataFrame meanDF = this.mean();
            LinkedList<DataFrame> tmpSeparatedDFs = removingSpareCols();
            DataFrame tmpFrame = frame.iloc(0,size()-1);
            ArrayList<Integer> colIds = new ArrayList<>();
            for( int j = 0; j< colToSort.length; j++){
                colIds.add(getColNumberInRow(colToSort[j]));
            }
            int i;

            DataFrame returnable = new DataFrame(tmpSeparatedDFs.get(0).getColNames(),tmpSeparatedDFs.get(0).getTypes());
            int k=0;
            for(DataFrame df : tmpSeparatedDFs){
                ArrayList<Value> itemToAdd = new ArrayList<>();
                i=0;
                for(Column curCol : df.table){
                    if(colIds.contains(tmpFrame.getColNumberInRow(curCol.name))){
                        itemToAdd.add(curCol.obj.get(0));
                    }
                    else {
                        Value tmpVal = curCol.obj.get(0);
                        Value powerer = createValObj(curCol.type.getName()), multiplier = createValObj(curCol.type.getName());

                        powerer.set("2");
                        multiplier.set(String.valueOf(df.size()));
                        for (int j = 1; j < curCol.obj.size(); j++) {
                            if(tmpVal.getClass().toString().equals(curCol.obj.get(j).getClass().toString())){
                                throw new DataFrameException(curCol.name, j);
                            }
                            tmpVal = tmpVal.add(curCol.obj.get(j).sub(meanDF.getItem(k).get(i)).pow(powerer));
                        }
                        tmpVal = tmpVal.div(multiplier);
                        powerer.set("0.5");
                        tmpVal = tmpVal.pow(powerer);
                        itemToAdd.add(tmpVal);
                    }
                    i++;
                }
                k++;
                returnable.add(itemToAdd);
            }


            return returnable;
        }

        @Override
        public DataFrame sum() throws DataFrameException{
            LinkedList<DataFrame> tmpSeparatedDFs = new LinkedList<>();
            for(DataFrame df : separatedDFs) tmpSeparatedDFs.add(df.iloc(0,df.size()-1));
            DataFrame tmpFrame = frame.iloc(0,size()-1);
            ArrayList<Integer> colIds = new ArrayList<>();
            for( int j = 0; j< colToSort.length; j++){
                colIds.add(getColNumberInRow(colToSort[j]));
            }
            int i=0;
            for(Column curCol : tmpFrame.table){
                if(!(colIds.contains(tmpFrame.getColNumberInRow(curCol.name)) || curCol.type.getName().equals("backend.IntegerValue") || curCol.type.getName().equals("backend.FloatValue") || curCol.type.getName().equals("backend.DoubleValue"))){
                    int k=0;
                    for(k=0;k<tmpSeparatedDFs.size(); k++){
                        tmpSeparatedDFs.get(k).table.remove(i);
                    }
                }
                else i++;
            }

            DataFrame returnable = new DataFrame(tmpSeparatedDFs.get(0).getColNames(),tmpSeparatedDFs.get(0).getTypes());
            for(DataFrame df : tmpSeparatedDFs){
                ArrayList<Value> itemToAdd = new ArrayList<>();
                i=0;
                for(Column curCol : df.table){
                    if(colIds.contains(tmpFrame.getColNumberInRow(curCol.name))){
                        itemToAdd.add(curCol.obj.get(0));
                    }
                    else {
                        Value tmpVal = curCol.obj.get(0);
                        for (int j = 1; j < curCol.obj.size(); j++) {
                            if(tmpVal.getClass().toString().equals(curCol.obj.get(j).getClass().toString())){
                                throw new DataFrameException(curCol.name, j);
                            }
                            tmpVal = tmpVal.add(curCol.obj.get(j));
                        }

                        itemToAdd.add(tmpVal);
                    }
                    i++;
                }
                returnable.add(itemToAdd);
            }


            return returnable;

        }

        @Override
        public DataFrame var() throws DataFrameException {
            DataFrame meanDF = this.mean();
            LinkedList<DataFrame> tmpSeparatedDFs = removingSpareCols();
            DataFrame tmpFrame = frame.iloc(0,size()-1);
            ArrayList<Integer> colIds = new ArrayList<>();
            for( int j = 0; j< colToSort.length; j++){
                colIds.add(getColNumberInRow(colToSort[j]));
            }
            int i;

            DataFrame returnable = new DataFrame(tmpSeparatedDFs.get(0).getColNames(),tmpSeparatedDFs.get(0).getTypes());
            int k=0;
            for(DataFrame df : tmpSeparatedDFs){
                ArrayList<Value> itemToAdd = new ArrayList<>();
                i=0;
                for(Column curCol : df.table){
                    if(colIds.contains(tmpFrame.getColNumberInRow(curCol.name))){
                        itemToAdd.add(curCol.obj.get(0));
                    }
                    else {
                        Value tmpVal = curCol.obj.get(0);
                        Value powerer = createValObj(curCol.type.getName()), multiplier = createValObj(curCol.type.getName());

                        powerer.set("2");
                        multiplier.set(String.valueOf(df.size()));
                        for (int j = 1; j < curCol.obj.size(); j++) {
                            if(tmpVal.getClass().toString().equals(curCol.obj.get(j).getClass().toString())){
                                throw new DataFrameException(curCol.name, j);
                            }
                            tmpVal = tmpVal.add(curCol.obj.get(j).sub(meanDF.getItem(k).get(i)).pow(powerer));
                        }
                        tmpVal = tmpVal.div(multiplier);
                        itemToAdd.add(tmpVal);
                    }
                    i++;
                }
                k++;
                returnable.add(itemToAdd);
            }


            return returnable;
        }

        @Override// nie ma
        public DataFrame apply(Applyable a) {
            for(DataFrame df : separatedDFs){
                a.apply(df);
            }
            return null;
        }
    }

    public ArrayList<Column> table = new ArrayList<>(0);
    public ArrayList<Class<? extends Value>> types = new ArrayList<>();

    public DataFrame(){

    }

    public DataFrame(String[] kolumny, ArrayList<Class<? extends Value>> typy){
        types=typy;
        int ite=0;
        for(String columnaName : kolumny) {
            table.add(new Column(columnaName,typy.get(ite)));
            ite++;
        }
    
    
    }

    public DataFrame(String fileName, ArrayList<Class<? extends Value>> typy, boolean header) throws IOException {
        types=typy;
        FileInputStream fstream = new FileInputStream(fileName);
        BufferedReader br = new BufferedReader(new InputStreamReader(fstream));

        String strLine;
        String[] names;
        if(header) {
            strLine = br.readLine();
            names = strLine.split("[,]");
        }
        else {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Prosze mi podac te nazwy (po przecinku)");
            strLine = scanner.nextLine();
            names = strLine.split("[,]");
        }

        int noColumns=0;
        for(String columnaName : names) {
            table.add(new Column(columnaName,typy.get(noColumns)));
            noColumns++;
        }

        int i = 0;

        strLine = br.readLine();
        while ((strLine) != null)   {
            String[] currentItem = strLine.split("[,]");
            for(int j = 0; j<currentItem.length; j++) {

                table.get(j).obj.add(Value.getInstance(table.get(j).type));
                table.get(j).obj.get(i).set(currentItem[j]);
            }
            i++;

            strLine = br.readLine();
        }

        br.close();
    }

    public TableView<ArrayList<Value>> makeTableView(){
        TableView<ArrayList<Value>> returnable = new TableView<>();
        ArrayList<TableColumn<Value, String>> tableCols = new ArrayList<>();
        for( int i =0; i<getColNum(); i++){
            tableCols.add(new TableColumn<>());
        }
        ObservableList<ArrayList<Value>> ourData = FXCollections.observableArrayList(getItems());
        returnable.setItems(ourData);
        return returnable;
    }

    public ArrayList<ArrayList<Value>> getItems(){
        ArrayList<ArrayList<Value>> returnable = new ArrayList<>();
        for(int i =0; i<size(); i++){
            returnable.add(getItem(i));
        }
        return returnable;
    }

    public int size(){
        return table.get(0).obj.size();
    }

    public ArrayList<Class<? extends Value>> getTypes(){
        ArrayList<Class<? extends Value>> returnable = new ArrayList<>();
        for(Column curCol:this.table){
            returnable.add(curCol.type);
        }
        return returnable;
    }

    public Column get(String colname) {
        for(Column currentColumn : table) {
            if(currentColumn.name.equals(colname))
                return currentColumn;
        }
        return null;
    }

    public ArrayList<Column> get(String [] cols, boolean copy) throws CloneNotSupportedException {
        ArrayList<Column> toBeReturned = new ArrayList<Column>();
        for(String currentColName : cols) {
            for(Column currentCol : table) {
                if(currentColName.equals(currentCol.name)) {
                    if(copy) {
                        toBeReturned.add(currentCol.clone());
                    }
                    else {
                        toBeReturned.add(currentCol);
                    }
                }
            }
        }
        return toBeReturned;
    }

    public int getColNum(){
        int i=0;
        for(Column currentCol : this.table)
            i++;
        return i;
    }

    //dziala
    public String[] getColNames(){
        String[] returnable = new String[getColNum()];
        int i=0;
        for(Column currentCol : this.table) {
            returnable[i] = currentCol.name;
            i++;
        }
        return returnable;
    }

    //dziala
    public DataFrame iloc(int i){//trzeba by cos dopisac do ilocow obu
        DataFrame returnable = new DataFrame();
        //returnable.add(getItem(i));
        return returnable;
    }

    //dziala
    public DataFrame iloc(int from, int to){
        DataFrame returnable = new DataFrame(getColNames(),types);

        for(int i = from; i <= to; i++){
            returnable.add(getItem(i));
        }
        return returnable;
    }

    public void print(){
        for(String currentName: getColNames()){
            System.out.println(currentName);
        }
        for(int i=0; i<size(); i++){
            System.out.println(getItem(i));
        }
    }

    //getItem działa
    public ArrayList<Value> getItem (int index){
        ArrayList<Value> returnable = new ArrayList<>();
        for(Column currentCol : table) {
            returnable.add(currentCol.obj.get(index));
        }
        return returnable;
    }
    //add działa
    public void add(ArrayList<Value> item){
        int k = 0;

        for(Value currentField : item) {
            this.table.get(k).obj.add(currentField);
            k++;
        }
    }
    //tu można zrobić zliczanie jaki jest najpopularniejszy item zeby go eliminowac do sparsa
    public Object mostCommon(){
        //for(backend.Column.ob)
        return null;
    }

    public static String sledzParser(String a) {
        switch(a) {
            case "String":
                a = "java.lang.String";
                break;
            case "int":
                a = "java.lang.Integer";
                break;
            case "Int":
                a = "java.lang.Integer";
                break;
            case "double":
                a = "java.lang.Double";
                break;
            case "Double":
                a = "java.lang.Double";
                break;
            case "float":
                a = "java.lang.Float";
                break;
            case "Float":
                a = "java.lang.Float";
                break;
            case "char":
                a = "java.lang.Character";
                break;
            case "Character":
                a = "java.lang.Character";
                break;
            case "byte":
                a = "java.lang.Byte";
                break;
            case "Byte":
                a = "java.lang.Byte";
                break;
            case "boolean":
                a = "java.lang.Boolean";
                break;
            case "Boolean":
                a = "java.lang.Boolean";
                break;
            case "long":
                a = "java.lang.Long";
                break;
            case "Long":
                a = "java.lang.Long";
                break;
        }
        return a;
    }

    private int getColNumberInRow (String a){
        int i=0;
        for(String current:this.getColNames()){
            if(current.equals(a)) {
                return i;
            }
            i++;
        }
        return 0;
    }

    public LinkedList<DataFrame> toList(String[] colnames){

        LinkedList<DataFrame> returnable = new LinkedList<>();
        int[] colIds = new int[colnames.length];

        for( int i = 0; i< colnames.length; i++){
            colIds[i] = getColNumberInRow(colnames[i]);
        }

        boolean stillEquals = true;
        DataFrame toAdd = new DataFrame(getColNames(), types);

        for( int i =0; i<size()-1; i++){

            stillEquals = true;

            for(int colIterator = 0; colIterator<colnames.length; colIterator++){

                if(!getItem(i).get(colIds[colIterator]).eq(getItem(i+1).get(colIds[colIterator]))){
                    stillEquals = false;
                }
            }

            toAdd.add(getItem(i));

            if(!stillEquals) {
                returnable.add(toAdd);
                toAdd = new DataFrame(getColNames(),types);
            }
        }

        if(stillEquals){
            toAdd.add(getItem(size()-1));
            returnable.add(toAdd);
        }

        return returnable;
    }


    class SortingDataFrame implements Comparator<ArrayList<Value>>{
        private String[] parameters;

        private SortingDataFrame(String[] parameters) {
            this.parameters = parameters;
        }

        @Override
        public int compare(ArrayList<Value> o1, ArrayList<Value> o2) {
            for(String currentCol : parameters){
                if (!o1.get(getColNumberInRow(currentCol)).equals(o2.get(getColNumberInRow(currentCol)))){
                    return o1.get(getColNumberInRow(currentCol)).lte(o2.get(getColNumberInRow(currentCol))) ?  -1 : 1;
                }
            }
            return 1;
        }
    }

    public ArrayList<ArrayList<Value>> getArrayList(){
        ArrayList<ArrayList<Value>> returnable = new ArrayList<>();
        for(int i =0; i<size(); i++){
            returnable.add(getItem(i));
        }
        return returnable;
    }

    //tu trzeba robić
    public DFGroup groupby(String[] colnames){
        DFGroup returnable = new DFGroup(getColNames(),types, colnames);
        ArrayList<ArrayList<Value>> ar = getArrayList();
        Collections.sort(ar,new SortingDataFrame(colnames));

        for(ArrayList<Value> curItem : ar) {
            returnable.frame.add(curItem);
        }
        //returnable.frame.print();
        returnable.separatedDFs = returnable.frame.toList(colnames);
        return returnable;
    }

    public LinkedList<DataFrame> groupby(String colname){
        LinkedList<DataFrame> returnable = new LinkedList<>();
        int colNum = 0;
        for(Column currentCol : table){
            if(currentCol.name.equals(colname)){
                for(int i=0; i<table.size(); i++){
                    boolean isThereAny = false;
                    for(DataFrame currentDf : returnable){
                        if(currentDf.table.get(colNum).obj.get(0).equals(getItem(i).get(getColNumberInRow(colname)))){
                            currentDf.add(getItem(i));
                            isThereAny = true;
                        }
                    }
                    if(!isThereAny){
                        DataFrame tmpFrame = new DataFrame(getColNames(),types);
                        tmpFrame.add(getItem(i));
                        returnable.add(tmpFrame);
                    }
                }
            }
            colNum++;
        }
        return returnable;
    }
}

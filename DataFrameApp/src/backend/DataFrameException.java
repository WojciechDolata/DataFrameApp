package backend;

public class DataFrameException extends Exception{

    public DataFrameException(int lenA, int lenB, String nameA, String nameB){
        super("Błąd przy dodawaniu kolumn: "+nameA+ " i "+nameB + " długości to odpowiednio: "+Integer.toString(lenA)+ " i "+Integer.toString(lenB));
    }

    public DataFrameException(String colName, int row){
        super("Błąd w kolumnie: " + colName + ", rzędzie: "+Integer.toString(row));
    }
}

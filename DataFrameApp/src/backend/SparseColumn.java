package backend;

import java.util.ArrayList;

public class SparseColumn {

    public String name;
    public Class<? extends Value> type;
    public Value toHide;
    public ArrayList<CooValue> obj;

    public Value copyItemId(int id){
        for(CooValue currentVal : obj){
            if(currentVal.id == id)
                return currentVal.val;
        }
        return toHide;
    }

    public SparseColumn(String nameToBe, Class<? extends Value> dataType, Value hide) {
        this.name = nameToBe;
        this.type = dataType;
        obj = new ArrayList<CooValue>();
        toHide = hide;
    }
}

package backend;

import java.util.ArrayList;

public class Column implements Cloneable{

    public String name;
    public Class<? extends Value> type;

    public ArrayList<Value> obj;

    public Column mul(Value multiplier) throws DataFrameException{
        Column returnable = new Column(name, type);
        int i=0;
        for(Value curObj : this.obj) {
            if(curObj.getClass().toString().equals(multiplier.getClass().toString())){
                returnable.obj.add(curObj.mul(multiplier));
            }
            else {
                throw new DataFrameException(name,i);
            }
            i++;
        }

        return returnable;
    }

    public Column div(Value divisor) throws DataFrameException{
        Column returnable = new Column(name, type);
        int i=0;
        for(Value curObj : this.obj) {
            if(curObj.getClass().toString().equals(divisor.getClass().toString())){
                returnable.obj.add(curObj.div(divisor));
            }
            else {
                throw new DataFrameException(name,i);
            }
            i++;
        }

        return returnable;
    }

    public Column add(Value other) throws DataFrameException{
        Column returnable = new Column(name, type);
        int i=0;
        for(Value curObj : this.obj) {
            if(curObj.getClass().toString().equals(other.getClass().toString())){
                returnable.obj.add(curObj.add(other));
            }
            else {
                throw new DataFrameException(name,i);
            }
            i++;
        }

        return returnable;
    }

    public Column sub(Value other) throws DataFrameException{
        Column returnable = new Column(name, type);
        int i=0;
        for(Value curObj : this.obj) {
            if(curObj.getClass().toString().equals(other.getClass().toString())){
                returnable.obj.add(curObj.sub(other));
            }
            else {
                throw new DataFrameException(name,i);
            }
            i++;
        }

        return returnable;
    }

    public Column clone() throws CloneNotSupportedException{

        return (Column) super.clone();

    }

    public Column mul(Column other) throws DataFrameException{
        Column returnable = new Column(name, type);
        int i=0;
        if(other.obj.size()!=this.obj.size()){
            throw new DataFrameException(this.obj.size(),other.obj.size(),this.name,other.name);
        }
        for(Value curObj : this.obj) {
            if(curObj.getClass().toString().equals(other.obj.get(i).getClass().toString())){
                returnable.obj.add(curObj.mul(other.obj.get(i)));
            }
            else {
                throw new DataFrameException(name,i);
            }
            i++;
        }

        return returnable;
    }

    public Column div(Column other) throws DataFrameException{
        Column returnable = new Column(name, type);
        int i=0;
        if(other.obj.size()!=this.obj.size()){
            throw new DataFrameException(this.obj.size(),other.obj.size(),this.name,other.name);
        }
        for(Value curObj : this.obj) {
            if(curObj.getClass().toString().equals(other.obj.get(i).getClass().toString())){
                returnable.obj.add(curObj.div(other.obj.get(i)));
            }
            else {
                throw new DataFrameException(name,i);
            }
            i++;
        }

        return returnable;
    }

    public Column add(Column other) throws DataFrameException{
        Column returnable = new Column(name, type);
        int i=0;
        if(other.obj.size()!=this.obj.size()){
            throw new DataFrameException(this.obj.size(),other.obj.size(),this.name,other.name);
        }
        for(Value curObj : this.obj) {
            if(curObj.getClass().toString().equals(other.obj.get(i).getClass().toString())){
                returnable.obj.add(curObj.add(other.obj.get(i)));
            }
            else {
                throw new DataFrameException(name,i);
            }
            i++;
        }

        return returnable;
    }

    public Column sub(Column other) throws DataFrameException{
        Column returnable = new Column(name, type);
        int i=0;
        if(other.obj.size()!=this.obj.size()){
            throw new DataFrameException(this.obj.size(),other.obj.size(),this.name,other.name);
        }
        for(Value curObj : this.obj) {
            if(curObj.getClass().toString().equals(other.obj.get(i).getClass().toString())){
                returnable.obj.add(curObj.sub(other.obj.get(i)));
            }
            else {
                throw new DataFrameException(name,i);
            }
            i++;
        }

        return returnable;
    }

    private Value createValObj(String s){
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

    public Value min(){

        Value returnable = createValObj(type.getName());
        for(Value curObj: obj){
            if(curObj.lte(returnable)){
                returnable = curObj;
            }
        }

        return returnable;
    }

    public Value max(){

        Value returnable = createValObj(type.getName());
        for(Value curObj: obj){
            if(curObj.gte(returnable)){
                returnable = curObj;
            }
        }

        return returnable;
    }

    public Value sum(){

        Value returnable = createValObj(type.getName());
        returnable.set("0");
        for(Value curObj: obj){
            System.out.println(returnable);
            returnable = returnable.add(curObj);
        }
        System.out.println(returnable);
        return returnable;
    }

    public Value std(){

        Value returnable = createValObj(type.getName());
        for(Value curObj: obj){
            if(curObj.lte(returnable)){
                returnable = curObj;
            }
        }

        return returnable;
    }

    public Column(String nameToBe, Class<? extends Value> dataType) {
        this.name = nameToBe;
        this.type = dataType;
        obj = new ArrayList<>();
    }
}

package backend;

import java.util.Objects;

public class IntegerValue extends Value{

    public Integer val;

    public IntegerValue(Integer valToBe){
        val = valToBe;
    }

    public IntegerValue(String valToBe){
        val = Integer.parseInt(valToBe);
    }

    public IntegerValue(){
        val = 0;
    }

    @Override
    public Object get() {
        return val;
    }

    @Override
    public void set(String other) {
        IntegerValue tmp = new IntegerValue(other);
        this.val = tmp.val;
    }

    @Override
    public Value create(String other) {
        return new IntegerValue(other);
    }

    @Override
    public String toString(){
        return String.valueOf(val);
    }

    @Override
    public Value add(Value other){
        if (other instanceof  IntegerValue) {
            return  new IntegerValue(val + (Integer)other.getVal());
        }
        else {
            throw new ArithmeticException("Wartosci maja niekompatybilne typy");
        }
    }

    @Override
    public Value sub(Value other){
        if (other instanceof  IntegerValue) {
            return  new IntegerValue(val - (Integer)other.getVal());
        }
        else {
            throw new ArithmeticException("Wartosci maja niekompatybilne typy");
        }
    }

    @Override
    public Value mul(Value other){
        if (other instanceof  IntegerValue) {
            return  new IntegerValue(val * (Integer)other.getVal());
        }
        else {
            throw new ArithmeticException("Wartosci maja niekompatybilne typy");
        }
    }

    @Override
    public Value div(Value other){
        if (other instanceof  IntegerValue) {
            return  new IntegerValue(val / (Integer)other.getVal());
        }
        else {
            throw new ArithmeticException("Wartosci maja niekompatybilne typy");
        }
    }

    @Override
    public Value pow(Value other){
        if (other instanceof  IntegerValue) {
            Double g = Math.pow(val, (Integer)other.getVal());
            return  new IntegerValue(g.intValue());
        }
        else {
            throw new ArithmeticException("Wartosci maja niekompatybilne typy");
        }
    }

    @Override
    public boolean eq(Value other){
        if (other instanceof  IntegerValue && other.getVal().equals(val)) {
            return true;
        }
        else if (other instanceof  IntegerValue && !other.getVal().equals(val)) {
            return false;
        }
        else {
            throw new ArithmeticException("Wartosci maja niekompatybilne typy");
        }
    }

    @Override
    public boolean neq(Value other){
        return !eq(other);
    }

    @Override
    public boolean gte(Value other){

        if (other instanceof IntegerValue) {
            return ((Integer) other.getVal() >= val);
        }
        else {
            throw new ArithmeticException("Wartosci maja niekompatybilne typy");
        }
    }

    @Override
    public boolean lte(Value other){

        if (other instanceof IntegerValue){
            return ((Integer)other.getVal() >= val);
        }
        else {
            throw new ArithmeticException("Wartosci maja niekompatybilne typy");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof IntegerValue)) return false;
        IntegerValue that = (IntegerValue) o;
        return Objects.equals(val, that.val);
    }

    @Override
    public int hashCode() {
        return Objects.hash(val);
    }

    @Override
    public Object getVal(){
        return val;
    }


}
package backend;

import java.util.Objects;

public class DoubleValue extends Value {

    public Double val;

    public DoubleValue(Double valToBe){
        val = valToBe;
    }

    public DoubleValue(Integer valToBe){
        val = valToBe.doubleValue();
    }


    public DoubleValue(String valToBe){
        val = Double.parseDouble(valToBe);
    }

    public DoubleValue(){
        val = 0.0;
    }

    @Override
    public Object get() {
        return val;
    }

    @Override
    public void set(String other) {
        DoubleValue tmp = new DoubleValue(other);
        this.val = tmp.val;
    }

    @Override
    public String toString() {
        return val.toString();
    }

    @Override
    public Value add(Value other){
        if (other instanceof  DoubleValue) {
            return  new DoubleValue(val + (Double) other.getVal());
        }
        else {
            throw new ArithmeticException("Wartosci maja niekompatybilne typy");
        }
    }

    @Override
    public Value sub(Value other){
        if (other instanceof  DoubleValue) {
            return  new DoubleValue(val - (Double) other.getVal());
        }
        else {
            throw new ArithmeticException("Wartosci maja niekompatybilne typy");
        }
    }

    @Override
    public Value mul(Value other) {
        if (other instanceof  DoubleValue) {
            return  new DoubleValue(val * (Double) other.getVal());
        }
        else {
            throw new ArithmeticException("Wartosci maja niekompatybilne typy");
        }
    }

    @Override
    public Value div(Value other) {
        if (other instanceof  DoubleValue) {
            return  new DoubleValue(val / (Double) other.getVal());
        }
        else {
            throw new ArithmeticException("Wartosci maja niekompatybilne typy");
        }
    }

    @Override
    public Value pow(Value other) {
        if (other instanceof  DoubleValue) {
            return  new DoubleValue(Math.pow(val, (Double) other.getVal()));
        }
        else {
            throw new ArithmeticException("Wartosci maja niekompatybilne typy");
        }
    }

    @Override
    public boolean eq(Value other) {
        if (other instanceof  DoubleValue) {
            return other.getVal().equals(val);
        }
        else {
            throw new ArithmeticException("Wartosci maja niekompatybilne typy");
        }
    }

    @Override
    public boolean lte(Value other) {
        if (other instanceof DoubleValue) {
            return ((Double) other.getVal() >= val);
        }
        else {
            throw new ArithmeticException("Wartosci maja niekompatybilne typy");
        }
    }

    @Override
    public boolean gte(Value other) {
        if (other instanceof DoubleValue) {
            return ((Double) other.getVal() <= val);
        }
        else {
            throw new ArithmeticException("Wartosci maja niekompatybilne typy");
        }
    }

    @Override
    public boolean neq(Value other) {
        return !eq(other);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DoubleValue)) return false;
        DoubleValue that = (DoubleValue) o;
        return Objects.equals(val, that.val);
    }

    @Override
    public int hashCode() {
        return Objects.hash(val);
    }

    @Override
    public Value create(String s) {
        return new DoubleValue(s);
    }

    @Override
    public Object getVal() {
        return val;
    }
}

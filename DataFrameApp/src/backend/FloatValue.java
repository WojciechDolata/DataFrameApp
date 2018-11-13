package backend;

import java.util.Objects;

public class FloatValue extends Value {
    public Float val;

    public FloatValue(Double valToBe){
        val = valToBe.floatValue();
    }

    public FloatValue(Float valToBe){
        val = valToBe;
    }

    public FloatValue(Integer valToBe){
        val = valToBe.floatValue();
    }

    public FloatValue(String valToBe){
        val = Float.parseFloat(valToBe);
    }

    @Override
    public Object get() {
        return val;
    }

    @Override
    public void set(String other) {
        FloatValue tmp = new FloatValue(other);
        this.val = tmp.val;
    }

    @Override
    public String toString() {
        return val.toString();
    }

    @Override
    public Value add(Value other) {
        if (other instanceof  FloatValue) {
            return  new FloatValue(val + (Float) other.getVal());
        }
        else {
            throw new ArithmeticException("Wartosci maja niekompatybilne typy");
        }
    }

    @Override
    public Value sub(Value other) {
        if (other instanceof  FloatValue) {
            return  new FloatValue(val - (Float) other.getVal());
        }
        else {
            throw new ArithmeticException("Wartosci maja niekompatybilne typy");
        }
    }

    @Override
    public Value mul(Value other) {
        if (other instanceof  FloatValue) {
            return  new FloatValue(val * (Float) other.getVal());
        }
        else {
            throw new ArithmeticException("Wartosci maja niekompatybilne typy");
        }
    }

    @Override
    public Value div(Value other) {
        if (other instanceof  FloatValue) {
            return  new FloatValue(val / (Float) other.getVal());
        }
        else {
            throw new ArithmeticException("Wartosci maja niekompatybilne typy");
        }
    }

    @Override
    public Value pow(Value other) {
        if (other instanceof  FloatValue) {
            return  new FloatValue(Math.pow(val, (Float) other.getVal()));
        }
        else {
            throw new ArithmeticException("Wartosci maja niekompatybilne typy");
        }
    }

    @Override
    public boolean eq(Value other) {
        if (other instanceof  FloatValue) {
            return other.getVal().equals(val);
        }
        else {
            throw new ArithmeticException("Wartosci maja niekompatybilne typy");
        }
    }

    @Override
    public boolean lte(Value other) {
        if (other instanceof FloatValue) {
            return ((Float) other.getVal() >= val);
        }
        else {
            throw new ArithmeticException("Wartosci maja niekompatybilne typy");
        }
    }

    @Override
    public boolean gte(Value other) {
        if (other instanceof FloatValue) {
            return ((Float) other.getVal() <= val);
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
        if (!(o instanceof FloatValue)) return false;
        FloatValue that = (FloatValue) o;
        return Objects.equals(val, that.val);
    }

    @Override
    public int hashCode() {
        return Objects.hash(val);
    }

    @Override
    public Value create(String s) {
        return new FloatValue(s);
    }

    @Override
    public Object getVal() {
        return val;
    }
}

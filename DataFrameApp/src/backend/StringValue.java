package backend;

import java.util.Objects;

public class StringValue extends Value {

    public String val;

    public StringValue() {
        val = "";
    }

    public StringValue(String valToBe) {
        val = valToBe;
    }

    StringValue(Value valToBe) {
        val = valToBe.toString();
    }

    StringValue(Object valToBe) {
        val = valToBe.toString();
    }


    @Override
    public Object get() {
        return val;
    }

    @Override
    public void set(String other) {
        this.val = other;
    }

    @Override
    public String toString() {
        return val;
    }

    @Override
    public Value add(Value other) {
        if (other instanceof  StringValue) {
            return  new StringValue(val + other.getVal());
        }
        else {
            throw new ArithmeticException("Wartosci maja niekompatybilne typy");
        }
    }

    @Override
    public Value sub(Value other) {
        throw new ArithmeticException("Nie mozna przeprowadzac operacji matamatycznych na stringach");
    }

    @Override
    public Value mul(Value other) {
        throw new ArithmeticException("Nie mozna przeprowadzac operacji matamatycznych na stringach");
    }

    @Override
    public Value div(Value other) {
        throw new ArithmeticException("Nie mozna przeprowadzac operacji matamatycznych na stringach");
    }

    @Override
    public Value pow(Value other) {
        throw new ArithmeticException("Nie mozna przeprowadzac operacji matamatycznych na stringach");
    }

    @Override
    public boolean eq(Value other) {
        if (other instanceof  StringValue) {
            return other.getVal().equals(val);
        }
        else {
            throw new ArithmeticException("Wartosci maja niekompatybilne typy");
        }
    }

    @Override
    public boolean lte(Value other) {
        if (other instanceof StringValue) {
            String tmp = (String) other.getVal();
            if(this.val.compareTo(tmp)<=0)
                return true;
            return false;
        }
        else {
            throw new ArithmeticException("Wartosci maja niekompatybilne typy");
        }
    }

    @Override
    public boolean gte(Value other) {
        if (other instanceof StringValue) {
            String tmp = (String) other.getVal();
            if(this.val.compareTo(tmp)>=0)
                return true;
            return false;
        }
        else {
            throw new ArithmeticException("Wartosci maja niekompatybilne typy");
        }
        //throw new ArithmeticException("Nie mozna przeprowadzac operacji matamatycznych na stringach");
    }

    @Override
    public boolean neq(Value other) {
        return !eq(other);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof StringValue)) return false;
        StringValue that = (StringValue) o;
        return Objects.equals(val, that.val);
    }

    @Override
    public int hashCode() {
        return Objects.hash(val);
    }

    @Override
    public Value create(String s) {
        return new StringValue(s);
    }

    @Override
    public Object getVal() {
        return toString();
    }
}

package backend;

public class CooValue extends Value{
    public int id;
    public Value val;

    @Override
    public void set(String other) {

    }

    @Override
    public Object get() {
        return id;
    }

    public CooValue(int idToBe, Value valToBe) {
        id = idToBe;
        val = valToBe;
    }

    CooValue() {
        
    }

    @Override
    public String toString() {
        return val.toString();
    }

    @Override
    public Value add(Value other) {
        return val.add(other);
    }

    @Override
    public Value sub(Value other) {
        return val.sub(other);
    }

    @Override
    public Value mul(Value other) {
        return val.mul(other);
    }

    @Override
    public Value div(Value other) {
        return val.div(other);
    }

    @Override
    public Value pow(Value other) {
        return val.pow(other);
    }

    @Override
    public boolean eq(Value other) {
        return val.eq(other);
    }

    @Override
    public boolean lte(Value other) {
        return val.lte(other);
    }

    @Override
    public boolean gte(Value other) {
        return val.gte(other);
    }

    @Override
    public boolean neq(Value other) {
        return val.neq(other);
    }

    @Override
    public boolean equals(Object other) {
        return val.equals(other);
    }

    @Override
    public int hashCode() {
        return val.hashCode();
    }

    @Override
    public Value create(String s) {
        return val.create(s);
    }

    @Override
    public Object getVal() {
        return val.getVal();
    }
}
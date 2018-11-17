package backend;

import java.util.GregorianCalendar;
import java.util.Objects;

public class DateTimeValue extends Value {

    GregorianCalendar val;


    public DateTimeValue(String dateToBe){
        String[] tmpDate = dateToBe.split("-");
        val = new GregorianCalendar(Integer.parseInt(tmpDate[0]),
                Integer.parseInt(tmpDate[1]),Integer.parseInt(tmpDate[2]));
        //System.out.println(val);

    }

    public DateTimeValue(String date, String time){

        String[] tmpDate = date.split("-");
        String[] tmpTime = time.split(":");
        val = new GregorianCalendar(Integer.parseInt(tmpDate[0]),Integer.parseInt(tmpDate[1]),
                Integer.parseInt(tmpDate[2]),Integer.parseInt(tmpTime[0]),Integer.parseInt(tmpTime[1]));

    }

    public DateTimeValue(){
        val = null;
    }

    public DateTimeValue(GregorianCalendar other){
        val = other;
    }

    @Override
    public Object get() {
        return val;
    }

    @Override
    public void set(String other) {
        DateTimeValue tmp = new DateTimeValue(other);
        this.val = tmp.val;
    }

    @Override
    public String toString() {
        return val.toString();
    }

    @Override
    public Value add(Value other) {
        throw new ArithmeticException("Nie mozna wykonywac dzialan arytmetycznych na datach. ");
    }

    @Override
    public Value sub(Value other) {
        throw new ArithmeticException("Nie mozna wykonywac dzialan arytmetycznych na datach. ");
    }

    @Override
    public Value mul(Value other) {
        throw new ArithmeticException("Nie mozna wykonywac dzialan arytmetycznych na datach. ");
    }

    @Override
    public Value div(Value other) {
        throw new ArithmeticException("Nie mozna wykonywac dzialan arytmetycznych na datach. ");
    }

    @Override
    public Value pow(Value other) {
        throw new ArithmeticException("Nie mozna wykonywac dzialan arytmetycznych na datach. ");
    }

    @Override
    public boolean eq(Value other) {
        if (other instanceof DateTimeValue) {
            return (equals(other));
        }
        else {
            throw new ArithmeticException("Wartosci maja niekompatybilne typy");
        }
    }

    @Override
    public boolean lte(Value other) {
        if (other instanceof DateTimeValue) {
            return (val.before(other.getVal()) || equals(other));
        }
        else {
            throw new ArithmeticException("Wartosci maja niekompatybilne typy");
        }
    }

    @Override
    public boolean gte(Value other) {
        if (other instanceof DateTimeValue) {
            return (val.after(other.getVal()) || equals(other));
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
        if (!(o instanceof DateTimeValue)) return false;
        DateTimeValue that = (DateTimeValue) o;
        return Objects.equals(val, that.val);
    }

    @Override
    public int hashCode() {
        return Objects.hash(val);
    }

    @Override
    public Value create(String s) {
        return new DateTimeValue(s);
    }

    @Override
    public Object getVal() {
        return val;
    }
}

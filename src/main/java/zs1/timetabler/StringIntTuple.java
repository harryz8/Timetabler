package zs1.timetabler;

public class StringIntTuple {
    String string;
    int integer;
    public StringIntTuple(String string, int integer) {
        this.string = string;
        this.integer = integer;
    }

    public void setInteger(int integer) {
        this.integer = integer;
    }

    public void setString(String string) {
        this.string = string;
    }

    public int getInteger() {
        return integer;
    }

    public String getString() {
        return string;
    }

    @Override public String toString() {
        return string;
    }
}

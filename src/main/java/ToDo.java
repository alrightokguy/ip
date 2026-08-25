public class ToDo extends Task {

    private static final String TYPE = "T";

    public ToDo(String desc) {
        super(desc);
    }

    public ToDo(String desc, boolean status) {
        super(desc, status);
    }

    @Override
    public String toString() {
        return String.format("[%s]%s", TYPE, super.toString());
    }

    @Override
    public String toCsv() {
        return String.format("%s,%s", TYPE, super.toCsv());
    }
}
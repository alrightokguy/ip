public class Deadline extends Task {

    private static final String TYPE = "D";
    private final String by;

    public Deadline(String desc, String by) {
        super(desc);
        this.by = by;
    }

    public Deadline(String desc, boolean status, String by) {
        super(desc, status);
        this.by = by;
    }

    @Override
    public String toString() {
        return String.format("[%s]%s (by: %s)", TYPE, super.toString(), this.by);
    }

    @Override
    public String toCsv() {
        return String.format("%s,%s,%s", TYPE, super.toCsv(), this.by);
    }
}

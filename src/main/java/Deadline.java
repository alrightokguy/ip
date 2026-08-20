public class Deadline extends Task {

    private final String type;
    private final String by;

    public Deadline(String desc, String by) {
        super(desc);
        this.type = "D";
        this.by = by;
    }

    @Override
    public String toString() {
        return String.format("[%s]%s (by: %s)", this.type, super.toString(), this.by);
    }
}

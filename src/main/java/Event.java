public class Event extends Task {

    private static final String TYPE = "E";
    private final String from;
    private final String to;

    public Event(String desc, String from, String to) {
        super(desc);
        this.from = from;
        this.to = to;
    }

    public Event(String desc, boolean status, String from, String to) {
        super(desc, status);
        this.from = from;
        this.to = to;
    }

    @Override
    public String toString() {
        return String.format("[%s]%s (from: %s to: %s)", TYPE, super.toString(), this.from, this.to);
    }

    @Override
    public String toCsv() {
        return String.format("%s,%s,%s,%s", TYPE, super.toCsv(), this.from, this.to);
    }
}

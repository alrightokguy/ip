public class Event extends Task {

    private final String type;
    private final String from;
    private final String to;

    public Event(String desc, String from, String to) {
        super(desc);
        this.type = "E";
        this.from = from;
        this.to = to;
    }

    @Override
    public String toString() {
        return String.format("[%s]%s (from: %s to: %s)", this.type, super.toString(), this.from, this.to);
    }
}

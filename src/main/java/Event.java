import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Event extends Task {

    private static final DateTimeFormatter DATE_TIME_FORMATTER =
            DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
    private static final String TYPE = "E";
    private final LocalDateTime from;
    private final LocalDateTime to;

    public Event(String desc, LocalDateTime from, LocalDateTime to) {
        super(desc);
        this.from = from;
        this.to = to;
    }

    public Event(String desc, boolean status, LocalDateTime from, LocalDateTime to) {
        super(desc, status);
        this.from = from;
        this.to = to;
    }

    @Override
    public String toString() {
        return String.format(
                "[%s]%s (from: %s to: %s)",
                TYPE,
                super.toString(),
                DATE_TIME_FORMATTER.format(this.from),
                DATE_TIME_FORMATTER.format(this.to)
        );
    }

    @Override
    public String toCsv() {
        return String.format("%s,%s,%s,%s", TYPE, super.toCsv(), this.from, this.to);
    }
}
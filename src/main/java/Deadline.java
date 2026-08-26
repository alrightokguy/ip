import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Deadline extends Task {

    private static final DateTimeFormatter DATE_TIME_FORMATTER =
            DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
    private static final String TYPE = "D";
    private final LocalDateTime by;

    public Deadline(String desc, LocalDateTime by) {
        super(desc);
        this.by = by;
    }

    public Deadline(String desc, boolean status, LocalDateTime by) {
        super(desc, status);
        this.by = by;
    }

    @Override
    public String toString() {
        return String.format("[%s]%s (by: %s)", TYPE, super.toString(), DATE_TIME_FORMATTER.format(by));
    }

    @Override
    public String toCsv() {
        return String.format("%s,%s,%s", TYPE, super.toCsv(), by);
    }
}

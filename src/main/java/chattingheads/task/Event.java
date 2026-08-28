package chattingheads.task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents a task that occurs between a specified start and end date and time.
 */
public class Event extends Task {

    private static final DateTimeFormatter DATE_TIME_FORMATTER =
            DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
    private static final String TYPE = "E";
    private final LocalDateTime from;
    private final LocalDateTime to;

    /**
     * Creates an incomplete event task with the given description,
     * start date and time, and end date and time.
     *
     * @param description Description of the task.
     * @param from Start date and time of the event.
     * @param to End date and time of the event.
     */
    public Event(String description, LocalDateTime from, LocalDateTime to) {
        super(description);
        this.from = from;
        this.to = to;
    }

    /**
     * Creates an event task with the given description, completion status,
     * start date and time, and end date and time.
     *
     * @param description Description of the task.
     * @param isDone Completion status of the task.
     * @param from Start date and time of the event.
     * @param to End date and time of the event.
     */
    public Event(String description, boolean isDone, LocalDateTime from, LocalDateTime to) {
        super(description, isDone);
        this.from = from;
        this.to = to;
    }

    @Override
    public String toString() {
        return String.format(
                "[%s]%s (from: %s to: %s)",
                TYPE,
                super.toString(),
                DATE_TIME_FORMATTER.format(from),
                DATE_TIME_FORMATTER.format(to)
        );
    }

    @Override
    public String toCsv() {
        return String.format("%s,%s,%s,%s", TYPE, super.toCsv(), from, to);
    }
}

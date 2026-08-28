package chattingheads.task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents a task that must be completed by a specified date and time.
 */
public class Deadline extends Task {

    private static final DateTimeFormatter DATE_TIME_FORMATTER =
            DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
    private static final String TYPE = "D";
    private final LocalDateTime by;

    /**
     * Creates an incomplete deadline task with the given description and deadline.
     *
     * @param description Description of the task.
     * @param by Date and time by which the task should be completed.
     */
    public Deadline(String description, LocalDateTime by) {
        super(description);
        this.by = by;
    }

    /**
     * Creates a deadline task with the given description, completion status,
     * and deadline.
     *
     * @param description Description of the task.
     * @param status Completion status of the task.
     * @param by Date and time by which the task should be completed.
     */
    public Deadline(String description, boolean status, LocalDateTime by) {
        super(description, status);
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

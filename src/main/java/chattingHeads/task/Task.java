package chattingHeads.task;

import java.io.IOException;
import java.time.LocalDateTime;

/**
 * Represents a task with a description and completion status.
 */
public abstract class Task {

    private final String description;
    private boolean status;

    /**
     * Creates an incomplete task with the given description.
     *
     * @param description Description of the task.
     */
    public Task(String description) {
        this.description = description;
        status = false;
    }

    /**
     * Creates a task with the given description and completion status.
     *
     * @param description Description of the task.
     * @param status Completion status of the task.
     */
    public Task(String description, boolean status) {
        this.description = description;
        this.status = status;
    }

    /**
     * Marks this task as completed.
     */
    public void mark() {
        status = true;
    }

    /**
     * Marks this task as incomplete.
     */
    public void unmark() {
        status = false;
    }

    @Override
    public String toString() {
        return String.format("[%s] %s", status ? "X" : " ", description);
    }

    /**
     * Generates a task from its CSV representation.
     *
     * @param line CSV representation of the task.
     * @return Task represented from the CSV data.
     * @throws IOException If the CSV data is corrupted.
     */
    public static Task fromCsv(String line) throws IOException {
        String[] fields = line.split(",");
        String type = fields[0];
        return switch (type) {
            case "T" -> new Todo(fields[1], Boolean.parseBoolean(fields[2]));
            case "D" -> new Deadline(
                    fields[1],
                    Boolean.parseBoolean(fields[2]),
                    LocalDateTime.parse(fields[3])
            );
            case "E" -> new Event(
                    fields[1],
                    Boolean.parseBoolean(fields[2]),
                    LocalDateTime.parse(fields[3]),
                    LocalDateTime.parse(fields[4])
            );
            default -> throw new IOException();
        };
    }

    /**
     * Returns the CSV representation of this task.
     *
     * @return CSV representation of this task.
     */
    public String toCsv() {
        return String.format("%s,%s", description, status);
    }

    public boolean isDone() {
        return status;
    }
}

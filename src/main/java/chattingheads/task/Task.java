package chattingheads.task;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.Arrays;

import chattingheads.exception.InvalidInputException;

/**
 * Represents a task with a description and completion status.
 */
public abstract class Task {

    private final String description;
    private boolean isDone;

    /**
     * Creates an incomplete task with the given description.
     *
     * @param description Description of the task.
     */
    public Task(String description) {
        this.description = description;
        isDone = false;
    }

    /**
     * Creates a task with the given description and completion status.
     *
     * @param description Description of the task.
     * @param isDone      Completion status of the task.
     */
    public Task(String description, boolean isDone) {
        this.description = description;
        this.isDone = isDone;
    }

    /**
     * Marks this task as completed.
     */
    public void mark() {
        isDone = true;
    }

    /**
     * Marks this task as incomplete.
     */
    public void unmark() {
        isDone = false;
    }

    @Override
    public String toString() {
        return String.format("[%s] %s", isDone ? "X" : " ", description);
    }

    /**
     * Generates a task from its CSV representation.
     *
     * @param line CSV representation of the task.
     * @return Task represented from the CSV data.
     * @throws InvalidInputException If the CSV data is corrupted or a task has an unrecognised type.
     */
    public static Task fromCsv(String line) throws InvalidInputException {
        try {
            String[] fields = line.split(",", -1);
            String type = fields[0];

            return switch (type) {
                case "T" -> {
                    String description = String.join(
                            ",", Arrays.copyOfRange(fields, 1, fields.length - 1));
                    boolean isDone = Boolean.parseBoolean(fields[fields.length - 1]);

                    yield new Todo(description, isDone);
                }
                case "D" -> {
                    String description = String.join(
                            ",", Arrays.copyOfRange(fields, 1, fields.length - 2));
                    boolean isDone = Boolean.parseBoolean(fields[fields.length - 2]);
                    LocalDateTime deadline = LocalDateTime.parse(fields[fields.length - 1]);

                    yield new Deadline(description, isDone, deadline);
                }
                case "E" -> {
                    String description = String.join(
                            ",", Arrays.copyOfRange(fields, 1, fields.length - 3));
                    boolean isDone = Boolean.parseBoolean(fields[fields.length - 3]);
                    LocalDateTime start = LocalDateTime.parse(fields[fields.length - 2]);
                    LocalDateTime end = LocalDateTime.parse(fields[fields.length - 1]);

                    yield new Event(description, isDone, start, end);
                }
                default -> throw InvalidInputException.invalidInput("task type");
            };
        } catch (IndexOutOfBoundsException | DateTimeParseException e) {
            throw InvalidInputException.invalidInput("stored task");
        }
    }

    /**
     * Returns the CSV representation of this task.
     *
     * @return CSV representation of this task.
     */
    public String toCsv() {
        return String.format("%s,%s", description, isDone);
    }

    public boolean isDone() {
        return isDone;
    }

    public String getDescription() {
        return description;
    }
}

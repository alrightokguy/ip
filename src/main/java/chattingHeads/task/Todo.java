package chattingHeads.task;

/**
 * Represents a task without a specific date or time.
 */
public class Todo extends Task {

    private static final String TYPE = "T";

    /**
     * Creates an incomplete todo task with the given description.
     *
     * @param description Description of the task.
     */
    public Todo(String description) {
        super(description);
    }

    /**
     * Creates a todo task with the given description and completion status.
     *
     * @param description Description of the task.
     * @param status Completion status of the task.
     */
    public Todo(String description, boolean status) {
        super(description, status);
    }

    @Override
    public String toString() {
        return String.format("[%s]%s", TYPE, super.toString());
    }

    @Override
    public String toCsv() {
        return String.format("%s,%s", TYPE, super.toCsv());
    }
}

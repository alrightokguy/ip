package chattingheads.task;

public class Todo extends Task {

    private static final String TYPE = "T";

    public Todo(String description) {
        super(description);
    }

    public Todo(String description, boolean isDone) {
        super(description, isDone);
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

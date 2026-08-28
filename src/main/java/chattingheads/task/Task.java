package chattingheads.task;

import java.io.IOException;
import java.time.LocalDateTime;

public abstract class Task {

    private final String description;
    private boolean isDone;

    public Task(String description) {
        this.description = description;
        isDone = false;
    }

    public Task(String description, boolean status) {
        this.description = description;
        this.isDone = status;
    }

    public void mark() {
        isDone = true;
    }

    public void unmark() {
        isDone = false;
    }

    @Override
    public String toString() {
        return String.format("[%s] %s", isDone ? "X" : " ", description);
    }

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

    public String toCsv() {
        return String.format("%s,%s", description, isDone);
    }

    public boolean isDone() {
        return isDone;
    }
}

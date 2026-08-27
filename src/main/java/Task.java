import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public abstract class Task {

    private static final DateTimeFormatter DATE_TIME_FORMATTER =
            DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
    private final String description;
    private boolean status;

    public Task(String description) {
        this.description = description;
        status = false;
    }

    public Task(String description, boolean status) {
        this.description = description;
        this.status = status;
    }

    public void mark() {
        status = true;
    }

    public void unmark() {
        status = false;
    }

    @Override
    public String toString() {
        return String.format("[%s] %s", status ? "X" : " ", description);
    }

    public static Task fromCsv(String line) throws IOException {
        String[] fields =  line.split(",");
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
        return String.format("%s,%s", description, status);
    }
}

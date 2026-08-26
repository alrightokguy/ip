import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public abstract class Task {

    private static final DateTimeFormatter DATE_TIME_FORMATTER =
            DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
    private final String desc;
    private boolean status;

    public Task(String desc) {
        this.desc = desc;
        status = false;
    }

    public Task(String desc, boolean status) {
        this.desc = desc;
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
        return String.format("[%s] %s", status ? "X" : " ", desc);
    }

    public static Task fromCsv(String line) throws IOException {
        String[] fields =  line.split(",");
        String type = fields[0];
        return switch (type) {
            case "T" -> new ToDo(fields[1], Boolean.parseBoolean(fields[2]));
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
        return String.format("%s,%s", desc, status);
    }
}

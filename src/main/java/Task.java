import java.io.IOException;

abstract class Task {

    private final String desc;
    private boolean status;

    public Task(String desc) {
        this.desc = desc;
        this.status = false;
    }

    public Task(String desc, boolean status) {
        this.desc = desc;
        this.status = status;
    }

    public void mark() {
        this.status = true;
    }

    public void unmark() {
        this.status = false;
    }

    @Override
    public String toString() {
        return String.format("[%s] %s", this.status ? "X" : " ", this.desc);
    }

    public static Task fromCsv(String line) throws IOException {
        String[] fields =  line.split(",");
        String type = fields[0];
        return switch (type) {
            case "T" -> new ToDo(fields[1], Boolean.parseBoolean(fields[2]));
            case "D" -> new Deadline(fields[1], Boolean.parseBoolean(fields[2]), fields[3]);
            case "E" -> new Event(fields[1], Boolean.parseBoolean(fields[2]), fields[3], fields[4]);
            default -> throw new IOException();
        };
    }

    public String toCsv() {
        return String.format("%s,%s", this.desc, this.status);
    }
}
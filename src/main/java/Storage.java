import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Storage {

    private final Path filePath;

    public Storage(String file) {
        filePath = Path.of(file);
    }

    public ArrayList<Task> load() {
        ArrayList<Task> tasks = new ArrayList<>();

        try {
            List<String> lines = Files.readAllLines(filePath);
            for (String line : lines) {
                tasks.add(Task.fromCsv(line));
            }
        } catch (IOException ignored) {
        }
        return tasks;
    }

    public void save(ArrayList<Task> tasks) {
        try {
            Files.write(filePath, tasks.stream().map(Task::toCsv).toList());
        } catch (IOException e) {
            System.out.println("Error writing tasks to file");
        }
    }
}

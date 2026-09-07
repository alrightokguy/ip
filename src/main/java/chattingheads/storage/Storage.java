package chattingheads.storage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import chattingheads.task.Task;
import chattingheads.task.TaskList;

/**
 * Handles loading tasks from and saving tasks to a file.
 */
public class Storage {

    private final Path filePath;

    /**
     * Creates a storage object that reads from and writes to the specified file.
     *
     * @param file Path of the file used to store tasks.
     */
    public Storage(String file) {
        filePath = Path.of(file);
    }

    /**
     * Loads tasks from the storage file.
     *
     * @return Tasks loaded from the file.
     */
    public ArrayList<Task> load() {
        ArrayList<Task> tasks = new ArrayList<>();

        try {
            List<String> lines = Files.readAllLines(filePath);
            for (String line : lines) {
                tasks.add(Task.fromCsv(line));
            }
        } catch (IOException e) {
            return tasks;
        }
        return tasks;
    }

    /**
     * Saves all tasks in the task list to the storage file.
     *
     * @param taskList Task list to save.
     */
    public void save(TaskList taskList) {
        try {
            Files.write(filePath, taskList.getTasks().stream().map(Task::toCsv).toList());
        } catch (IOException e) {
        }
    }
}

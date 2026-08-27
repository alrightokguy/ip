import java.util.ArrayList;

public class TaskList {

    private final ArrayList<Task> tasks;

    public TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    public Task get(int i) {
        return tasks.get(i);
    }

    public ArrayList<Task> getTasks() {
        return tasks;
    }

    public void add(Task task) {
        tasks.add(task);
    }

    public Task delete(int taskNumber) {
        return tasks.remove(taskNumber);
    }

    public void mark(int i) {
        tasks.get(i).mark();
    }

    public void unmark(int i) {
        tasks.get(i).unmark();
    }
}

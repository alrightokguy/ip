package chattingHeads.task;

import java.util.ArrayList;

import chattingHeads.exception.InvalidTaskNumberException;
import chattingHeads.storage.Storage;

public class TaskList {

    private final ArrayList<Task> tasks;

    public TaskList(Storage storage) {
        tasks = storage.load();
    }

    public TaskList() {
        tasks = new ArrayList<>();
    }

    public boolean isEmpty() {
        return tasks.isEmpty();
    }

    public int size() {
        return tasks.size();
    }

    public Task get(int index) {
        return tasks.get(index);
    }

    public ArrayList<Task> getTasks() {
        return tasks;
    }

    public void add(Task task) {
        tasks.add(task);
    }

    public Task delete(int index) throws InvalidTaskNumberException {
        validateIndex(index);
        return tasks.remove(index);
    }

    public void mark(int index) throws InvalidTaskNumberException {
        validateIndex(index);
        tasks.get(index).mark();
    }

    public void unmark(int index) throws InvalidTaskNumberException {
        validateIndex(index);
        tasks.get(index).unmark();
    }

    private void validateIndex(int index) throws InvalidTaskNumberException {
        if (index < 0 || index >= size()) {
            throw new InvalidTaskNumberException();
        }
    }
}

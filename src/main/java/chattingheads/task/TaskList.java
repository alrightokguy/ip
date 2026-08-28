package chattingheads.task;

import java.util.ArrayList;

import chattingheads.exception.InvalidTaskNumberException;
import chattingheads.storage.Storage;

/**
 * Represents a collection of tasks and provides operations for managing them.
 */
public class TaskList {

    private final ArrayList<Task> tasks;

    /**
     * Creates a task list containing tasks loaded from storage.
     *
     * @param storage Storage from which tasks are loaded.
     */
    public TaskList(Storage storage) {
        tasks = storage.load();
    }

    /**
     * Creates an empty task list.
     */
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

    /**
     * Adds a task to the task list.
     *
     * @param task Task to add.
     */
    public void add(Task task) {
        tasks.add(task);
    }

    /**
     * Deletes the task at the specified index.
     *
     * @param index Index of the task to delete.
     * @return Deleted task.
     * @throws InvalidTaskNumberException If the index is invalid.
     */
    public Task delete(int index) throws InvalidTaskNumberException {
        validateIndex(index);
        return tasks.remove(index);
    }

    /**
     * Marks the task at the specified index as completed.
     *
     * @param index Index of the task to mark.
     * @throws InvalidTaskNumberException If the index is invalid.
     */
    public void mark(int index) throws InvalidTaskNumberException {
        validateIndex(index);
        tasks.get(index).mark();
    }

    /**
     * Marks the task at the specified index as incomplete.
     *
     * @param index Index of the task to unmark.
     * @throws InvalidTaskNumberException If the index is invalid.
     */
    public void unmark(int index) throws InvalidTaskNumberException {
        validateIndex(index);
        tasks.get(index).unmark();
    }

    /**
     * Validates that an index refers to a task in the task list.
     *
     * @param index Index to validate.
     * @throws InvalidTaskNumberException If the index is invalid.
     */
    private void validateIndex(int index) throws InvalidTaskNumberException {
        if (index < 0 || index >= size()) {
            throw new InvalidTaskNumberException();
        }
    }
}

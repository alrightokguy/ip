package chattingheads.task;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

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

    /**
     * Returns whether the TaskList is empty.
     *
     * @return Boolean for whether the TaskList is empty.
     */
    public boolean isEmpty() {
        return tasks.isEmpty();
    }

    /**
     * Returns the current number of tasks in the list.
     *
     * @return Number of tasks in the list.
     */
    public int size() {
        return tasks.size();
    }

    /**
     * Returns the task at the specified index.
     *
     * @param index Index of task requested.
     * @return Task at the specified index.
     */
    public Task get(int index) {
        return tasks.get(index);
    }

    /**
     * Returns the whole list of tasks.
     *
     * @return ArrayList of tasks stored.
     */
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
     * Find all indices of tasks that contains a specified keyword in the description.
     *
     * @param keyword Keyword to search tasks for.
     * @return List of indices of matching tasks.
     */
    public List<Integer> findIndices(String keyword) {
        return IntStream.range(0, tasks.size())
                .boxed()
                .filter(i -> tasks.get(i).getDescription().toLowerCase().contains(keyword))
                .toList();
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

    public void postponeDeadline(int index, LocalDateTime newDeadline) throws InvalidTaskNumberException {
        validateIndex(index);
        Task task = tasks.get(index);

        if (!(task instanceof Deadline)) {

        }


    }

    public void rescheduleEvent(int index, LocalDateTime newStart, LocalDateTime newEnd)
            throws InvalidTaskNumberException {
        validateIndex(index);

        if
    }
}

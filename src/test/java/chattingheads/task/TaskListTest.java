package chattingheads.task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import chattingheads.exception.InvalidTaskNumberException;

public class TaskListTest {

    @Test
    public void add_validTask_taskAdded() {
        TaskList taskList = new TaskList();
        Task task = new Todo("test");

        taskList.add(task);

        assertEquals(1, taskList.size());
        assertEquals(task, taskList.get(0));
    }

    @Test
    public void delete_validIndex_correctTaskDeleted() throws InvalidTaskNumberException {
        TaskList taskList = new TaskList();
        Task task = new Todo("test");
        Task task1 = new Todo("test1");

        taskList.add(task);
        taskList.add(task1);

        Task removed = taskList.delete(0);

        assertEquals(task, removed);
        assertEquals(1, taskList.size());
        assertEquals(task1, taskList.get(0));
    }

    @Test
    public void markTask_validIndex_correctTaskMarked() throws InvalidTaskNumberException {
        TaskList taskList = new TaskList();
        Task task = new Todo("test");
        taskList.add(task);

        taskList.mark(0);

        assertTrue(task.isDone());
    }

    @Test
    public void unmarkTask_validIndex_correctTaskUnmarked() throws InvalidTaskNumberException {
        TaskList taskList = new TaskList();
        Task task = new Todo("test");
        task.mark();
        taskList.add(task);

        taskList.unmark(0);

        assertFalse(task.isDone());
    }

    @Test
    public void markTask_invalidIndex_throwsInvalidTaskNumberException() {
        TaskList taskList = new TaskList();

        assertThrows(InvalidTaskNumberException.class, () -> taskList.mark(0));
    }

    @Test
    public void unmarkTask_invalidIndex_throwsInvalidTaskNumberException() {
        TaskList taskList = new TaskList();

        assertThrows(InvalidTaskNumberException.class, () -> taskList.unmark(0));
    }

    @Test
    public void delete_invalidIndex_throwsInvalidTaskNumberException() {
        TaskList taskList = new TaskList();

        assertThrows(InvalidTaskNumberException.class, () -> taskList.delete(0));
    }
}

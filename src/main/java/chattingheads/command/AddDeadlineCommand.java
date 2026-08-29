package chattingheads.command;

import java.time.LocalDateTime;

import chattingheads.task.Deadline;
import chattingheads.task.Task;
import chattingheads.task.TaskList;
import chattingheads.ui.Ui;

/**
 * Represents a command that adds a deadline task.
 */
public class AddDeadlineCommand extends Command {

    private final String description;
    private final LocalDateTime by;

    /**
     * Creates a command for adding a deadline task.
     *
     * @param description Description of the deadline task.
     * @param by Date and time by which the task should be completed.
     */
    public AddDeadlineCommand(String description, LocalDateTime by) {
        this.description = description;
        this.by = by;
    }

    @Override
    public String execute(TaskList taskList, Ui ui) {
        Task newTask = new Deadline(description, by);
        taskList.add(newTask);
        return ui.getAddStatus(newTask, taskList);
    }

    @Override
    public boolean shouldSave() {
        return true;
    }
}

package chattingheads.command;

import chattingheads.exception.InvalidTaskNumberException;
import chattingheads.task.TaskList;
import chattingheads.ui.Ui;

/**
 * Represents a command that marks a task as incomplete.
 */
public class UnmarkCommand extends Command {

    private final int taskNumber;

    public UnmarkCommand(int taskNumber) {
        this.taskNumber = taskNumber;
    }

    @Override
    public String execute(TaskList taskList, Ui ui) throws InvalidTaskNumberException {
        taskList.unmark(taskNumber - 1);
        return ui.getUnmarkStatus(taskList.get(taskNumber - 1));
    }

    public int getTaskNumber() {
        return taskNumber;
    }

    @Override
    public boolean shouldSave() {
        return true;
    }
}

package chattingheads.command;

import chattingheads.exception.InvalidTaskNumberException;
import chattingheads.task.Task;
import chattingheads.task.TaskList;
import chattingheads.ui.Ui;

/**
 * Represents a command that deletes a task.
 */
public class DeleteCommand extends Command {

    private final int taskNumber;

    public DeleteCommand(int taskNumber) {
        this.taskNumber = taskNumber;
    }

    @Override
    public boolean execute(TaskList taskList, Ui ui) throws InvalidTaskNumberException {
        Task deletedTask = taskList.delete(taskNumber - 1);
        ui.printDeleteStatus(deletedTask, taskList);
        return true;
    }

    public int getTaskNumber() {
        return taskNumber;
    }
}

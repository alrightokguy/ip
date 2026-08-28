package chattingHeads.command;

import chattingHeads.exception.InvalidTaskNumberException;
import chattingHeads.task.TaskList;
import chattingHeads.ui.Ui;

/**
 * Represents a command that marks a task as complete.
 */
public class MarkCommand extends Command {

    private final int taskNumber;

    public MarkCommand(int taskNumber) {
        this.taskNumber = taskNumber;
    }

    @Override
    public boolean execute(TaskList taskList, Ui ui) throws InvalidTaskNumberException {
        taskList.mark(taskNumber - 1);
        ui.printMarkStatus(taskList.get(taskNumber - 1));
        return true;
    }

    public int getTaskNumber() {
        return taskNumber;
    }
}

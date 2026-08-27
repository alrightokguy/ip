package chattingHeads.command;

import chattingHeads.exception.InvalidTaskNumberException;
import chattingHeads.task.Task;
import chattingHeads.task.TaskList;
import chattingHeads.ui.Ui;

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

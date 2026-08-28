package chattingheads.command;

import chattingheads.exception.InvalidTaskNumberException;
import chattingheads.task.Task;
import chattingheads.task.TaskList;
import chattingheads.ui.Ui;

public class DeleteCommand extends Command {

    private final int taskNumber;

    public DeleteCommand(int taskNumber) {
        this.taskNumber = taskNumber;
    }

    @Override
    public void execute(TaskList taskList, Ui ui) throws InvalidTaskNumberException {
        Task deletedTask = taskList.delete(taskNumber - 1);
        ui.printDeleteStatus(deletedTask, taskList);
    }

    public int getTaskNumber() {
        return taskNumber;
    }

    @Override
    public boolean shouldSave() {
        return true;
    }
}

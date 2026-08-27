package chattingHeads.command;

import chattingHeads.exception.InvalidTaskNumberException;
import chattingHeads.task.TaskList;
import chattingHeads.ui.Ui;

public class UnmarkCommand extends Command {

    private final int taskNumber;

    public UnmarkCommand(int taskNumber) {
        this.taskNumber = taskNumber;
    }

    @Override
    public boolean execute(TaskList taskList, Ui ui) throws InvalidTaskNumberException {
        taskList.unmark(taskNumber - 1);
        ui.printUnmarkStatus(taskList.get(taskNumber - 1));
        return true;
    }

    public int getTaskNumber() {
        return taskNumber;
    }
}

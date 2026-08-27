package chattingHeads.command;

import chattingHeads.exception.InvalidTaskNumberException;
import chattingHeads.task.TaskList;
import chattingHeads.ui.Ui;

public abstract class Command {

    public abstract boolean execute(TaskList taskList, Ui ui) throws InvalidTaskNumberException;

    public boolean isExit() {
        return false;
    }
}

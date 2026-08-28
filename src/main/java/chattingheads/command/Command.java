package chattingheads.command;

import chattingheads.exception.InvalidTaskNumberException;
import chattingheads.task.TaskList;
import chattingheads.ui.Ui;

public abstract class Command {

    public abstract boolean execute(TaskList taskList, Ui ui) throws InvalidTaskNumberException;

    public boolean isExit() {
        return false;
    }
}

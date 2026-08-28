package chattingheads.command;

import chattingheads.exception.InvalidTaskNumberException;
import chattingheads.task.TaskList;
import chattingheads.ui.Ui;

public abstract class Command {

    public abstract void execute(TaskList taskList, Ui ui) throws InvalidTaskNumberException;

    public boolean isExit() {
        return false;
    }

    public boolean shouldSave() {
        return false;
    }
}

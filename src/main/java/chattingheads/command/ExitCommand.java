package chattingheads.command;

import chattingheads.task.TaskList;
import chattingheads.ui.Ui;

/**
 * Represents a command that exits the program.
 */
public class ExitCommand extends Command {

    @Override
    public String execute(TaskList taskList, Ui ui) {
        return ui.getShutdownMessage();
    }

    @Override
    public boolean isExit() {
        return true;
    }
}

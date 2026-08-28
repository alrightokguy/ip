package chattingheads.command;

import chattingheads.task.TaskList;
import chattingheads.ui.Ui;

/**
 * Represents a command that exits the program.
 */
public class ExitCommand extends Command {

    @Override
    public void execute(TaskList taskList, Ui ui) {
        ui.printShutdownMessage();
    }

    @Override
    public boolean isExit() {
        return true;
    }
}

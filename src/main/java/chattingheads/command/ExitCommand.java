package chattingheads.command;

import chattingheads.task.TaskList;
import chattingheads.ui.Ui;

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

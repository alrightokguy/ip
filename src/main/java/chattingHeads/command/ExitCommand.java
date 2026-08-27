package chattingHeads.command;

import chattingHeads.task.TaskList;
import chattingHeads.ui.Ui;

public class ExitCommand extends Command {

    @Override
    public boolean execute(TaskList taskList, Ui ui) {
        ui.printShutdownMessage();
        return false;
    }

    @Override
    public boolean isExit() {
        return true;
    }
}

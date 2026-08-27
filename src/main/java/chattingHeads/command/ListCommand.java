package chattingHeads.command;

import chattingHeads.task.TaskList;
import chattingHeads.ui.Ui;

public class ListCommand extends Command {

    @Override
    public boolean execute(TaskList taskList, Ui ui) {
        ui.printTasks(taskList);
        return false;
    }
}

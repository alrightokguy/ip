package chattingheads.command;

import chattingheads.task.TaskList;
import chattingheads.ui.Ui;

public class ListCommand extends Command {

    @Override
    public boolean execute(TaskList taskList, Ui ui) {
        ui.printTasks(taskList);
        return false;
    }
}

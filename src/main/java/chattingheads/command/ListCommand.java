package chattingheads.command;

import chattingheads.task.TaskList;
import chattingheads.ui.Ui;

/**
 * Represents a command that lists all tasks in the task list.
 */
public class ListCommand extends Command {

    @Override
    public boolean execute(TaskList taskList, Ui ui) {
        ui.printTasks(taskList);
        return false;
    }
}

package chattingheads.command;

import chattingheads.task.TaskList;
import chattingheads.ui.Ui;

/**
 * Represents a command that lists all tasks that contains a specified keyword in the task list.
 */
public class FindCommand extends Command {

    private final String keyword;

    public FindCommand(String keyword) {
        this.keyword = keyword;
    }

    @Override
    public void execute(TaskList tasklist, Ui ui) {
        ui.printFoundTasks(tasklist, keyword);
    }
}

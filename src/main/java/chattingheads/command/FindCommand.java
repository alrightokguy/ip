package chattingheads.command;

import chattingheads.task.TaskList;
import chattingheads.ui.Ui;

public class FindCommand extends Command {

    private final String keyword;

    public FindCommand(String keyword) {
        this.keyword = keyword;
    }

    @Override
    public void execute(TaskList tasklist, Ui ui) {
        ui.printFoundTasks(tasklist, keyword);
    }

    @Override
    public boolean shouldSave() {
        return false;
    }
}

package chattingHeads.command;

import java.time.LocalDateTime;

public class AddDeadlineCommand extends Command {

    private final String description;
    private final LocalDateTime by;

    public AddDeadlineCommand(String description, LocalDateTime by) {
        this.description = description;
        this.by = by;
    }

    @Override
    public boolean execute(TaskList taskList, Ui ui) {
        Task newTask = new Deadline(description, by);
        taskList.add(newTask);
        ui.printAddStatus(newTask, taskList);
        return true;
    }
}

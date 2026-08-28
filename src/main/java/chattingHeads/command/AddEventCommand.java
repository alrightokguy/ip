package chattingHeads.command;

import chattingHeads.task.Event;
import chattingHeads.task.Task;
import chattingHeads.task.TaskList;
import chattingHeads.ui.Ui;

import java.time.LocalDateTime;

/**
 * Represents a command that adds an event task.
 */
public class AddEventCommand extends Command {

    private final String description;
    private final LocalDateTime from;
    private final LocalDateTime to;

    public AddEventCommand(String description, LocalDateTime from, LocalDateTime to) {
        this.description = description;
        this.from = from;
        this.to = to;
    }

    @Override
    public boolean execute(TaskList taskList, Ui ui) {
        Task newTask = new Event(description, from, to);
        taskList.add(newTask);
        ui.printAddStatus(newTask, taskList);
        return true;
    }
}

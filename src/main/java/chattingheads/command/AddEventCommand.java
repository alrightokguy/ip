package chattingheads.command;

import java.time.LocalDateTime;

import chattingheads.task.Event;
import chattingheads.task.Task;
import chattingheads.task.TaskList;
import chattingheads.ui.Ui;

/**
 * Represents a command that adds an event task.
 */
public class AddEventCommand extends Command {

    private final String description;
    private final LocalDateTime from;
    private final LocalDateTime to;

    /**
     * Creates a command for adding an event task.
     *
     * @param description Description of the event task.
     * @param from Start date and time of the event.
     * @param to End date and time of the event.
     */
    public AddEventCommand(String description, LocalDateTime from, LocalDateTime to) {
        this.description = description;
        this.from = from;
        this.to = to;
    }

    @Override
    public void execute(TaskList taskList, Ui ui) {
        Task newTask = new Event(description, from, to);
        taskList.add(newTask);
        ui.printAddStatus(newTask, taskList);
    }

    @Override
    public boolean shouldSave() {
        return true;
    }
}

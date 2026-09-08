package chattingheads.command;

import chattingheads.exception.InvalidTaskNumberException;
import chattingheads.task.TaskList;
import chattingheads.ui.Ui;

public class RescheduleEventCommand extends Command {

    private final int taskNumber;

    /**
     * Creates a command to reschedule the specified event.
     *
     * @param taskNumber Task number to mark.
     */
    public RescheduleEventCommand(int taskNumber) {
        this.taskNumber = taskNumber;
    }

    @Override
    public String execute(TaskList taskList, Ui ui) throws InvalidTaskNumberException {
        assert taskList != null : "TaskList should not be null";
        assert ui != null : "Ui should not be null";

        taskList.mark(taskNumber - 1);
        return ui.getRescheduleStatus(taskList.get(taskNumber - 1));
    }

    public int getTaskNumber() {
        return taskNumber;
    }

    @Override
    public boolean shouldSave() {
        return true;
    }
}

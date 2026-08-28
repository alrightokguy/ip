package chattingheads.command;

import chattingheads.exception.InvalidTaskNumberException;
import chattingheads.task.TaskList;
import chattingheads.ui.Ui;

/**
 * Represents a command that can be executed on the task list.
 */
public abstract class Command {

    /**
     * Executes this command.
     *
     * @param taskList Task list on which the command operates.
     * @param ui User interface used to display command results.
     * @return {@code true} if executing the command changes the task list.
     * @throws InvalidTaskNumberException If the command refers to an invalid task number.
     */
    public abstract boolean execute(TaskList taskList, Ui ui) throws InvalidTaskNumberException;

    /**
     * Returns whether this command exits the application.
     *
     * @return {@code true} if this command exits the application.
     */
    public boolean isExit() {
        return false;
    }
}

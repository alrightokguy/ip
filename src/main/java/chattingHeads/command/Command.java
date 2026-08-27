package chattingHeads.command;

public abstract class Command {

    public abstract boolean execute(TaskList taskList, Ui ui) throws InvalidTaskNumberException;

    public boolean isExit() {
        return false;
    }
}

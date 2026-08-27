public abstract class Command {

    public abstract boolean execute(TaskList taskList, Ui ui);

    public boolean isExit() {
        return false;
    }
}

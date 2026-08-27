public class UnmarkCommand extends Command {

    private final int taskNumber;

    public UnmarkCommand(int taskNumber) {
        this.taskNumber = taskNumber;
    }

    @Override
    public void execute(TaskList taskList, Ui ui) {
        taskList.unmark(taskNumber - 1);
        ui.printUnmarkStatus(taskList.get(taskNumber - 1));
    }
}

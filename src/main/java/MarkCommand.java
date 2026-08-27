public class MarkCommand extends Command {

    private final int taskNumber;

    public MarkCommand(int taskNumber) {
        this.taskNumber = taskNumber;
    }

    @Override
    public void execute(TaskList taskList, Ui ui) {
        taskList.mark(taskNumber - 1);
        ui.printMarkStatus(taskList.get(taskNumber - 1));
    }
}

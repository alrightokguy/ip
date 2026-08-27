public class DeleteCommand extends Command {

    private final int taskNumber;

    public DeleteCommand(int taskNumber) {
        this.taskNumber = taskNumber;
    }

    @Override
    public boolean execute(TaskList taskList, Ui ui) {
        Task deletedTask = taskList.delete(taskNumber - 1);
        ui.printDeleteStatus(deletedTask, taskList);
        return true;
    }
}

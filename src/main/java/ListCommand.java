public class ListCommand extends Command {

    @Override
    public boolean execute(TaskList taskList, Ui ui) {
        ui.printTasks(taskList);
        return false;
    }
}

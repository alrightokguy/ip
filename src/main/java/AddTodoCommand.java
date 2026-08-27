public class AddTodoCommand extends Command {

    private final String description;

    public AddTodoCommand(String description) {
        this.description = description;
    }

    @Override
    public void execute(TaskList taskList, Ui ui) {
        Task newTask = new Todo(description);
        taskList.add(newTask);
        ui.printAddStatus(newTask, taskList.getTasks());
    }
}

package chattingheads;

import chattingheads.command.Command;
import chattingheads.parser.Parser;
import chattingheads.storage.Storage;
import chattingheads.task.TaskList;
import chattingheads.ui.Ui;

/**
 * Represents the main application and coordinates its components.
 */
public class ChattingHeads {

    private final Storage storage;
    private final TaskList taskList;
    private final Parser parser;
    private final Ui ui;

    /**
     * Creates the application and initialises its components.
     */
    public ChattingHeads() {
        storage = new Storage("tasks.txt");
        taskList = new TaskList(storage);
        parser = new Parser();
        ui = new Ui();
    }

    /**
     * Runs the main command loop until the user exits the application.
     */
    private void run() {
        System.out.println(ui.getStartupMessage());

        while (true) {
            try {
                String input = ui.readCommand();
                Command command = parser.parse(input);

                String response = command.execute(taskList, ui);
                System.out.println(response);

                if (command.isExit()) {
                    break;
                }
                if (command.shouldSave()) {
                    storage.save(taskList);
                }
            } catch (Exception e) {
                System.out.println(ui.getErrorMessage(e));
            }
        }
    }

    public CommandResult getResponse(String input) {
        try {
            Command command = parser.parse(input);

            String response = command.execute(taskList, ui);

            if (command.shouldSave()) {
                storage.save(taskList);
            }
            return new CommandResult(response, command.isExit());
        } catch (Exception e) {
            return new CommandResult(ui.getErrorMessage(e), false);
        }
    }

    public String getStartupMessage() {
        return ui.getStartupMessage();
    }

    /**
     * Starts the application.
     *
     * @param ignoredArgs Command-line arguments, which are not used.
     */
    static void main(String[] ignoredArgs) {
        new ChattingHeads().run();
    }
}

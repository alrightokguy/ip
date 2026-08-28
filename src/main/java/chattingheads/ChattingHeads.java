package chattingheads;

import chattingheads.command.Command;
import chattingheads.parser.Parser;
import chattingheads.storage.Storage;
import chattingheads.task.TaskList;
import chattingheads.ui.Ui;

public class ChattingHeads {

    private final Storage storage;
    private final TaskList taskList;
    private final Parser parser;
    private final Ui ui;

    private ChattingHeads() {
        storage = new Storage("tasks.txt");
        taskList = new TaskList(storage);
        parser = new Parser();
        ui = new Ui();
    }

    private void run() {
        ui.printStartupMessage();

        while (true) {
            try {
                String input = ui.readCommand();
                Command command = parser.parse(input);

                boolean hasChanged = command.execute(taskList, ui);

                if (command.isExit()) {
                    break;
                }
                if (hasChanged) {
                    storage.save(taskList);
                }
            } catch (Exception e) {
                ui.printErrorMessage(e);
            }
        }
    }

    static void main(String[] ignoredArgs) {
        new ChattingHeads().run();
    }
}

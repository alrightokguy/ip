package chattingHeads;

import chattingHeads.command.Command;
import chattingHeads.parser.Parser;
import chattingHeads.storage.Storage;
import chattingHeads.task.TaskList;
import chattingHeads.ui.Ui;

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

import java.time.LocalDateTime;
import java.util.ArrayList;

public class ChattingHeads {

    private final Storage storage;
    private final TaskList taskList;
    private final Parser parser;
    private final Ui ui;

    private ChattingHeads(String file) {
        storage = new Storage(file);
        taskList = new TaskList(storage.load());
        parser = new Parser();
        ui = new Ui();
    }

    private enum Command {
        LIST,
        TODO,
        DEADLINE,
        EVENT,
        MARK,
        UNMARK,
        DELETE,
        BYE;

        public static Command from(String input) throws InvalidCommandException {
            try {
                return Command.valueOf(input.toUpperCase());
            } catch (IllegalArgumentException e) {
                throw new InvalidCommandException();
            }
        }
    }

    private void run() {
        Command command;

        ui.printStartupMessage();

        while (true) {
            String input = ui.readCommand();

            try {
                if (input.isEmpty()) {
                    throw new InvalidInputException("command");
                }
                String[] tokens = input.split("\\s+");
                command = Command.from(tokens[0]);

                switch (command) {
                    case LIST -> ui.printTasks(taskList.getTasks());
                    case TODO -> addToDo(taskList.getTasks(), tokens);
                    case DEADLINE -> addDeadline(taskList.getTasks(), tokens);
                    case EVENT -> addEvent(taskList.getTasks(), tokens);
                    case MARK -> setTaskStatus(taskList.getTasks(), tokens, true);
                    case UNMARK -> setTaskStatus(taskList.getTasks(), tokens, false);
                    case DELETE -> deleteTask(taskList.getTasks(), tokens);
                    case BYE -> {
                        ui.printShutdownMessage();
                        return;
                    }
                }
            } catch (Exception e) {
                ui.printErrorMessage(e);
            }
        }
    }

    static void main(String[] ignoredArgs) {
        new ChattingHeads("tasks.txt").run();
    }

    private void addToDo(ArrayList<Task> tasks, String[] tokens) throws InvalidInputException {
        String desc = parser.parseString(tokens, 1, tokens.length);

        if (desc.isEmpty()) {
            throw new InvalidInputException("description");
        }

        ToDo newTask = new ToDo(desc);
        taskList.add(newTask);
        ui.printAddStatus(newTask, tasks);
        storage.save(tasks);
    }

    private void addDeadline(ArrayList<Task> tasks, String[] tokens) throws InvalidInputException {
        int marker = tokens.length;

        for (int i = 1; i < tokens.length; i++) {
            if (tokens[i].equals("/by")) {
                marker = i;
                break;
            }
        }
        ArrayList<String> emptyInputs = new ArrayList<>();
        String desc = parser.parseString(tokens, 1, marker);
        LocalDateTime by = parser.parseDateTime(tokens, marker + 1, tokens.length);

        if (desc.isEmpty()) {
            emptyInputs.add("description");
        }
        if (by == null) {
            emptyInputs.add("deadline");
        }
        if  (!emptyInputs.isEmpty()) {
            throw new InvalidInputException(emptyInputs);
        }

        Deadline newTask = new Deadline(desc, by);
        taskList.add(newTask);
        ui.printAddStatus(newTask, tasks);
        storage.save(tasks);
    }

    private void addEvent(ArrayList<Task> tasks, String[] tokens) throws InvalidInputException {
        int marker1 = tokens.length;
        int marker2 = tokens.length;

        for (int i = 1; i < tokens.length; i++) {
            if (tokens[i].equals("/from")) {
                marker1 = i;
            } else if (tokens[i].equals("/to")) {
                marker2 = i;
                break;
            }
        }
        ArrayList<String> emptyInputs = new ArrayList<>();
        String desc = parser.parseString(tokens, 1, marker1);
        LocalDateTime from = parser.parseDateTime(tokens, marker1 + 1, marker2);
        LocalDateTime to = parser.parseDateTime(tokens, marker2 + 1, tokens.length);

        if (desc.isEmpty()) {
            emptyInputs.add("description");
        }
        if (from == null) {
            emptyInputs.add("start");
        }
        if (to == null) {
            emptyInputs.add("end");
        }
        if  (!emptyInputs.isEmpty()) {
            throw new InvalidInputException(emptyInputs);
        }

        Event newTask = new Event(desc, from, to);
        taskList.add(newTask);
        ui.printAddStatus(newTask, tasks);
        storage.save(tasks);
    }

    private void setTaskStatus(ArrayList<Task> tasks, String[] tokens, boolean mark)
            throws InvalidInputException, InvalidTaskNumberException {
        if (tokens.length < 2) {
            throw new InvalidInputException("task number");
        }

        int taskNum = Integer.parseInt(tokens[1]) - 1;
        if (taskNum < 0 || taskNum >= tasks.size()) {
            throw new InvalidTaskNumberException();
        }
        Task task = tasks.get(taskNum);

        if (mark) {
            task.mark();
            ui.printMarkStatus(task);
        } else {
            task.unmark();
            ui.printUnmarkStatus(task);
        }
        storage.save(tasks);
    }

    private void deleteTask(ArrayList<Task> tasks, String[] tokens)
            throws InvalidInputException, InvalidTaskNumberException {
        if (tokens.length < 2) {
            throw new InvalidInputException("task number");
        }

        int taskNum = Integer.parseInt(tokens[1]) - 1;
        if (taskNum < 0 || taskNum >= tasks.size()) {
            throw new InvalidTaskNumberException();
        }
        Task task = taskList.delete(taskNum);
        ui.printDeleteStatus(task, tasks);
        storage.save(tasks);
    }
}

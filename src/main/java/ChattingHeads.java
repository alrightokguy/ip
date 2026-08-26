import java.time.LocalDateTime;
import java.util.Scanner;
import java.util.ArrayList;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class ChattingHeads {

    private final Storage storage;
    private final TaskList tasks;
    private final Parser parser;
    private final Ui ui;

    private static final Path TASK_FILE = Path.of("tasks.txt");

    private ChattingHeads(String file) {
        storage = new Storage(file);
        tasks = new TaskList(storage.load());
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
        Scanner scan = new Scanner(System.in);
        ArrayList<Task> tasks = storage.load();
        Command command;

        ui.printStartupMessage();

        while (true) {
            String input = scan.nextLine();

            try {
                if (input.isEmpty()) {
                    throw new InvalidInputException("command");
                }
                String[] tokens = input.split("\\s+");
                command = Command.from(tokens[0]);

                switch (command) {
                    case LIST -> ui.printTasks(tasks);
                    case TODO -> addToDo(tasks, tokens);
                    case DEADLINE -> addDeadline(tasks, tokens);
                    case EVENT -> addEvent(tasks, tokens);
                    case MARK -> setTaskStatus(tasks, tokens, true);
                    case UNMARK -> setTaskStatus(tasks, tokens, false);
                    case DELETE -> deleteTask(tasks, tokens);
                    case BYE -> {
                        ui.printShutdownMessage();
                        return;
                    }
                }
            } catch (Exception e) {
                ui.printErrorMessage(e);
            }
            ui.printSeparator();
        }
    }

    static void main(String[] ignoredArgs) {
        new ChattingHeads("tasks.txt").run();
    }

    private void saveTasks(ArrayList<Task> tasks) {
        try {
            Files.write(TASK_FILE, tasks.stream().map(Task::toCsv).toList());
        } catch (IOException e) {
            ui.printErrorMessage(e);
        }
    }

    private void addToDo(ArrayList<Task> tasks, String[] tokens) throws InvalidInputException {
        String desc = parser.parseString(tokens, 1, tokens.length);

        if (desc.isEmpty()) {
            throw new InvalidInputException("description");
        }

        ToDo newTask = new ToDo(desc);
        tasks.add(newTask);
        ui.printAddStatus(newTask, tasks);
        saveTasks(tasks);
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
        tasks.add(newTask);
        ui.printAddStatus(newTask, tasks);
        saveTasks(tasks);
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
        tasks.add(newTask);
        ui.printAddStatus(newTask, tasks);
        saveTasks(tasks);
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
        saveTasks(tasks);
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
        Task task = tasks.remove(taskNum);
        ui.printDeleteStatus(task, tasks);
        saveTasks(tasks);
    }
}

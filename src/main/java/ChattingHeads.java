import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Arrays;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class ChattingHeads {

    private final Storage storage;
    private final TaskList tasks;
    private final Ui ui;

    private static final Path TASK_FILE = Path.of("tasks.txt");
    private static final DateTimeFormatter DATE_TIME_FORMATTER =
            DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    private ChattingHeads(String file) {
        this.storage = new Storage(file);
        this.tasks = new TaskList(storage.load());
        this.ui = new Ui();
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
        ArrayList<Task> tasks = this.storage.load();
        Command command;

        System.out.println("""
                Hello! I'm Chatting Heads.
                You may find yourself
                Living in a shotgun shack
                What can I do for you?""");
        this.ui.printSeparator();

        while (true) {
            String input = scan.nextLine();

            try {
                if (input.isEmpty()) {
                    throw new InvalidInputException("command");
                }
                String[] tokens = input.split("\\s+");
                command = Command.from(tokens[0]);

                switch (command) {
                    case LIST -> listTasks(tasks);
                    case TODO -> addToDo(tasks, tokens);
                    case DEADLINE -> addDeadline(tasks, tokens);
                    case EVENT -> addEvent(tasks, tokens);
                    case MARK -> setTaskStatus(tasks, tokens, true);
                    case UNMARK -> setTaskStatus(tasks, tokens, false);
                    case DELETE -> deleteTask(tasks, tokens);
                    case BYE -> {
                        System.out.println("Letting the days go \"bye!\"\nLet the water shut me down");
                        this.ui.printSeparator();
                        return;
                    }
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            this.ui.printSeparator();
        }
    }

    static void main(String[] ignoredArgs) {
        new ChattingHeads("tasks.txt").run();
    }

    private static void saveTasks(ArrayList<Task> tasks) {
        try {
            Files.write(TASK_FILE, tasks.stream().map(Task::toCsv).toList());
        } catch (IOException e) {
            System.out.println("Error writing tasks to file");
        }
    }

    private static void listTasks(ArrayList<Task> tasks) {
        System.out.println("Take a look at these tasks:");
        for (int i = 0; i < tasks.size(); i++) {
            System.out.printf("%d.%s\n", i + 1, tasks.get(i));
        }
    }

    private void addToDo(ArrayList<Task> tasks, String[] tokens) throws InvalidInputException {
        String desc = parseString(tokens, 1, tokens.length);

        if (desc.isEmpty()) {
            throw new InvalidInputException("description");
        }

        ToDo newTask = new ToDo(desc);
        tasks.add(newTask);
        this.ui.printAddStatus(newTask, tasks);
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
        String desc = parseString(tokens, 1, marker);
        LocalDateTime by = parseDateTime(tokens, marker + 1, tokens.length);

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
        this.ui.printAddStatus(newTask, tasks);
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
        String desc = parseString(tokens, 1, marker1);
        LocalDateTime from = parseDateTime(tokens, marker1 + 1, marker2);
        LocalDateTime to = parseDateTime(tokens, marker2 + 1, tokens.length);

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
        this.ui.printAddStatus(newTask, tasks);
        saveTasks(tasks);
    }

    private static void setTaskStatus(ArrayList<Task> tasks, String[] tokens, boolean mark)
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
            System.out.println("Nice! I've marked this task as done:\n" + task);
        } else {
            task.unmark();
            System.out.println("OK, I've marked this task as not done yet:\n" + task);
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
        this.ui.printDeleteStatus(task, tasks);
        saveTasks(tasks);
    }

    private static String parseString(String[] tokens, int start, int end) {
        if (start >= 0 && end <= tokens.length && start <= end) {
            return String.join(" ", Arrays.copyOfRange(tokens, start, end));
        }
        return "";
    }

    private static LocalDateTime parseDateTime(String[] tokens, int start, int end) {
        try {
            return LocalDateTime.parse(parseString(tokens, start, end), DATE_TIME_FORMATTER);
        } catch (DateTimeParseException e) {
            return null;
        }
    }
}
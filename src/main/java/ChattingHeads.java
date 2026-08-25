import java.util.List;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Arrays;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class ChattingHeads {

    private final static Path TASK_FILE = Path.of("tasks.txt");

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

    static void main(String[] ignoredArgs) {
        Scanner scan = new Scanner(System.in);
        ArrayList<Task> tasks = loadTasks();
        Command command;

        System.out.println("""
                Hello! I'm Chatting Heads.
                You may find yourself
                Living in a shotgun shack
                What can I do for you?""");
        printSeparator();

        while (true) {
            String input = scan.nextLine();

            try {
                if (input.isEmpty()) {
                    throw new EmptyInputException("command");
                }
                String[] parsed = input.split("\\s+");
                command = Command.from(parsed[0]);

                switch (command) {
                    case LIST -> listTasks(tasks);
                    case TODO -> addToDo(tasks, parsed);
                    case DEADLINE -> addDeadline(tasks, parsed);
                    case EVENT -> addEvent(tasks, parsed);
                    case MARK -> setTaskStatus(tasks, parsed, true);
                    case UNMARK -> setTaskStatus(tasks, parsed, false);
                    case DELETE -> deleteTask(tasks, parsed);
                    case BYE -> {
                        System.out.println("Letting the days go \"bye!\"\nLet the water shut me down");
                        printSeparator();
                        return;
                    }
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            printSeparator();
        }
    }

    private static ArrayList<Task> loadTasks() {
        ArrayList<Task> tasks = new ArrayList<>();

        try {
            List<String> lines = Files.readAllLines(TASK_FILE);

            for (String line : lines) {
                String[] fields =  line.split(",");
                String type = fields[0];
                Task newTask = switch (type) {
                    case "T" -> new ToDo(fields[1], Boolean.parseBoolean(fields[2]));
                    case "D" -> new Deadline(fields[1], Boolean.parseBoolean(fields[2]), fields[3]);
                    case "E" -> new Event(fields[1], Boolean.parseBoolean(fields[2]), fields[3], fields[4]);
                    default -> throw new IOException();
                };
                tasks.add(newTask);
            }
        } catch (IOException ignored) {
        }
        return tasks;
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

    private static void addToDo(ArrayList<Task> tasks, String[] parsed) throws EmptyInputException {
        String desc = collectString(parsed, 1, parsed.length);

        if (desc.isEmpty()) {
            throw new EmptyInputException("description");
        }

        ToDo newTask = new ToDo(desc);
        tasks.add(newTask);
        printAddStatus(newTask, tasks);
        saveTasks(tasks);
    }

    private static void addDeadline(ArrayList<Task> tasks, String[] parsed) throws EmptyInputException {
        int marker = parsed.length;

        for (int i = 1; i < parsed.length; i++) {
            if (parsed[i].equals("/by")) {
                marker = i;
                break;
            }
        }
        ArrayList<String> emptyInputs = new ArrayList<>();
        String desc = collectString(parsed, 1, marker);
        String by = collectString(parsed, marker + 1, parsed.length);

        if (desc.isEmpty()) {
            emptyInputs.add("description");
        }
        if (by.isEmpty()) {
            emptyInputs.add("deadline");
        }
        if  (!emptyInputs.isEmpty()) {
            throw new EmptyInputException(emptyInputs);
        }

        Deadline newTask = new Deadline(desc, by);
        tasks.add(newTask);
        printAddStatus(newTask, tasks);
        saveTasks(tasks);
    }

    private static void addEvent(ArrayList<Task> tasks, String[] parsed) throws EmptyInputException {
        int marker1 = parsed.length;
        int marker2 = parsed.length;
        boolean fromFirst = true;

        for (int i = 1; i < parsed.length; i++) {
            if (parsed[i].equals("/from") || parsed[i].equals("/to")) {
                if (marker1 == parsed.length) {
                    marker1 = i;
                    fromFirst = parsed[i].equals("/from");
                } else {
                    marker2 = i;
                    break;
                }
            }
        }
        ArrayList<String> emptyInputs = new ArrayList<>();
        String desc = collectString(parsed, 1, marker1);
        String from = fromFirst
                ? collectString(parsed, marker1 + 1, marker2)
                : collectString(parsed, marker2 + 1, parsed.length);
        String to = fromFirst
                ? collectString(parsed, marker2 + 1, parsed.length)
                : collectString(parsed, marker1 + 1, marker2);

        if (desc.isEmpty()) {
            emptyInputs.add("description");
        }
        if (from.isEmpty()) {
            emptyInputs.add("start");
        }
        if (to.isEmpty()) {
            emptyInputs.add("end");
        }
        if  (!emptyInputs.isEmpty()) {
            throw new EmptyInputException(emptyInputs);
        }

        Event newTask = new Event(desc, from, to);
        tasks.add(newTask);
        printAddStatus(newTask, tasks);
        saveTasks(tasks);
    }

    private static void setTaskStatus(ArrayList<Task> tasks, String[] parsed, boolean mark)
            throws EmptyInputException, InvalidTaskNumberException {
        if (parsed.length < 2) {
            throw new EmptyInputException("task number");
        }

        int taskNum = Integer.parseInt(parsed[1]) - 1;
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

    private static void deleteTask(ArrayList<Task> tasks, String[] parsed)
            throws EmptyInputException, InvalidTaskNumberException {
        if (parsed.length < 2) {
            throw new EmptyInputException("task number");
        }

        int taskNum = Integer.parseInt(parsed[1]) - 1;
        if (taskNum < 0 || taskNum >= tasks.size()) {
            throw new InvalidTaskNumberException();
        }
        Task task = tasks.remove(taskNum);
        printDeleteStatus(task, tasks);
        saveTasks(tasks);
    }

    private static void printSeparator() {
        System.out.println("------------------------------------------------------------");
    }

    private static void printListStatus(ArrayList<Task> tasks) {
        if (tasks.size() == 1) {
            System.out.println("Now you have 1 task in the list.");
        } else {
            System.out.printf("Now you have %s tasks in the list.\n", tasks.isEmpty() ? "no" : tasks.size());
        }
    }

    private static void printAddStatus(Task task, ArrayList<Task> tasks) {
        System.out.println("Got it. I've added this task:\n" + task);
        printListStatus(tasks);
    }

    private static void printDeleteStatus(Task task, ArrayList<Task> tasks) {
        System.out.println("Into the blue again\nAfter this task is gone:\n" + task);
        printListStatus(tasks);
    }

    private static String collectString(String[] parsed, int start, int end) {
        if (start >= 0 && end <= parsed.length && start <= end) {
            return String.join(" ", Arrays.copyOfRange(parsed, start, end));
        }
        return "";
    }
}
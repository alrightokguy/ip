import java.util.Arrays;
import java.util.Scanner;
import java.util.ArrayList;

public class ChattingHeads {

     static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        ArrayList<Task> tasks = new ArrayList<>();
        String command;

        System.out.println(
                """
                        Hello! I'm Chatting Heads.
                        You may find yourself
                        Living in a shotgun shack
                        What can I do for you?"""
        );
        lineBreak();
        String input = scan.nextLine();

        while (!input.equals("bye")) {
            String[] parsed = input.split("\\s+");

            if (parsed.length > 0) {
                command = parsed[0];
            } else {
                command = "";
            }

            try {
                switch (command) {
                    case "list" -> listTasks(tasks);
                    case "todo" -> addToDo(tasks, parsed);
                    case "deadline" -> addDeadline(tasks, parsed);
                    case "event" -> addEvent(tasks, parsed);
                    case "mark" -> setTaskStatus(tasks, parsed, true);
                    case "unmark" -> setTaskStatus(tasks, parsed, false);
                    default -> throw new InvalidCommandException();
                }
            } catch (InvalidCommandException e) {
                System.out.println(e.getMessage());
            }
            lineBreak();
            input = scan.nextLine();
        }
        System.out.println("Letting the days go bye\nLet the water hold me down");
    }

    private static void listTasks(ArrayList<Task> tasks) {
        System.out.println("Here are the tasks in your list:");
        for (int i = 0; i < tasks.size(); i++) {
            System.out.printf("%d.%s\n", i + 1, tasks.get(i));
        }
    }

    private static void addToDo(ArrayList<Task> tasks, String[] parsed) {
        ToDo newTask = new ToDo(collectString(parsed, 1, parsed.length));
        tasks.add(newTask);
        addStatus(newTask, tasks);
    }

    private static void addDeadline(ArrayList<Task> tasks, String[] parsed) {
        int marker = parsed.length;

        for (int i = 1; i < parsed.length; i++) {
            if (parsed[i].equals("/by")) {
                marker = i;
                break;
            }
        }
        Deadline newTask = new Deadline(
                collectString(parsed, 1, marker),
                collectString(parsed, marker+1, parsed.length)
        );
        tasks.add(newTask);
        addStatus(newTask, tasks);
    }

    private static void addEvent(ArrayList<Task> tasks, String[] parsed) {
        Event newTask;
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
        if (fromFirst) {
            newTask = new Event(
                    collectString(parsed, 1, marker1),
                    collectString(parsed, marker1+1, marker2),
                    collectString(parsed, marker2+1, parsed.length)
            );
        } else {
            newTask = new Event(
                    collectString(parsed, 1, marker1),
                    collectString(parsed, marker2+1, parsed.length),
                    collectString(parsed, marker1+1, marker2)
            );
        }
        tasks.add(newTask);
        addStatus(newTask, tasks);
    }

    private static void setTaskStatus(ArrayList<Task> tasks, String[] parsed, boolean mark) {
        if (parsed.length > 1) {
            int taskNum = Integer.parseInt(parsed[1]) - 1;

            if (0 <= taskNum && taskNum < tasks.size()) {
                Task task = tasks.get(taskNum);
                if (mark) {
                    task.mark();
                    System.out.println("Nice! I've marked this task as done:\n" + task);
                } else {
                    task.unmark();
                    System.out.println("OK, I've marked this task as not done yet:\n" + task);
                }
            }
        }
    }

    private static void lineBreak() {
        System.out.println("------------------------------------------------------------");
    }

    private static void listStatus(ArrayList<Task> list) {
        System.out.printf("Now you have %d tasks in the list.\n", list.size());
    }

    private static void addStatus(Task task, ArrayList<Task> list) {
        System.out.println("Got it. I've added this task:\n" + task);
        listStatus(list);
    }

    private static String collectString(String[] parsed, int start, int end) {
        if (start >= 0 && end <= parsed.length && start <= end) {
            return String.join(" ", Arrays.copyOfRange(parsed, start, end));
        }
        return "";
    }
}
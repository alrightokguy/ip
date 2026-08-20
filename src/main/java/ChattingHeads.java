import java.util.Arrays;
import java.util.Scanner;
import java.util.ArrayList;

public class ChattingHeads {

    public static void lineBreak() {
        System.out.println("------------------------------------------------------------");
    }

    public static void listStatus(ArrayList<Task> list) {
        System.out.printf("Now you have %d tasks in the list.\n", list.size());
    }

    public static void addStatus(Task task, ArrayList<Task> list) {
        System.out.println("Got it. I've added this task:\n" + task);
        listStatus(list);
    }

    public static String collectString(String[] parsed, int start, int end) {
        if (start >= 0 && end <= parsed.length && start <= end) {
            return String.join(" ", Arrays.copyOfRange(parsed, start, end));
        }
        return "";
    }

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        ArrayList<Task> tasks = new ArrayList<>();
        String opt = "";
        String desc = "";
        Task newTask;

        System.out.println(
            "Hello! I'm Chatting Heads.\n" +
            "You may find yourself\n" +
            "Living in a shotgun shack\n" +
            "What can I do for you?"
        );
        lineBreak();
        String input = scan.nextLine();

        while (!input.equals("bye")) {
            String[] parsed = input.split("\\s+");

            if (parsed.length > 0) {
                opt = parsed[0];
            }

            switch (opt) {
                case "list" -> {
                    System.out.println("Here are the tasks in your list:");
                    for (int i = 0; i < tasks.size(); i++) {
                        System.out.printf("%d.%s\n", i + 1, tasks.get(i));
                    }
                }
                case "todo" -> {
                    newTask = new ToDo(collectString(parsed, 1, parsed.length));
                    tasks.add(newTask);
                    addStatus(newTask, tasks);
                }
                case "deadline" -> {
                    int marker = parsed.length;

                    for (int i = 1; i < parsed.length; i++) {
                        if (parsed[i].equals("/by")) {
                            marker = i;
                            break;
                        }
                    }
                    newTask = new Deadline(
                            collectString(parsed, 1, marker),
                            collectString(parsed, marker+1, parsed.length)
                    );
                    tasks.add(newTask);
                    addStatus(newTask, tasks);
                }
                case "event" -> {
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
                case "mark", "unmark" -> {
                    if (parsed.length > 1) {
                        int taskNum = Integer.parseInt(parsed[1]) - 1;

                        if (0 <= taskNum && taskNum < tasks.size()) {
                            Task task = tasks.get(taskNum);
                            if (opt.equals("mark")) {
                                task.mark();
                                System.out.println("Nice! I've marked this task as done:\n" + task);
                            } else {
                                task.unmark();
                                System.out.println("OK, I've marked this task as not done yet:\n" + task);
                            }
                        }
                    }
                }
                default -> {
                    System.out.println(
                        "And you may ask yourself\n" +
                        "\"How do I work this?\""
                    );
                }
            }
            lineBreak();
            opt = null;
            input = scan.nextLine();
        }
        System.out.println(
            "Letting the days go bye\n" +
            "Let the water hold me down"
        );
    }
}
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
                case "list":
                    System.out.println("Here are the tasks in your list:");
                    for (int i = 0; i < tasks.size(); i++) {
                        System.out.printf("%d.%s\n", i + 1, tasks.get(i));
                    }
                    break;
                case "todo":
                    desc = "";

                    newTask = new ToDo(String.join(" ", Arrays.copyOfRange(parsed, 1, parsed.length)));
                    tasks.add(newTask);
                    addStatus(newTask, tasks);
                    break;
                case "deadline":
                    String by;
                    int descMarker = 0;

                    for (String word : parsed) {
                        if (word.equals("/by")) {
                            desc = String.join(" ", Arrays.copyOfRange(parsed, 1, descMarker));
                            break;
                        } else {
                            descMarker++;
                        }
                    }
                    newTask = new Deadline(
                            desc,
                            String.join(" ", Arrays.copyOfRange(parsed, descMarker+1, parsed.length))
                    );
                    tasks.add(newTask);
                    addStatus(newTask, tasks);
                    break;
                case "event":
                    desc = "";

                    newTask = new Event(parsed[1], parsed[2], parsed[3]);
                    tasks.add(newTask);
                    addStatus(newTask, tasks);
                    break;
                case "mark":
                    if (parsed.length > 1) {
                        int taskNum = Integer.parseInt(parsed[1]) - 1;

                        if (0 <= taskNum && taskNum < tasks.size()) {
                            Task task = tasks.get(taskNum);
                            task.mark();
                            System.out.println("Nice! I've marked this task as done:\n" + task);
                        }
                    }
                    break;
                case "unmark":
                    if (parsed.length > 1) {
                        int taskNum = Integer.parseInt(parsed[1]) - 1;

                        if (0 <= taskNum && taskNum < tasks.size()) {
                            Task task = tasks.get(taskNum);
                            task.unmark();
                            System.out.println("OK, I've marked this task as not done yet:\n" + task);
                        }
                    }
                    break;
                default:
                    System.out.println(
                        "And you may ask yourself\n" +
                        "\"How do I work this?\""
                    );
                    break;
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
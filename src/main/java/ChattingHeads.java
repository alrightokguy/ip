import java.util.Scanner;
import java.util.ArrayList;

public class ChattingHeads {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        ArrayList<Task> tasks = new ArrayList<>();

        System.out.println(
            "Hello! I'm Chatting Heads.\n" +
            "You may find yourself\n" +
            "Living in a shotgun shack\n" +
            "What can I do for you?"
        );
        lineBreak();
        String input = scan.nextLine();

        while (!input.equals("bye")) {
            if (input.equals("list")) {
                System.out.println("Here are the tasks in your list:");
                for (int i = 0; i < tasks.size(); i++) {
                    System.out.printf("%d.%s\n", i+1, tasks.get(i));
                }
            } else if (input.length() > 5 && input.substring(0, 5).equals("mark ")) {
                int taskNum = Integer.parseInt(input.substring(5)) - 1;

                if (0 <= taskNum && taskNum < tasks.size()) {
                    Task task = tasks.get(taskNum);
                    task.mark();
                    System.out.println(
                        "Nice! I've marked this task as done:\n" +
                        task
                    );
                }
            } else if (input.length() > 7 && input.substring(0, 7).equals("unmark ")) {
                int taskNum = Integer.parseInt(input.substring(7)) - 1;

                if (0 <= taskNum && taskNum < tasks.size()) {
                    Task task = tasks.get(taskNum);
                    task.unmark();
                    System.out.println(
                        "OK, I've marked this task as not done yet:\n" +
                        task
                    );
                }
            } else {
                tasks.add(new Task(input));
                System.out.println("added: " + input);
            }
            lineBreak();
            input = scan.nextLine();
        }
        System.out.println("Letting the days go bye");
    }

    public static void lineBreak() {
        System.out.println("------------------------------------------------------------");
    }
}
import java.util.Scanner;

public class Ui {

    private final Scanner scanner = new Scanner(System.in);

    public String readCommand() {
        return scanner.nextLine();
    }

    public void printStartupMessage() {
        System.out.println("""
                Hello! I'm Chatting Heads.
                You may find yourself
                Living in a shotgun shack
                What can I do for you?""");
        printSeparator();
    }

    public void printShutdownMessage() {
        System.out.println("Letting the days go \"bye!\"\nLet the water shut me down");
        printSeparator();
    }

    public void printSeparator() {
        System.out.println("------------------------------------------------------------");
    }

    public void printListStatus(TaskList taskList) {
        if (taskList.size() == 1) {
            System.out.println("Now you have 1 task in the list.");
        } else {
            System.out.printf("Now you have %s tasks in the list.\n", taskList.isEmpty() ? "no" : taskList.size());
        }
        printSeparator();
    }

    public void printAddStatus(Task task, TaskList taskList) {
        System.out.println("Got it. I've added this task:\n" + task);
        printListStatus(taskList);
    }

    public void printDeleteStatus(Task task, TaskList taskList) {
        System.out.println("Into the blue again\nAfter this task is gone:\n" + task);
        printListStatus(taskList);
    }

    public void printErrorMessage(Exception e) {
        System.out.println(e.getMessage());
        printSeparator();
    }

    public void printMarkStatus(Task task) {
        System.out.println("Nice! I've marked this task as done:\n" + task);
        printSeparator();
    }

    public void printUnmarkStatus(Task task) {
        System.out.println("OK, I've marked this task as not done yet:\n" + task);
        printSeparator();
    }

    public void printTasks(TaskList taskList) {
        System.out.println("Take a look at these tasks:");
        for (int i = 0; i < taskList.size(); i++) {
            System.out.printf("%d.%s\n", i + 1, taskList.get(i));
        }
        printSeparator();
    }
}

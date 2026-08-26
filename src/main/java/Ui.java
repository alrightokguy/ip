import java.util.ArrayList;

public class Ui {

    public Ui() {
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

    public void printListStatus(ArrayList<Task> tasks) {
        if (tasks.size() == 1) {
            System.out.println("Now you have 1 task in the list.");
        } else {
            System.out.printf("Now you have %s tasks in the list.\n", tasks.isEmpty() ? "no" : tasks.size());
        }
    }

    public void printAddStatus(Task task, ArrayList<Task> tasks) {
        System.out.println("Got it. I've added this task:\n" + task);
        printListStatus(tasks);
    }

    public void printDeleteStatus(Task task, ArrayList<Task> tasks) {
        System.out.println("Into the blue again\nAfter this task is gone:\n" + task);
        printListStatus(tasks);
    }
}

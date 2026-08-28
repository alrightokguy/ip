package chattingheads.ui;

import java.util.Scanner;

import chattingheads.task.Task;
import chattingheads.task.TaskList;

/**
 * Handles user input and displays messages to the user.
 */
public class Ui {

    private final Scanner scanner = new Scanner(System.in);

    /**
     * Reads the next command entered by the user.
     *
     * @return User input.
     */
    public String readCommand() {
        return scanner.nextLine();
    }

    /**
     * Prints the startup message.
     */
    public void printStartupMessage() {
        System.out.println("""
                Hello! I'm Chatting Heads.
                You may find yourself
                Living in a shotgun shack
                What can I do for you?""");
        printSeparator();
    }

    /**
     * Prints the shutdown message.
     */
    public void printShutdownMessage() {
        System.out.println("Letting the days go \"bye!\"\nLet the water shut me down");
        printSeparator();
    }

    /**
     * Prints a separator line.
     */
    public void printSeparator() {
        System.out.println("------------------------------------------------------------");
    }

    /**
     * Prints the number of tasks currently in the task list.
     *
     * @param taskList Task list whose size is displayed.
     */
    public void printListStatus(TaskList taskList) {
        if (taskList.size() == 1) {
            System.out.println("Now you have 1 task in the list.");
        } else {
            System.out.printf(
                    "Now you have %s tasks in the list.\n",
                    taskList.isEmpty() ? "no" : taskList.size()
            );
        }
        printSeparator();
    }

    /**
     * Prints a message confirming that a task was added.
     *
     * @param task Added task.
     * @param taskList Task list after the addition.
     */
    public void printAddStatus(Task task, TaskList taskList) {
        System.out.println("Got it. I've added this task:\n" + task);
        printListStatus(taskList);
    }

    /**
     * Prints a message confirming that a task was deleted.
     *
     * @param task Deleted task.
     * @param taskList Task list after the deletion.
     */
    public void printDeleteStatus(Task task, TaskList taskList) {
        System.out.println("Into the blue again\nAfter this task is gone:\n" + task);
        printListStatus(taskList);
    }

    /**
     * Prints an error message.
     *
     * @param e Exception containing the error message.
     */
    public void printErrorMessage(Exception e) {
        System.out.println(e.getMessage());
        printSeparator();
    }

    /**
     * Prints a message confirming that a task was marked as completed.
     *
     * @param task Marked task.
     */
    public void printMarkStatus(Task task) {
        System.out.println("Nice! I've marked this task as done:\n" + task);
        printSeparator();
    }

    /**
     * Prints a message confirming that a task was marked as incomplete.
     *
     * @param task Unmarked task.
     */
    public void printUnmarkStatus(Task task) {
        System.out.println("OK, I've marked this task as not done yet:\n" + task);
        printSeparator();
    }

    /**
     * Prints all tasks in the task list.
     *
     * @param taskList Task list to display.
     */
    public void printTasks(TaskList taskList) {
        System.out.println("Take a look at these tasks:");
        for (int i = 0; i < taskList.size(); i++) {
            System.out.printf("%d.%s\n", i + 1, taskList.get(i));
        }
        printSeparator();
    }
}

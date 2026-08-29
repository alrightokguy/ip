package chattingheads.ui;

import java.util.List;
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
     * Returns the startup message.
     */
    public String getStartupMessage() {
        return """
                Hello! I'm Chatting Heads.
                You may find yourself
                Living in a shotgun shack
                What can I do for you?
                """ + getSeparator();
    }

    /**
     * Returns the shutdown message.
     */
    public String getShutdownMessage() {
        return "Letting the days go \"bye!\"\nLet the water shut me down\n" + getSeparator();
    }

    /**
     * Returns a separator line.
     */
    public String getSeparator() {
        return "--------------------------------------------------------------------------------";
    }

    /**
     * Returns the number of tasks currently in the task list.
     *
     * @param taskList Task list whose size is displayed.
     */
    public String getListStatus(TaskList taskList) {
        if (taskList.size() == 1) {
            return "Now you have 1 task in the list.\n" + getSeparator();
        } else {
            return String.format(
                    "Now you have %s tasks in the list.\n%s",
                    taskList.isEmpty() ? "no" : taskList.size(),
                    getSeparator()
            );
        }
    }

    /**
     * Returns a message confirming that a task was added.
     *
     * @param task     Added task.
     * @param taskList Task list after the addition.
     */
    public String getAddStatus(Task task, TaskList taskList) {
        return String.format(
                "Got it. I've added this task:\n%s\n%s",
                task,
                getListStatus(taskList)
        );
    }

    /**
     * Returns a message confirming that a task was deleted.
     *
     * @param task     Deleted task.
     * @param taskList Task list after the deletion.
     */
    public String getDeleteStatus(Task task, TaskList taskList) {
        return String.format(
                "Into the blue again\nAfter this task is gone:\n%s\n%s",
                task,
                getListStatus(taskList)
        );
    }

    /**
     * Returns an error message.
     *
     * @param e Exception containing the error message.
     */
    public String getErrorMessage(Exception e) {
        return e.getMessage() + "\n" + getSeparator();
    }

    /**
     * Returns a message confirming that a task was marked as completed.
     *
     * @param task Marked task.
     */
    public String getMarkStatus(Task task) {
        return String.format(
                "Nice! I've marked this task as done:\n%s\n%s",
                task,
                getSeparator()
        );
    }

    /**
     * Returns a message confirming that a task was marked as incomplete.
     *
     * @param task Unmarked task.
     */
    public String getUnmarkStatus(Task task) {
        return String.format(
                "OK, I've marked this task as not done yet:\n%s\n%s",
                task,
                getSeparator()
        );
    }

    /**
     * Returns a message listing all tasks in the task list.
     *
     * @param taskList Task list to display.
     */
    public String getTasks(TaskList taskList) {
        StringBuilder message = new StringBuilder("Take a look at these tasks:");

        for (int i = 0; i < taskList.size(); i++) {
            message.append(String.format(
                    "%n%d. %s",
                    i + 1,
                    taskList.get(i)
            ));
        }
        message.append("\n").append(getSeparator());
        return message.toString();
    }

    /**
     * Returns all tasks in the task list that contains a specified keyword.
     *
     * @param taskList Task list to search.
     * @param keyword  Desired keyword to search tasks for.
     */
    public String getFoundTasks(TaskList taskList, String keyword) {
        StringBuilder message = new StringBuilder("Take a look at these tasks:");
        List<Integer> foundTasks = taskList.findIndices(keyword);

        for (Integer i : foundTasks) {
            message.append(String.format(
                    "%n%d. %s",
                    i + 1,
                    taskList.get(i)
            ));
        }
        message.append("\n").append(getSeparator());
        return message.toString();
    }
}

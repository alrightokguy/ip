package chattingheads.ui.gui;

/**
 * Represents the result of executing a command through the GUI.
 *
 * @param response Response from command execution or error.
 * @param isExit   Whether the command causes the program to exit.
 */
public record CommandResult(String response, boolean isExit) {
}

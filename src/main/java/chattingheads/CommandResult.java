package chattingheads;

/**
 * Represents the result of executing a command through the GUI
 *
 * @param response The response from command execution or error
 * @param isExit Whether the command leads to an exit of the program (i.e. bye command)
 */
public record CommandResult(String response, boolean isExit) {
}

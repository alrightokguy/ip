package chattingHeads.exception;

/**
 * Represents an error caused by an unrecognised command.
 */
public class InvalidCommandException extends Exception {

    public InvalidCommandException() {
        super("And you may ask yourself\n\"How do I work this?\"");
    }
}

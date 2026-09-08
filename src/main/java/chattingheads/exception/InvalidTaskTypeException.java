package chattingheads.exception;

public class InvalidTaskTypeException extends Exception {
    public InvalidTaskTypeException(String correctTaskType) {
        super(String.format("Selected task is not %s\nLooks like I can't change you", correctTaskType));
    }
}

package chattingheads.exception;

/**
 * Represents an error caused by 1 or multiple missing or invalid inputs
 */
public class InvalidInputException extends ChattingHeadsException {

    private InvalidInputException(String message) {
        super(message);
    }

    public static InvalidInputException invalidInput(String... inputs) {
        StringBuilder message = new StringBuilder("And you may ask yourself");
        for (String input : inputs) {
            if (input.equals("deadline") || input.equals("start") || input.equals("end")) {
                message.append(String.format("\n\"Where is that %s? (dd/MM/yyyy HH:mm)\"", input));
            } else {
                message.append(String.format("\n\"Where is that %s?\"", input));
            }
        }
        return new InvalidInputException(message.toString());
    }

    public static InvalidInputException endBeforeStart() {
        return new InvalidInputException(
                "End time before start time\nTime isn't holding up\nTime isn't after us"
        );
    }
}

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

    public static InvalidInputException invalidTimeRange() {
        return new InvalidInputException(
                "End time not after start time\nTime isn't holding up\nTime isn't after us"
        );
    }

    public static InvalidInputException duplicatePrefix(String prefix) {
        return new InvalidInputException(
                String.format("Duplicate prefix: %s\nDouble prefix, double prefix, double prefix", prefix)
        );
    }

    public static InvalidInputException incompatiblePrefixes(String... prefixes) {
        return new InvalidInputException(
                "These prefixes cannot be used together: "
                        + String.join(", ", prefixes) + ".");
    }
}

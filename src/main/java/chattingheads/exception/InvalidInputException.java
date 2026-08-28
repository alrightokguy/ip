package chattingheads.exception;

import java.util.ArrayList;

/**
 * Represents an error caused by 1 or multiple missing or invalid inputs
 */
public class InvalidInputException extends Exception {

    public InvalidInputException(String input) {
        super(String.format("And you may ask yourself\n\"Where is that %s?\"", input));
    }

    public InvalidInputException(ArrayList<String> inputs) {
        StringBuilder message = new StringBuilder("And you may ask yourself");
        for (String input : inputs) {
            if (input.equals("deadline") || input.equals("start") || input.equals("end")) {
                message.append(String.format("\n\"Where is that %s? (dd/MM/yyyy HH:mm)\"", input));
            } else {
                message.append(String.format("\n\"Where is that %s?\"", input));
            }

        }
        super(message.toString());
    }
}

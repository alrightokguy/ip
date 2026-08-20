import java.util.ArrayList;

public class EmptyInputException extends Exception {

    public EmptyInputException(String input) {
        super(String.format("And you may ask yourself\n\"Where is that %s?\"", input));
    }

    public EmptyInputException(ArrayList<String> inputs) {
        StringBuilder message = new StringBuilder("And you may ask yourself");
        for (String input : inputs) {
            message.append(String.format("\n\"Where is that %s?\"", input));
        }
        super(message.toString());
    }
}
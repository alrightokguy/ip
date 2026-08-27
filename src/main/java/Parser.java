import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;

public class Parser {

    private final DateTimeFormatter DATE_TIME_FORMATTER =
            DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    public Command parse(String input) throws InvalidInputException {
        if (input.isEmpty()) {
            throw new InvalidInputException("command");
        }
        String[] tokens = input.split("\\s+");
        String command = tokens[0];
        String[] arguments = Arrays.copyOfRange(tokens, 1, tokens.length);

        return switch (command) {
            case "todo" -> parseTodo(arguments);
            case "deadline" -> parseDeadline(arguments);
            case "event" -> parseEvent(arguments);
            case "list" -> new ListCommand();
            case "mark" -> new MarkCommand(parseTaskNumber(arguments));
            case "unmark" -> new UnmarkCommand(parseTaskNumber(arguments));
            case "delete" -> new DeleteCommand(parseTaskNumber(arguments));
            case "bye" -> new ExitCommand();
            default -> throw new InvalidInputException("command");
        }
    }

    private AddTodoCommand parseTodo(String[] arguments) throws InvalidInputException {
        String description = parseString(arguments, 0, arguments.length);

        if (description.isEmpty()) {
            throw new InvalidInputException("description");
        }

        return new AddTodoCommand(description);
    }

    private AddDeadlineCommand parseDeadline(String[] arguments) throws InvalidInputException {
        int marker = arguments.length;

        for (int i = 0; i < arguments.length; i++) {
            if (arguments[i].equals("/by")) {
                marker = i;
                break;
            }
        }
        ArrayList<String> emptyInputs = new ArrayList<>();
        String description = parseString(arguments, 0, marker);
        LocalDateTime by = parseDateTime(arguments, marker + 1, arguments.length);

        if (description.isEmpty()) {
            emptyInputs.add("description");
        }
        if (by == null) {
            emptyInputs.add("deadline");
        }
        if  (!emptyInputs.isEmpty()) {
            throw new InvalidInputException(emptyInputs);
        }

        return new AddDeadlineCommand(description, by);
    }

    private void parseEvent(String[] arguments) {

    }

    private int parseTaskNumber(String[] arguments) {
        return Integer.parseInt(arguments[0]);
    }

    private String parseString(String[] tokens, int start, int end) {
        if (start >= 0 && end <= tokens.length && start <= end) {
            return String.join(" ", Arrays.copyOfRange(tokens, start, end));
        }
        return "";
    }

    private LocalDateTime parseDateTime(String[] tokens, int start, int end) {
        try {
            return LocalDateTime.parse(parseString(tokens, start, end), DATE_TIME_FORMATTER);
        } catch (DateTimeParseException e) {
            return null;
        }
    }
}

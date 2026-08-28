package chattingheads.parser;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;

import chattingheads.command.AddDeadlineCommand;
import chattingheads.command.AddEventCommand;
import chattingheads.command.AddTodoCommand;
import chattingheads.command.Command;
import chattingheads.command.DeleteCommand;
import chattingheads.command.ExitCommand;
import chattingheads.command.FindCommand;
import chattingheads.command.ListCommand;
import chattingheads.command.MarkCommand;
import chattingheads.command.UnmarkCommand;
import chattingheads.exception.InvalidCommandException;
import chattingheads.exception.InvalidInputException;

/**
 * Parses user input into executable commands.
 */
public class Parser {

    private static final DateTimeFormatter DATE_TIME_FORMATTER =
            DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    /**
     * Parses user input into the corresponding command.
     *
     * @param input User input to parse.
     * @return Command represented by the input.
     * @throws InvalidInputException If required command arguments are invalid or missing.
     * @throws InvalidCommandException If the command is not recognised.
     */
    public Command parse(String input) throws InvalidInputException, InvalidCommandException {
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
            case "find" -> new FindCommand(parseString(arguments, 1, arguments.length));
            case "mark" -> new MarkCommand(parseTaskNumber(arguments));
            case "unmark" -> new UnmarkCommand(parseTaskNumber(arguments));
            case "delete" -> new DeleteCommand(parseTaskNumber(arguments));
            case "bye" -> new ExitCommand();
            default -> throw new InvalidCommandException();
        };
    }

    /**
     * Parses the arguments of a todo command.
     *
     * @param arguments Arguments of the command.
     * @return Command for adding the todo.
     * @throws InvalidInputException If the description is missing.
     */
    private AddTodoCommand parseTodo(String[] arguments) throws InvalidInputException {
        String description = parseString(arguments, 0, arguments.length);

        if (description.isEmpty()) {
            throw new InvalidInputException("description");
        }

        return new AddTodoCommand(description);
    }

    /**
     * Parses the arguments of a deadline command.
     *
     * @param arguments Arguments of the command.
     * @return Command for adding the deadline.
     * @throws InvalidInputException If the description or deadline is invalid or missing.
     */
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
        if (!emptyInputs.isEmpty()) {
            throw new InvalidInputException(emptyInputs);
        }

        return new AddDeadlineCommand(description, by);
    }

    /**
     * Parses the arguments of an event command.
     *
     * @param arguments Arguments of the command.
     * @return Command for adding the event.
     * @throws InvalidInputException If the description, start, or end is invalid or missing.
     */
    private AddEventCommand parseEvent(String[] arguments) throws InvalidInputException {
        int marker1 = arguments.length;
        int marker2 = arguments.length;

        for (int i = 0; i < arguments.length; i++) {
            if (arguments[i].equals("/from")) {
                marker1 = i;
            } else if (arguments[i].equals("/to")) {
                marker2 = i;
                break;
            }
        }
        ArrayList<String> emptyInputs = new ArrayList<>();
        String description = parseString(arguments, 0, marker1);
        LocalDateTime from = parseDateTime(arguments, marker1 + 1, marker2);
        LocalDateTime to = parseDateTime(arguments, marker2 + 1, arguments.length);

        if (description.isEmpty()) {
            emptyInputs.add("description");
        }
        if (from == null) {
            emptyInputs.add("start");
        }
        if (to == null) {
            emptyInputs.add("end");
        }
        if (!emptyInputs.isEmpty()) {
            throw new InvalidInputException(emptyInputs);
        }

        return new AddEventCommand(description, from, to);
    }

    /**
     * Parses a task number from command arguments.
     *
     * @param arguments Arguments containing the task number.
     * @return Parsed task number.
     * @throws InvalidInputException If the task number is missing.
     */
    private int parseTaskNumber(String[] arguments) throws InvalidInputException {
        if (arguments.length < 1) {
            throw new InvalidInputException("task number");
        }

        try {
            return Integer.parseInt(arguments[0]);
        } catch (NumberFormatException e) {
            throw new InvalidInputException("task number");
        }
    }

    /**
     * Joins tokens within the specified range into a string.
     *
     * @param tokens Tokens to join.
     * @param start Inclusive start index.
     * @param end Exclusive end index.
     * @return Joined string, or an empty string if the range is invalid.
     */
    private String parseString(String[] tokens, int start, int end) {
        if (start >= 0 && end <= tokens.length && start <= end) {
            return String.join(" ", Arrays.copyOfRange(tokens, start, end));
        }
        return "";
    }

    /**
     * Parses tokens within the specified range as a date and time.
     *
     * @param tokens Tokens containing the date and time.
     * @param start Inclusive start index.
     * @param end Exclusive end index.
     * @return Parsed date and time, or {@code null} if parsing fails.
     */
    private LocalDateTime parseDateTime(String[] tokens, int start, int end) {
        try {
            return LocalDateTime.parse(parseString(tokens, start, end), DATE_TIME_FORMATTER);
        } catch (DateTimeParseException e) {
            return null;
        }
    }
}

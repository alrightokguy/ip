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
import chattingheads.command.RescheduleDeadlineCommand;
import chattingheads.command.RescheduleEventCommand;
import chattingheads.command.UnmarkCommand;
import chattingheads.exception.ChattingHeadsException;
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
     * @throws ChattingHeadsException If required command arguments are invalid or missing.
     */
    public Command parse(String input) throws ChattingHeadsException {
        if (input.isEmpty()) {
            throw InvalidInputException.invalidInput("command");
        }
        String[] tokens = input.split("\\s+");
        String commandName = tokens[0];
        String[] arguments = Arrays.copyOfRange(tokens, 1, tokens.length);

        return switch (commandName) {
            case "todo" -> parseTodo(arguments);
            case "deadline" -> parseDeadline(arguments);
            case "event" -> parseEvent(arguments);
            case "list" -> new ListCommand();
            case "find" -> new FindCommand(joinTokens(arguments, 0, arguments.length));
            case "mark" -> new MarkCommand(parseTaskNumber(arguments));
            case "unmark" -> new UnmarkCommand(parseTaskNumber(arguments));
            case "reschedule" -> parseReschedule(arguments);
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
        String description = joinTokens(arguments, 0, arguments.length);

        if (description.isEmpty()) {
            throw InvalidInputException.invalidInput("description");
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
        int byMarkerIndex = arguments.length;

        for (int i = 0; i < arguments.length; i++) {
            if (arguments[i].equals("/by")) {
                byMarkerIndex = i;
                break;
            }
        }
        ArrayList<String> invalidInputs = new ArrayList<>();
        String description = joinTokens(arguments, 0, byMarkerIndex);
        LocalDateTime deadline = parseDateTime(arguments, byMarkerIndex + 1, arguments.length);

        if (description.isEmpty()) {
            invalidInputs.add("description");
        }
        if (deadline == null) {
            invalidInputs.add("deadline");
        }
        if (!invalidInputs.isEmpty()) {
            throw InvalidInputException.invalidInput(invalidInputs.toArray(String[]::new));
        }

        return new AddDeadlineCommand(description, deadline);
    }

    /**
     * Parses the arguments of an event command.
     *
     * @param arguments Arguments of the command.
     * @return Command for adding the event.
     * @throws InvalidInputException If the description, start, or end is invalid or missing.
     */
    private AddEventCommand parseEvent(String[] arguments) throws InvalidInputException {
        int fromMarkerIndex = arguments.length;
        int toMarkerIndex = arguments.length;

        for (int i = 0; i < arguments.length; i++) {
            if (arguments[i].equals("/from")) {
                fromMarkerIndex = i;
            } else if (arguments[i].equals("/to")) {
                toMarkerIndex = i;
                break;
            }
        }
        ArrayList<String> invalidInputs = new ArrayList<>();
        String description = joinTokens(arguments, 0, fromMarkerIndex);
        LocalDateTime start = parseDateTime(arguments, fromMarkerIndex + 1, toMarkerIndex);
        LocalDateTime end = parseDateTime(arguments, toMarkerIndex + 1, arguments.length);

        if (description.isEmpty()) {
            invalidInputs.add("description");
        }
        if (start == null) {
            invalidInputs.add("start");
        }
        if (end == null) {
            invalidInputs.add("end");
        }
        if (!invalidInputs.isEmpty()) {
            throw InvalidInputException.invalidInput(invalidInputs.toArray(String[]::new));
        }
        if(end.isBefore(start)) {
            throw InvalidInputException.endBeforeStart();
        }

        return new AddEventCommand(description, start, end);
    }

    private Command parseReschedule(String[] arguments) throws InvalidInputException {
        if (Arrays.asList(arguments).contains("/by")) {
            return parseRescheduleDeadline(arguments);
        }
        if (Arrays.asList(arguments).contains("/from") && Arrays.asList(arguments).contains("/to")) {
            return parseRescheduleEvent(arguments);
        }
        throw InvalidInputException.invalidInput("/by or /from and /to");
    }

    private Command parseRescheduleDeadline(String[] arguments) throws InvalidInputException {
        int byMarkerIndex = arguments.length;

        for (int i = 0; i < arguments.length; i++) {
            if (arguments[i].equals("/by")) {
                byMarkerIndex = i;
                break;
            }
        }
        ArrayList<String> invalidInputs = new ArrayList<>();
        int taskNumber = -1;
        try {
            taskNumber = parseTaskNumber(arguments);
        } catch (InvalidInputException e) {
            invalidInputs.add("task number");
        }

        LocalDateTime deadline = parseDateTime(arguments, byMarkerIndex + 1, arguments.length);

        if (deadline == null) {
            invalidInputs.add("deadline");
        }
        if (!invalidInputs.isEmpty()) {
            throw InvalidInputException.invalidInput(invalidInputs.toArray(String[]::new));
        }

        return new RescheduleDeadlineCommand(taskNumber, deadline);
    }

    private Command parseRescheduleEvent(String[] arguments) throws InvalidInputException {
        int fromMarkerIndex = arguments.length;
        int toMarkerIndex = arguments.length;

        for (int i = 0; i < arguments.length; i++) {
            if (arguments[i].equals("/from")) {
                fromMarkerIndex = i;
            } else if (arguments[i].equals("/to")) {
                toMarkerIndex = i;
                break;
            }
        }
        ArrayList<String> invalidInputs = new ArrayList<>();
        int taskNumber = -1;
        try {
            taskNumber = parseTaskNumber(arguments);
        } catch (InvalidInputException e) {
            invalidInputs.add("task number");
        }

        LocalDateTime start = parseDateTime(arguments, fromMarkerIndex + 1, toMarkerIndex);
        LocalDateTime end = parseDateTime(arguments, toMarkerIndex + 1, arguments.length);

        if (start == null) {
            invalidInputs.add("start");
        }
        if (end == null) {
            invalidInputs.add("end");
        }
        if (!invalidInputs.isEmpty()) {
            throw InvalidInputException.invalidInput(invalidInputs.toArray(String[]::new));
        }
        if(end.isBefore(start)) {
            throw InvalidInputException.endBeforeStart();
        }

        return new RescheduleEventCommand(taskNumber, start, end);
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
            throw InvalidInputException.invalidInput("task number");
        }

        try {
            return Integer.parseInt(arguments[0]);
        } catch (NumberFormatException e) {
            throw InvalidInputException.invalidInput("task number");
        }
    }

    /**
     * Joins tokens within the specified range into a string.
     *
     * @param tokens Tokens to join.
     * @param start  Inclusive start index.
     * @param end    Exclusive end index.
     * @return Joined string, or an empty string if the range is invalid.
     */
    private String joinTokens(String[] tokens, int start, int end) {
        if (start >= 0 && end <= tokens.length && start <= end) {
            return String.join(" ", Arrays.copyOfRange(tokens, start, end));
        }
        return "";
    }

    /**
     * Parses tokens within the specified range as a date and time.
     *
     * @param tokens Tokens containing the date and time.
     * @param start  Inclusive start index.
     * @param end    Exclusive end index.
     * @return Parsed date and time, or {@code null} if parsing fails.
     */
    private LocalDateTime parseDateTime(String[] tokens, int start, int end) {
        try {
            return LocalDateTime.parse(joinTokens(tokens, start, end), DATE_TIME_FORMATTER);
        } catch (DateTimeParseException e) {
            return null;
        }
    }
}

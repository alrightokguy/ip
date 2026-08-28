package chattingheads.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import chattingheads.command.AddDeadlineCommand;
import chattingheads.command.AddEventCommand;
import chattingheads.command.AddTodoCommand;
import chattingheads.command.Command;
import chattingheads.command.DeleteCommand;
import chattingheads.command.ExitCommand;
import chattingheads.command.ListCommand;
import chattingheads.command.MarkCommand;
import chattingheads.command.UnmarkCommand;
import chattingheads.exception.InvalidCommandException;
import chattingheads.exception.InvalidInputException;


public class ParserTest {

    private final Parser parser = new Parser();

    @Test
    public void parse_validTodo_returnsAddTodoCommand()
            throws InvalidInputException, InvalidCommandException {
        Command command = parser.parse("todo read book");
        assertInstanceOf(AddTodoCommand.class, command);
    }

    @Test
    public void parse_validTodo_returnsCorrectDescription()
            throws InvalidInputException, InvalidCommandException {
        Parser parser = new Parser();
        Command command = parser.parse("todo read book");
        AddTodoCommand addTodoCommand = (AddTodoCommand) command;
        assertEquals("read book", addTodoCommand.getDescription());
    }

    @Test
    public void parse_validDeadline_returnsAddDeadlineCommand()
            throws InvalidInputException, InvalidCommandException {
        Command command = parser.parse("deadline submit report /by 28/08/2026 18:00");
        assertInstanceOf(AddDeadlineCommand.class, command);
    }

    @Test
    public void parse_validEvent_returnsAddEventCommand()
            throws InvalidInputException, InvalidCommandException {
        Command command = parser.parse("event meeting /from 28/08/2026 18:00 /to 28/08/2026 20:00");
        assertInstanceOf(AddEventCommand.class, command);
    }

    @Test
    public void parse_validList_returnsListCommand()
            throws InvalidInputException, InvalidCommandException {
        Command command = parser.parse("list");
        assertInstanceOf(ListCommand.class, command);
    }

    @Test
    public void parse_validMark_returnsCorrectTaskNumber()
            throws InvalidInputException, InvalidCommandException {
        Command command = parser.parse("mark 2");
        MarkCommand markCommand = (MarkCommand) command;
        assertEquals(2, markCommand.getTaskNumber());
    }

    @Test
    public void parse_validUnmark_returnsCorrectTaskNumber()
            throws InvalidInputException, InvalidCommandException {
        Command command = parser.parse("unmark 3");
        UnmarkCommand unmarkCommand = (UnmarkCommand) command;
        assertEquals(3, unmarkCommand.getTaskNumber());
    }

    @Test
    public void parse_validDelete_returnsCorrectTaskNumber()
            throws InvalidInputException, InvalidCommandException {
        Command command = parser.parse("delete 3");
        DeleteCommand deleteCommand = (DeleteCommand) command;
        assertEquals(3, deleteCommand.getTaskNumber());
    }

    @Test
    public void parse_validBye_returnsExitCommand()
            throws InvalidInputException, InvalidCommandException {
        Command command = parser.parse("bye");
        assertInstanceOf(ExitCommand.class, command);
    }

    @Test
    public void parse_invalidCommand_throwsInvalidCommandException() {
        assertThrows(InvalidCommandException.class, () -> parser.parse("test"));
    }

    @Test
    public void parse_invalidInput_throwsInvalidInputException() {
        assertThrows(InvalidInputException.class, () -> parser.parse("deadline test /by tomorrow"));
    }
}

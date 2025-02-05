package echolex.command;

import echolex.error.EchoLexException;
import echolex.task.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class CommandTest {

    private TaskList taskList;

    @BeforeEach
    void setUp() {
        taskList = new TaskList();
    }

    @Test
    void testIsExit() {
        Command exitCommand = new Command("bye", "", new HashMap<>());
        assertTrue(exitCommand.isExit());

        Command notExitCommand = new Command("list", "", new HashMap<>());
        assertFalse(notExitCommand.isExit());
    }

    @Test
    void testListCommand() {
        taskList.add(new Todo("Buy groceries", false));
        taskList.add(new Deadline("Submit assignment", false, LocalDateTime.of(2025, 2, 10, 23, 59)));

        Command listCommand = new Command("list", "", new HashMap<>());
        String expectedOutput = "Here are the tasks in your list:\n" +
                "1.[T][ ] Buy groceries\n" +
                "2.[D][ ] Submit assignment (by: Feb 10 2025)\n";
        assertEquals(expectedOutput, listCommand.listCommand(taskList));
    }

    @Test
    void testMarkCommand_validIndex() throws EchoLexException {
        taskList.add(new Todo("Read book", false));

        Command markCommand = new Command("mark", "1", new HashMap<>());
        String result = markCommand.markCommand(taskList);

        assertEquals("Nice! I've marked this task as done:\n  [T][X] Read book", result);
    }

    @Test
    void testMarkCommand_invalidIndex() {
        Command markCommand = new Command("mark", "5", new HashMap<>());

        assertThrows(EchoLexException.class, () -> markCommand.markCommand(taskList));
    }

    @Test
    void testUnmarkCommand_validIndex() throws EchoLexException {
        taskList.add(new Todo("Write blog", true));

        Command unmarkCommand = new Command("unmark", "1", new HashMap<>());
        String result = unmarkCommand.markCommand(taskList);

        assertEquals("OK, I've marked this task as not done yet:\n  [T][ ] Write blog", result);
    }

    @Test
    void testDeleteCommand_validIndex() throws EchoLexException {
        taskList.add(new Todo("Exercise", false));

        Command deleteCommand = new Command("delete", "1", new HashMap<>());
        String result = deleteCommand.deleteCommand(taskList);

        assertTrue(taskList.isEmpty());
        assertEquals("Noted. I've removed this task:\n  [T][ ] Exercise\nNow you have 0 tasks in the list.", result);
    }

    @Test
    void testAddCommand_todo() throws EchoLexException {
        Command addTodoCommand = new Command("todo", "Learn Java", new HashMap<>());
        String result = addTodoCommand.addCommand(taskList);

        assertEquals(1, taskList.size());
        assertEquals("Got it. I've added this task:\n  [T][ ] Learn Java\nNow you have 1 tasks in the list.", result);
    }

    @Test
    void testAddCommand_deadline() throws EchoLexException {
        HashMap<String, String> options = new HashMap<>();
        options.put("by", "2025-02-15");

        Command addDeadlineCommand = new Command("deadline", "Finish project", options);
        String result = addDeadlineCommand.addCommand(taskList);

        assertEquals(1, taskList.size());
        assertTrue(taskList.get(0) instanceof Deadline);
        assertEquals("Got it. I've added this task:\n  [D][ ] Finish project (by: Feb 15 2025)\nNow you have 1 tasks in the list.", result);
    }

    @Test
    void testAddCommand_deadline_missingBy() {
        HashMap<String, String> options = new HashMap<>();

        Command addDeadlineCommand = new Command("deadline", "Submit report", options);

        assertThrows(EchoLexException.class, () -> addDeadlineCommand.addCommand(taskList));
    }

    @Test
    void testAddCommand_event() throws EchoLexException {
        HashMap<String, String> options = new HashMap<>();
        options.put("from", "2025-03-01");
        options.put("to", "2025-03-02");

        Command addEventCommand = new Command("event", "Company retreat", options);
        String result = addEventCommand.addCommand(taskList);

        assertEquals(1, taskList.size());
        assertTrue(taskList.get(0) instanceof Event);
        assertEquals("Got it. I've added this task:\n  [E][ ] Company retreat (from: Mar 01 2025 to: Mar 02 2025)\nNow you have 1 tasks in the list.", result);
    }

    @Test
    void testAddCommand_event_missingFrom() {
        HashMap<String, String> options = new HashMap<>();
        options.put("to", "2025-03-02");

        Command addEventCommand = new Command("event", "Conference", options);

        assertThrows(EchoLexException.class, () -> addEventCommand.addCommand(taskList));
    }

    @Test
    void testAddCommand_event_missingTo() {
        HashMap<String, String> options = new HashMap<>();
        options.put("from", "2025-03-01");

        Command addEventCommand = new Command("event", "Workshop", options);

        assertThrows(EchoLexException.class, () -> addEventCommand.addCommand(taskList));
    }
}

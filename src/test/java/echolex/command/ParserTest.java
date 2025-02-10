package echolex.command;

import echolex.error.EchoLexException;
import echolex.utility.Parser;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class ParserTest {

    @Test
    void parse_validCommand_returnsCommandObject() throws EchoLexException {
        String input = "deadline Submit report /by 2025-02-10";
        Command command = Parser.parse(input);

        assertEquals("deadline", command.getCommand());
        assertEquals("Submit report", command.getArgument());
        assertEquals("2025-02-10", command.getOptions("by"));
    }

    @Test
    void parse_invalidCommand_throwsException() {
        String input = "invalidcommand";
        assertDoesNotThrow(() -> {
            Command command = Parser.parse(input);
            assertEquals("invalidcommand", command.getCommand());
            assertTrue(command.getArgument().isEmpty());
        });
    }

    @Test
    void parseDate_validDateFormats_returnsLocalDateTime() throws EchoLexException {
        LocalDateTime expectedDate = LocalDateTime.of(2025, 2, 10, 0, 0);

        assertEquals(expectedDate, Parser.parseDate("2025-2-10"));
        assertEquals(expectedDate, Parser.parseDate("2025-02-10"));
        assertEquals(expectedDate, Parser.parseDate("10-02-2025"));
        assertEquals(expectedDate, Parser.parseDate("Feb 10 2025"));
    }

    @Test
    void parseDate_invalidDate_throwsException() {
        assertThrows(EchoLexException.class, () -> Parser.parseDate("invalid-date"));
        assertThrows(EchoLexException.class, () -> Parser.parseDate("2025-13-10"));
    }
}

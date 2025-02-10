package echolex.utility;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.time.temporal.ChronoField;
import java.util.Arrays;
import java.util.HashMap;

import echolex.command.AddCommand;
import echolex.command.Command;
import echolex.command.DeleteCommand;
import echolex.command.FindCommand;
import echolex.command.ListCommand;
import echolex.command.MarkCommand;
import echolex.error.EchoLexException;

/**
 * Represents a command that can be executed on a task list.
 */
public class Parser {

    /**
     * Parses string into Command.
     *
     * @param commandString String input by user as command.
     * @return Command object.
     */
    public static Command parse(String commandString) throws EchoLexException {

        // Split Input into Main Command & Options
        String[] parts = commandString.split("/");

        // Main Command
        String[] main = parts[0].split(" ", 2);
        String command = main[0];
        String argument = "";
        if (main.length > 1) {
            argument = main[1].trim();
        }

        // Options
        HashMap<String, String> options = new HashMap<>();
        for (String option : Arrays.copyOfRange(parts, 1, parts.length)) {
            assert option != null && !option.isEmpty();
            String[] optionParts = option.split(" ", 2);
            options.put(optionParts[0], optionParts[1]);
        }

        return identifyCommandType(command, argument, options);

    }

    /**
     * Identifies the command type.
     *
     * @param command Command string.
     * @param argument Argument string.
     * @param options Options hashmap.
     * @return Command object.
     */
    public static Command identifyCommandType(String command, String argument, HashMap<String, String> options) {

        switch (command) {
        case "list":
            return new ListCommand(command, argument, options);
        case "find":
            return new FindCommand(command, argument, options);
        case "mark":
        case "unmark":
            return new MarkCommand(command, argument, options);
        case "delete":
            return new DeleteCommand(command, argument, options);
        case "todo":
        case "deadline":
        case "event":
            return new AddCommand(command, argument, options);
        default:
            return new Command(command, argument, options);
        }

    }

    /**
     * Parse date in command string to LocalDateTime object.
     *
     * @param dateString Command string containing date.
     * @return LocalDateTime object.
     */
    public static LocalDateTime parseDate(String dateString) throws EchoLexException {

        try {

            dateString = dateString.trim();
            DateTimeFormatter formatter = new DateTimeFormatterBuilder()
                    .appendOptional(DateTimeFormatter.ofPattern("yyyy-M-d"))
                    .appendOptional(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
                    .appendOptional(DateTimeFormatter.ofPattern("dd-MM-yyyy"))
                    .appendOptional(DateTimeFormatter.ofPattern("MMM dd yyyy"))
                    .parseDefaulting(ChronoField.ERA, 1)
                    .toFormatter()
                    .withResolverStyle(ResolverStyle.STRICT);

            LocalDate date = LocalDate.parse(dateString, formatter);
            return date.atStartOfDay(); // may be modified to accommodate time in the future

        } catch (DateTimeParseException e) {
            throw new EchoLexException("Invalid date: " + e.getMessage()
                    + "\nPlease provide the date in yyyy-mm-dd format (e.g., 2019-10-15)");
        }

    }
}

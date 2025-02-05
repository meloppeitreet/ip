package echolex.command;

import echolex.error.EchoLexException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.time.temporal.ChronoField;

import java.util.HashMap;
import java.util.Arrays;

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
            String[] optionParts = option.split(" ", 2);
            options.put(optionParts[0], optionParts[1]);
        }

        return new Command(command, argument, options);

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
            return date.atStartOfDay();  // may be modified to accommodate time in the future

        } catch (DateTimeParseException e) {
            throw new EchoLexException("Invalid date: " + e.getMessage() + "\nPlease provide the date in yyyy-mm-dd format (e.g., 2019-10-15)");
        }

    }
}

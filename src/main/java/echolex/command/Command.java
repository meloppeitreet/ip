package echolex.command;

import java.util.HashMap;

import echolex.task.TaskList;

/**
 * Represents a command that can be executed on a task list.
 */
public class Command {

    protected final String command;
    protected final String argument;
    protected final HashMap<String, String> options;

    /**
     * Constructs a Command object.
     *
     * @param command The command type.
     * @param argument The argument for the command.
     * @param options Additional options for the command.
     */
    public Command(String command, String argument, HashMap<String, String> options) {
        this.command = command;
        this.argument = argument;
        this.options = options;
    }

    /**
     * Gets the command type.
     *
     * @return The command type.
     */
    public String getCommand() {
        return command;
    }

    /**
     * Gets the argument associated with the command.
     *
     * @return The command argument.
     */
    public String getArgument() {
        return argument;
    }

    /**
     * Retrieves the value of an option based on its key.
     *
     * @param key The key of the option.
     * @return The value associated with the key, or an empty string if not found.
     */
    public String getOptions(String key) {
        assert options != null : "options is null";
        return options.getOrDefault(key, "");
    }

    /**
     * Executes the command on the given task list.
     *
     * @param tasks Result of command execution.
     */
    public String execute(TaskList tasks) {
        return "Sorry, I did not understand that command.";
    }

    /**
     * Checks if the command input is the exit command.
     *
     * @return Formatted memory list string.
     */
    public boolean isExit() {
        return command.equals("bye");
    }
}

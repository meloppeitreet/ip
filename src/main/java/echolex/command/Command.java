package echolex.command;

import java.time.LocalDateTime;
import java.util.HashMap;

import echolex.error.EchoLexException;
import echolex.task.Deadline;
import echolex.task.Event;
import echolex.task.Task;
import echolex.task.TaskList;
import echolex.task.Todo;

/**
 * Represents a command that can be executed on a task list.
 */
public class Command {

    private final String command;
    private final String argument;
    private final HashMap<String, String> options;

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
     * @param tasks The list of tasks.
     */
    public String execute(TaskList tasks) {

        switch (command) {
        case "list":
            return listCommand(tasks);
        case "find":
            return findCommand(tasks);
        case "mark":
        case "unmark":
            try {
                return markCommand(tasks);
            } catch (EchoLexException e) {
                return "EchoLex Error: " + e.getMessage();
            }
        case "delete":
            try {
                return deleteCommand(tasks);
            } catch (EchoLexException e) {
                return "EchoLex Error: " + e.getMessage();
            }
        case "todo":
        case "deadline":
        case "event":
            try {
                return addCommand(tasks);
            } catch (EchoLexException e) {
                return "EchoLex Error: " + e.getMessage();
            }
        default:
            return "Sorry, I did not understand that command.";
        }

    }

    /**
     * Checks if the command input is the exit command.
     *
     * @return Formatted memory list string.
     */
    public boolean isExit() {
        return command.equals("bye");
    }

    /**
     * Lists previous input from user saved in memory.
     *
     * @param tasks List of Tasks.
     * @return Formatted memory list string.
     */
    public String listCommand(TaskList tasks) {

        int counter = 1;
        String result = "Here are the tasks in your list:\n";

        for (Task task : tasks) {
            assert task != null : "task is null";
            result = result.concat(counter + "." + task.toString() + "\n");
            counter++;
            assert counter > 0 : "Task counter less than 1";
        }

        return result;

    }

    /**
     * Marks or unmarks tasks as done.
     *
     * @param tasks List of Tasks.
     * @return Result of marking/unmarking.
     */
    public String markCommand(TaskList tasks) throws EchoLexException {

        try {
            int markIndex = Integer.parseInt(argument);
            if (markIndex > tasks.size() || markIndex < 0) {
                throw new EchoLexException("The specified task is out of range. Please try again.");
            } else {
                assert markIndex < tasks.size() && markIndex > 0 : "The specified task is out of range";
                Task markEntry = tasks.get(markIndex - 1);
                if (command.equals("mark")) {
                    markEntry.markDone();
                    return "Nice! I've marked this task as done:\n  " + markEntry.toString();
                } else if (command.equals("unmark")) {
                    markEntry.unmarkDone();
                    return "OK, I've marked this task as not done yet:\n  " + markEntry.toString();
                } else {
                    throw new EchoLexException("The command is not recognized.");
                }
            }
        } catch (NumberFormatException e) {
            throw new EchoLexException("Invalid index: " + argument);
        }

    }

    /**
     * Add Tasks.
     *
     * @param tasks List of Tasks.
     * @return Task created message.
     */
    public String addCommand(TaskList tasks) throws EchoLexException {

        Task task;

        switch (command) {
        case "deadline":
            task = deadlineAddCommand();
            break;
        case "event":
            task = eventAddCommand();
            break;
        default:
            task = new Todo(argument, Boolean.FALSE);
        }

        tasks.add(task);

        String result = "Got it. I've added this task:\n  " + task.toString();
        result = result.concat("\nNow you have " + tasks.size() + " tasks in the list.");
        return result;

    }

    /**
     * Creates Deadline Task.
     *
     * @return Task object.
     */
    public Task deadlineAddCommand() throws EchoLexException {
        String by = options.get("by");
        if (by == null) {
            throw new EchoLexException("Deadline option '/by' has not been provided.");
        }
        try { // parse "by" date
            LocalDateTime byDate = Parser.parseDate(by);
            return new Deadline(argument, Boolean.FALSE, byDate);
        } catch (EchoLexException e) {
            throw new EchoLexException(e.getMessage());
        }
    }

    /**
     * Creates Event Task.
     *
     * @return Event object.
     */
    public Task eventAddCommand() throws EchoLexException {
        String from = options.get("from");
        if (from == null) {
            throw new EchoLexException("Event option '/from' has not been provided.");
        }
        String to = options.get("to");
        if (to == null) {
            throw new EchoLexException("Event option '/to' has not been provided.");
        }
        try { // parse "from" and "to" dates
            LocalDateTime fromDate = Parser.parseDate(from);
            LocalDateTime toDate = Parser.parseDate(to);
            return new Event(argument, Boolean.FALSE, fromDate, toDate);
        } catch (EchoLexException e) {
            throw new EchoLexException(e.getMessage());
        }
    }

    /**
     * Delete tasks.
     *
     * @param tasks List of Tasks.
     * @return Result of task deletion.
     */
    public String deleteCommand(TaskList tasks) throws EchoLexException {

        int deleteIndex = Integer.parseInt(argument);
        if (deleteIndex > tasks.size() || deleteIndex < 0) {
            throw new EchoLexException("The specified task is out of range. Please try again.");
        } else {
            assert deleteIndex < tasks.size() && deleteIndex > 0 : "The specified task is out of range";
            String result = "Noted. I've removed this task:\n  " + tasks.get(deleteIndex - 1).toString();
            tasks.remove(deleteIndex - 1);
            result += "\nNow you have " + tasks.size() + " tasks in the list.";
            return result;
        }

    }

    /**
     * Find tasks.
     *
     * @param tasks List of Tasks.
     * @return Result of task deletion.
     */
    public String findCommand(TaskList tasks) {

        int counter = 1;
        String result = "";

        for (Task task : tasks) {
            assert task != null : "task is null";
            if (task.getDescription().contains(argument)) {
                result = result.concat(counter + "." + task.toString() + "\n");
                counter++;
                assert counter > 0 : "Task counter less than 1";
            }
        }

        return result;

    }

}

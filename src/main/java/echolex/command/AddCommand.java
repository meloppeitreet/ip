package echolex.command;

import java.time.LocalDateTime;
import java.util.HashMap;

import echolex.error.EchoLexException;
import echolex.task.Deadline;
import echolex.task.Event;
import echolex.task.Task;
import echolex.task.TaskList;
import echolex.task.Todo;
import echolex.utility.Parser;

/**
 * Represents a command to add a task to the task list.
 */
public class AddCommand extends Command {

    /**
     * Constructs an AddCommand object.
     *
     * @param command The command type.
     * @param argument The argument for the command.
     * @param options Additional options for the command.
     */
    public AddCommand(String command, String argument, HashMap<String, String> options) {
        super(command, argument, options);
    }

    /**
     * Executes the command on the given task list.
     * Add Tasks.
     *
     * @param tasks The list of tasks.
     * @return Result of task creation.
     */
    @Override
    public String execute(TaskList tasks) {

        Task task;

        switch (command) {
        case "deadline":
            try {
                task = deadlineAddCommand();
            } catch (EchoLexException e) {
                return "EchoLex Error: " + e.getMessage();
            }
            break;
        case "event":
            try {
                task = eventAddCommand();
            } catch (EchoLexException e) {
                return "EchoLex Error: " + e.getMessage();
            }
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
}

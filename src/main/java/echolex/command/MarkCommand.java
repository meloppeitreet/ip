package echolex.command;

import java.util.HashMap;

import echolex.task.Task;
import echolex.task.TaskList;

/**
 * Represents a command to delete a task from the task list.
 */
public class MarkCommand extends Command {

    /**
     * Constructs a ListCommand object.
     *
     * @param command The command type.
     * @param argument The argument for the command.
     * @param options Additional options for the command.
     */
    public MarkCommand(String command, String argument, HashMap<String, String> options) {
        super(command, argument, options);
    }

    /**
     * Executes the command on the given task list.
     * Marks or unmarks tasks as done.
     *
     * @param tasks The list of tasks.
     * @return Result of marking/unmarking.
     */
    @Override
    public String execute(TaskList tasks) {

        try {
            int markIndex = Integer.parseInt(argument);
            if (markIndex > tasks.size() || markIndex < 0) {
                return "The specified task is out of range. Please try again.";
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
                    return "The command is not recognized.";
                }
            }
        } catch (NumberFormatException e) {
            return "Invalid task index: \"" + argument + "\".\nPlease specify a number";
        }

    }
}

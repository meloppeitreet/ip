package echolex.command;

import java.util.HashMap;

import echolex.task.TaskList;

/**
 * Represents a command to delete a task from the task list.
 */
public class DeleteCommand extends Command {

    /**
     * Constructs a ListCommand object.
     *
     * @param command The command type.
     * @param argument The argument for the command.
     * @param options Additional options for the command.
     */
    public DeleteCommand(String command, String argument, HashMap<String, String> options) {
        super(command, argument, options);
    }

    /**
     * Executes the command on the given task list.
     * Delete tasks.
     *
     * @param tasks The list of tasks.
     * @return Result of task deletion.
     */
    @Override
    public String execute(TaskList tasks) {

        int deleteIndex = Integer.parseInt(argument);
        if (deleteIndex > tasks.size() || deleteIndex < 0) {
            return "The specified task is out of range. Please try again.";
        } else {
            assert deleteIndex < tasks.size() && deleteIndex > 0 : "The specified task is out of range";
            String result = "Noted. I've removed this task:\n  " + tasks.get(deleteIndex - 1).toString();
            tasks.remove(deleteIndex - 1);
            result += "\nNow you have " + tasks.size() + " tasks in the list.";
            return result;
        }

    }
}

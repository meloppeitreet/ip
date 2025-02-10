package echolex.command;

import java.util.HashMap;

import echolex.task.Task;
import echolex.task.TaskList;

/**
 * Represents a command to list the task list.
 */
public class ListCommand extends Command {

    /**
     * Constructs a ListCommand object.
     *
     * @param command The command type.
     * @param argument The argument for the command.
     * @param options Additional options for the command.
     */
    public ListCommand(String command, String argument, HashMap<String, String> options) {
        super(command, argument, options);
    }

    /**
     * Executes the command on the given task list.
     * Lists previous input from user saved in memory.
     *
     * @param tasks The list of tasks.
     * @return Results of task list formatting.
     */
    @Override
    public String execute(TaskList tasks) {

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
}

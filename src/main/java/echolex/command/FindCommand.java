package echolex.command;

import java.util.HashMap;
import java.util.List;

import echolex.task.Task;
import echolex.task.TaskList;

/**
 * Represents a command to search for a term within the task descriptions in the task list.
 */
public class FindCommand extends Command {

    /**
     * Constructs a ListCommand object.
     *
     * @param command The command type.
     * @param argument The argument for the command.
     * @param options Additional options for the command.
     */
    public FindCommand(String command, String argument, HashMap<String, String> options) {
        super(command, argument, options);
    }

    /**
     * Executes the command on the given task list.
     * Find tasks.
     *
     * @param tasks The list of tasks.
     * @return Results of task search as string.
     */
    @Override
    public String execute(TaskList tasks) {

        int counter = 1;
        String result = "";

        List<Task> results = tasks.stream()
                .filter(task -> task.getDescription().contains(argument))
                .toList();

        for (Task task : results) {
            assert task != null : "task is null";
            result = result.concat(counter + "." + task.toString() + "\n");
            counter++;
            assert counter > 0 : "Task counter less than 1";
        }

        return result;

    }
}

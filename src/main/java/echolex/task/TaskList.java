package echolex.task;

import java.util.ArrayList;

/**
 * Represents a list of tasks that extends ArrayList to provide a collection of Task objects.
 */
public class TaskList extends ArrayList<Task> {

    /**
     * Constructs an empty TaskList object.
     * This initializes the task list with no tasks.
     */
    public TaskList() {
        super();
    }

    /**
     * Constructs a TaskList object with the specified list of tasks.
     *
     * @param tasks the list of tasks to initialize the TaskList object with
     */
    public TaskList(ArrayList<Task> tasks) {
        super(tasks);
    }

}

package echolex.task;

/**
 * Represents a task that is categorized as a "to-do" item.
 */
public class Todo extends Task {

    /**
     * Constructs a new Todo object with the specified description and completion status.
     *
     * @param description the description of the to-do task
     * @param isDone the completion status of the to-do task
     */
    public Todo(String description, Boolean isDone) {
        super(description, isDone);
    }

    /**
     * Returns a string representation of the to-do task in a human-readable format.
     * The format includes the task type (T for to-do), completion status, and the task description.
     *
     * @return a string representation of the to-do task
     */
    @Override
    public String toString() {
        return "[T]" + super.toString();
    }

}

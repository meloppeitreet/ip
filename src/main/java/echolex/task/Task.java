package echolex.task;

enum Importance {
    LOW,
    MEDIUM,
    HIGH
}

/**
 * Represents a task with a description, completion status, and importance level.
 */
public class Task {

    protected String description;
    protected boolean isDone;
    private Importance importance;

    /**
     * Constructs a new Task object with the specified description and completion status.
     * The task's importance is initially set to Importance#LOW.
     *
     * @param description the description of the task
     * @param isDone the completion status of the task
     */
    public Task(String description, Boolean isDone) {
        this.description = description;
        this.isDone = isDone;
        this.importance = Importance.LOW;
    }

    /**
     * Returns the description of the task.
     *
     * @return the description of the task
     */
    public String getDescription() {
        return description;
    }

    /**
     * Marks the task as completed by setting the {@code isDone} status to {@code true}.
     */
    public void markDone() {
        isDone = true;
    }

    /**
     * Marks the task as not completed by setting the {@code isDone} status to {@code false}.
     */
    public void unmarkDone() {
        isDone = false;
    }

    /**
     * Increases the importance of the task.
     * If the task's importance is currently Importance#LOW, it will be escalated to Importance#MEDIUM.
     * If the task's importance is currently Importance#MEDIUM, it will be escalated to Importance#HIGH.
     */
    public void escalate() {
        switch (importance) {
        case LOW:
            importance = Importance.MEDIUM;
            break;
        case MEDIUM:
            importance = Importance.HIGH;
            break;
        default:
            break;
        }
    }

    /**
     * Decreases the importance of the task.
     * If the task's importance is currently Importance#HIGH, it will be de-escalated to Importance#MEDIUM.
     * If the task's importance is currently Importance#MEDIUM, it will be de-escalated to Importance#LOW.
     */
    public void deEscalate() {
        switch (importance) {
        case HIGH:
            importance = Importance.MEDIUM;
            break;
        case MEDIUM:
            importance = Importance.LOW;
            break;
        default:
            break;
        }
    }

    /**
     * Returns a string representation of the task in a format suitable for saving to a file.
     * The format includes the task type (T for task), completion status, and description.
     *
     * @return a string representing the task in a saveable format
     */
    public String saveFormat() {
        return "T | " + (isDone ? "1" : "0") + " | " + description;
    }

    /**
     * Returns a string representation of the task in a human-readable format.
     * The format includes the completion status (represented as [X] for done or [ ] for not done)
     * and the task's description.
     *
     * @return a string representation of the task
     */
    @Override
    public String toString() {
        return "[" + (isDone ? "X" : " ") + "] " + description;
    }

}

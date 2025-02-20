package echolex.task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents a task with a specific deadline. The task includes a description, completion status,
 * and the date and time by which the task must be completed.
 */
public class Deadline extends Task {

    private static DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("MMM dd yyyy");
    private static final DateTimeFormatter SAVE_OUTPUT_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    protected LocalDateTime by;

    /**
     * Constructs a new Deadline object with the specified description, completion status, and deadline time.
     *
     * @param description the description of the task
     * @param isDone the completion status of the task
     * @param by the deadline by which the task must be completed
     */
    public Deadline(String description, Boolean isDone, LocalDateTime by) {
        super(description, isDone);
        this.by = by;
    }

    /**
     * Returns the deadline date and time of the task.
     *
     * @return the deadline for the task
     */
    public LocalDateTime getBy() {
        return by;
    }

    /**
     * Returns a string representation of the task in a format suitable for saving to a file.
     * The format includes the task type (D for deadline), completion status, description, and deadline in
     * a saved format of yyyy-MM-dd.
     *
     * @return a string representing the task in a saveable format
     */
    @Override
    public String saveFormat() {
        return "D | " + (isDone ? "1" : "0") + " | " + description + " | " + by.format(SAVE_OUTPUT_FORMATTER);
    }

    /**
     * Returns a string representation of the task in a human-readable format.
     * The format includes the task type, completion status, description, and deadline in a format of
     * MMM dd yyyy.
     *
     * @return a string representation of the task
     */
    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + by.format(outputFormatter) + ")";
    }

}

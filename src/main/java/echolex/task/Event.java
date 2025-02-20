package echolex.task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents an event task with a specific start time and end time.
 * The task includes a description, completion status, and the time window during which the event takes place.
 */
public class Event extends Task {

    private static DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("MMM dd yyyy");
    private static final DateTimeFormatter SAVE_OUTPUT_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    protected LocalDateTime from;
    protected LocalDateTime to;

    /**
     * Constructs a new Event object with the specified description, completion status, start time, and end time.
     *
     * @param description the description of the event
     * @param isDone the completion status of the event
     * @param from the start time of the event
     * @param to the end time of the event
     */
    public Event(String description, Boolean isDone, LocalDateTime from, LocalDateTime to) {
        super(description, isDone);
        this.from = from;
        this.to = to;
    }

    /**
     * Returns the start time of the event.
     *
     * @return the start time of the event
     */
    public LocalDateTime getFrom() {
        return from;
    }

    /**
     * Returns the end time of the event.
     *
     * @return the end time of the event
     */
    public LocalDateTime getTo() {
        return to;
    }

    /**
     * Returns a string representation of the event in a format suitable for saving to a file.
     * The format includes the task type (E for event), completion status, description,
     * and the start and end times in a saved format of yyyy-MM-dd.
     *
     * @return a string representing the event in a saveable format
     */
    @Override
    public String saveFormat() {
        return "E | " + (isDone ? "1" : "0") + " | " + description + " | "
                + from.format(SAVE_OUTPUT_FORMATTER) + " | " + to.format(SAVE_OUTPUT_FORMATTER);
    }

    /**
     * Returns a string representation of the event in a human-readable format.
     * The format includes the task type, completion status, description,
     * and the start and end times in a format of MMM dd yyyy.
     *
     * @return a string representation of the event
     */
    @Override
    public String toString() {
        return "[E]" + super.toString()
                + " (from: " + from.format(outputFormatter) + " to: " + to.format(outputFormatter) + ")";
    }

}

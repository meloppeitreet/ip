package echolex.task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Event extends Task {

    protected LocalDateTime from;
    protected LocalDateTime to;
    private static DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("MMM dd yyyy");
    private static final DateTimeFormatter SAVE_OUTPUT_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public Event(String description, Boolean isDone, LocalDateTime from, LocalDateTime to) {
        super(description, isDone);
        this.from = from;
        this.to = to;
    }

    public LocalDateTime getFrom() {
        return from;
    }

    public LocalDateTime getTo() {
        return to;
    }

    @Override
    public String saveFormat() {
        return "E | " + (isDone ? "1" : "0") + " | " + description + " | "
                + from.format(SAVE_OUTPUT_FORMATTER) + " | " + to.format(SAVE_OUTPUT_FORMATTER);
    }

    @Override
    public String toString() {
        return "[E]" + super.toString()
                + " (from: " + from.format(outputFormatter) + " to: " + to.format(outputFormatter) + ")";
    }

}

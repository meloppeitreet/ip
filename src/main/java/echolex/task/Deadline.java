package echolex.task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Deadline extends Task {

    protected LocalDateTime by;
    private static DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("MMM dd yyyy");
    private static final DateTimeFormatter SAVE_OUTPUT_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public Deadline(String description, Boolean isDone, LocalDateTime by) {
        super(description, isDone);
        this.by = by;
    }

    @Override
    public String saveFormat() {
        return "D | " + (isDone ? "1" : "0") + " | " + description + " | " + by.format(SAVE_OUTPUT_FORMATTER);
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + "(by: " + by.format(outputFormatter) + ")";
    }

}

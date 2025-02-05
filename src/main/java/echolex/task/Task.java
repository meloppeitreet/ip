package echolex.task;

enum Importance {
    LOW,
    MEDIUM,
    HIGH
}

public class Task {

    protected String description;
    protected boolean isDone;
    private Importance importance;

    public Task(String description, Boolean isDone) {
        this.description = description;
        this.isDone = isDone;
        this.importance = Importance.LOW;
    }

    public void markDone() {
        isDone = true;
    }

    public void unmarkDone() {
        isDone = false;
    }

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

    public String saveFormat() {
        return "T | " + (isDone ? "1" : "0") + " | " + description;
    }

    @Override
    public String toString() {
        return "[" + (isDone ? "X" : " ") +  "] " + description;
    }

}

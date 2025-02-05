package echolex.task;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;

public class DeadlineTest {
    private Deadline deadline;
    private LocalDateTime dueDate;

    @BeforeEach
    void setUp() {
        dueDate = LocalDateTime.of(2025, 2, 15, 23, 59);
        deadline = new Deadline("Submit report", false, dueDate);
    }

    @Test
    void testDeadlineConstructor() {
        assertEquals("Submit report", deadline.description);
        assertFalse(deadline.isDone);
        assertEquals(dueDate, deadline.by);
    }

    @Test
    void testMarkDone() {
        deadline.markDone();
        assertTrue(deadline.isDone);
    }

    @Test
    void testUnmarkDone() {
        deadline.markDone(); // Mark done first
        deadline.unmarkDone(); // Then unmark
        assertFalse(deadline.isDone);
    }

    @Test
    void testToString() {
        assertEquals("[D][ ] Submit report(by: Feb 15 2025)", deadline.toString());
        deadline.markDone();
        assertEquals("[D][X] Submit report(by: Feb 15 2025)", deadline.toString());
    }

    @Test
    void testSaveFormat() {
        assertEquals("D | 0 | Submit report | 2025-02-15", deadline.saveFormat());
        deadline.markDone();
        assertEquals("D | 1 | Submit report | 2025-02-15", deadline.saveFormat());
    }
}
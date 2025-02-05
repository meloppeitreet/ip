package echolex.task;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;

public class EventTest {
    private Event event;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    @BeforeEach
    void setUp() {
        startTime = LocalDateTime.of(2025, 3, 10, 14, 0);
        endTime = LocalDateTime.of(2025, 3, 10, 16, 0);
        event = new Event("Team meeting", false, startTime, endTime);
    }

    @Test
    void testEventConstructor() {
        assertEquals("Team meeting", event.description);
        assertFalse(event.isDone);
        assertEquals(startTime, event.from);
        assertEquals(endTime, event.to);
    }

    @Test
    void testMarkDone() {
        event.markDone();
        assertTrue(event.isDone);
    }

    @Test
    void testUnmarkDone() {
        event.markDone(); // Mark done first
        event.unmarkDone(); // Then unmark
        assertFalse(event.isDone);
    }

    @Test
    void testToString() {
        assertEquals("[E][ ] Team meeting(from: Mar 10 2025 to: Mar 10 2025)", event.toString());
        event.markDone();
        assertEquals("[E][X] Team meeting(from: Mar 10 2025 to: Mar 10 2025)", event.toString());
    }

    @Test
    void testSaveFormat() {
        assertEquals("E | 0 | Team meeting | 2025-03-10 | 2025-03-10", event.saveFormat());
        event.markDone();
        assertEquals("E | 1 | Team meeting | 2025-03-10 | 2025-03-10", event.saveFormat());
    }
}

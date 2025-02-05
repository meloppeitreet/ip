package echolex.task;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TodoTest {
    private Todo todo;

    @BeforeEach
    void setUp() {
        todo = new Todo("Finish homework", false);
    }

    @Test
    void testTodoConstructor() {
        assertEquals("Finish homework", todo.description);
        assertFalse(todo.isDone);
    }

    @Test
    void testMarkDone() {
        todo.markDone();
        assertTrue(todo.isDone);
    }

    @Test
    void testUnmarkDone() {
        todo.markDone(); // Mark done first
        todo.unmarkDone(); // Then unmark
        assertFalse(todo.isDone);
    }

    @Test
    void testToString() {
        assertEquals("[T][ ] Finish homework", todo.toString());
        todo.markDone();
        assertEquals("[T][X] Finish homework", todo.toString());
    }

    @Test
    void testSaveFormat() {
        assertEquals("T | 0 | Finish homework", todo.saveFormat());
        todo.markDone();
        assertEquals("T | 1 | Finish homework", todo.saveFormat());
    }
}

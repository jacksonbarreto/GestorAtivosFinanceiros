package test;

import model.Log;
import org.junit.jupiter.api.Test;

import static model.Operation.CHANGED_PASSWORD;
import static model.Operation.CHANGED_USERNAME;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class LogTest {
    Log log1 = new Log(CHANGED_PASSWORD);
    Log log2 = new Log(CHANGED_PASSWORD);
    Log log3 = new Log(CHANGED_USERNAME);

    @Test
    void testGetOperation() {
        assertEquals(CHANGED_PASSWORD, log1.getOperation());
    }

    @Test
    void testHashCode() {
        assertNotEquals(log1.hashCode(), log3.hashCode());
        assertEquals(log1.getMoment(), log2.getMoment());
        assertEquals(log1.hashCode(), log2.hashCode());
    }

    @Test
    void testEquals() {
        assertNotEquals(log1, log3);
        assertEquals(log1, log2);
    }
}

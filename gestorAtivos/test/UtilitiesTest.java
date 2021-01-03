package test;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static model.Utilities.*;
import static org.junit.jupiter.api.Assertions.*;

public class UtilitiesTest {
    @Test
    void dateIsInThePeriodTest() {
        LocalDate initialDate = LocalDate.now();
        LocalDate finalDate = LocalDate.now();
        LocalDate dateToCheck = LocalDate.now();
        LocalDate dateNull = null;

        assertFalse(dateIsInThePeriod(initialDate.minusDays(1), finalDate, dateToCheck.plusMonths(1)));
        assertTrue(dateIsInThePeriod(initialDate.plusMonths(1), finalDate, dateToCheck));
        assertTrue(dateIsInThePeriod(initialDate, finalDate, dateToCheck));
        assertThrows(IllegalArgumentException.class, () -> dateIsInThePeriod(dateNull, finalDate, dateToCheck));
        assertThrows(IllegalArgumentException.class, () -> dateIsInThePeriod(initialDate, dateNull, dateToCheck));
        assertThrows(IllegalArgumentException.class, () -> dateIsInThePeriod(initialDate, finalDate, dateNull));

    }

    @Test
    void testDateIsBeforeOrEqual() {
        LocalDate milestoneDate = LocalDate.now();
        LocalDate dateToCheck = LocalDate.now();
        final LocalDate dateNull = null;
        assertTrue(dateIsBeforeOrEqual(milestoneDate, dateToCheck));
        dateToCheck = dateToCheck.minusDays(1L);
        assertTrue(dateIsBeforeOrEqual(milestoneDate, dateToCheck));
        dateToCheck = dateToCheck.plusDays(2L);
        assertFalse(dateIsBeforeOrEqual(milestoneDate, dateToCheck));
        assertThrows(IllegalArgumentException.class, () -> dateIsBeforeOrEqual(dateNull, LocalDate.now()));
        assertThrows(IllegalArgumentException.class, () -> dateIsBeforeOrEqual(LocalDate.now(), dateNull));
    }

    @Test
    void testDateIsAfterOrEqual() {
        LocalDate milestoneDate = LocalDate.now();
        LocalDate dateToCheck = LocalDate.now();
        final LocalDate dateNull = null;
        assertTrue(dateIsAfterOrEqual(milestoneDate, dateToCheck));
        dateToCheck = dateToCheck.plusDays(1L);
        assertTrue(dateIsAfterOrEqual(milestoneDate, dateToCheck));

        dateToCheck = dateToCheck.minusDays(2L);
        assertFalse(dateIsAfterOrEqual(milestoneDate, dateToCheck));

        assertThrows(IllegalArgumentException.class, () -> dateIsAfterOrEqual(dateNull, LocalDate.now()));
        assertThrows(IllegalArgumentException.class, () -> dateIsAfterOrEqual(LocalDate.now(), dateNull));
    }
}

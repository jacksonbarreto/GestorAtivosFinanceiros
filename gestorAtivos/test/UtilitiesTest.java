package test;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;

import static model.Utilities.dateIsInThePeriod;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UtilitiesTest {
    @Test
    void dateIsInThePeriodTest(){
        LocalDate initialDate = LocalDate.now();
        LocalDate finalDate = LocalDate.now();
        LocalDate dateToCheck = LocalDate.now();

        assertFalse(dateIsInThePeriod(initialDate.minusDays(1),finalDate,dateToCheck.plusMonths(1)));
        assertTrue(dateIsInThePeriod(initialDate.plusMonths(1),finalDate,dateToCheck));
        assertTrue(dateIsInThePeriod(initialDate,finalDate,dateToCheck));

    }
}

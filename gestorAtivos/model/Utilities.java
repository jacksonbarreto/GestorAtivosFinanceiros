package model;

import java.time.LocalDate;

final public class Utilities {
    private Utilities(){
    }

    static public boolean dateIsInThePeriod(LocalDate initialDate, LocalDate finalDate, LocalDate dateToCheck){
        LocalDate temp;
        if (initialDate.isAfter(finalDate)) {
            temp = finalDate;
            finalDate = initialDate;
            initialDate = temp;
        }
        return  (
                (dateToCheck.isAfter(initialDate) ||
                        dateToCheck.isEqual(initialDate)) &&
                        (dateToCheck.isBefore(finalDate) ||
                                dateToCheck.isEqual(finalDate))
        );
    }
}

package model;

import java.security.SecureRandom;
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

    static public byte[] getSalt(){
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        return salt;
    }
}

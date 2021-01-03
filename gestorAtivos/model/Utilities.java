package model;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.time.LocalDate;

final public class Utilities {
    private Utilities(){
    }

    static public boolean dateIsInThePeriod(LocalDate initialDate, LocalDate finalDate, LocalDate dateToCheck){
        if (initialDate == null || finalDate == null || dateToCheck == null)
            throw new IllegalArgumentException();

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

    static public boolean dateIsBeforeOrEqual(LocalDate milestoneDate, LocalDate dateToCheck){
        if (milestoneDate == null || dateToCheck == null)
            throw new IllegalArgumentException();

        return  ((dateToCheck.isBefore(milestoneDate) || dateToCheck.isEqual(milestoneDate)));
    }

    static public boolean dateIsAfterOrEqual(LocalDate milestoneDate, LocalDate dateToCheck){
        if (milestoneDate == null || dateToCheck == null)
            throw new IllegalArgumentException();

        return  ((dateToCheck.isAfter(milestoneDate) || dateToCheck.isEqual(milestoneDate)));
    }

    static public byte[] getSaltRandom(){
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        return salt;
    }

    static public String getHashedPassword(String password, byte[] salt){
        String hexHashedPassword = null;
        StringBuilder hexString = new StringBuilder();
        KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 65536, 128);
        SecretKeyFactory factory;

        try {
            factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            byte[] hashedPassword = factory.generateSecret(spec).getEncoded();
            for(byte b : hashedPassword){
                hexString.append(String.format("%02X", 0xFF & b));
            }
            hexHashedPassword = hexString.toString();
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            e.printStackTrace();
        }

        return hexHashedPassword;
    }
}

package test;


import model.Bank;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class BankTest {

    @Test
    void attributeVerification(){
        Bank bank = new Bank("Millenium");
        bank.setId(2L);
        assertEquals("Millenium", bank.getName());
        assertEquals(2L, bank.getId());
    }


}

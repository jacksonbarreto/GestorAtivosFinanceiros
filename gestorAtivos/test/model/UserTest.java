package test.model;

import model.*;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;

import static model.AssetType.*;
import static model.LogicalOperator.*;
import static model.User.filterByAmountInvested;
import static model.UserType.*;
import static org.junit.jupiter.api.Assertions.*;

public class UserTest {

    User user1 = new User("Carlos Caetano", "123456", ROOT);
    User user2 = new User("Carlos Caetano", "123456", ROOT);
    User user3 = user1;
    InvestmentFund investmentFund1 = new InvestmentFund(2,
            new BigDecimal("0.15"),
            "Fundo Petro4",
            new BigDecimal("1500.78"),
            new BigDecimal("0.12"));
    RentalProperty rentalProperty1 = new RentalProperty(5,
            new BigDecimal("0.15"),
            "Casa em Viana",
            new BigDecimal("158000"),
            new BigDecimal("450.70"),
            new BigDecimal("63.50"),
            new BigDecimal("28.90"),
            "Rua Nogueira de Melo, 10, 3º Direito");
    RentalProperty rentalProperty2 = new RentalProperty(12,
            new BigDecimal("0.20"),
            "Casa de Campo - Barcelos",
            new BigDecimal("450000"),
            new BigDecimal("750"),
            new BigDecimal("0"),
            new BigDecimal("35"),
            "Rua da Capela Grande, 95 - Barcelos");
    Bank bank1 = new Bank("Millenum");
    TermDeposit termDeposit1 = new TermDeposit(5,
            new BigDecimal("0.15"),
            "Casa de Praia",
            new BigDecimal("5000"),
            new BigDecimal("0.24"),
            "049AB7859",
            bank1);

    @Test
    void creationWithArgumentsNullTest() {
        String stringNull = null;
        assertThrows(IllegalArgumentException.class, () -> new User(stringNull, stringNull, UserType.SIMPLE));
        assertThrows(IllegalArgumentException.class, () -> new User("PedroCabral", stringNull, UserType.SIMPLE));
        assertThrows(IllegalArgumentException.class, () -> new User(stringNull, "abc123", UserType.SIMPLE));
    }

    @Test
    void creationWithArgumentsSmallTest() {
        assertThrows(IllegalArgumentException.class, () -> new User("jbc", "abc", UserType.SIMPLE));
        assertThrows(IllegalArgumentException.class, () -> new User("Mauro Caetano", "abc", UserType.SIMPLE));
        assertThrows(IllegalArgumentException.class, () -> new User("jbc", "$12fgon", UserType.SIMPLE));
    }

    @Test
    void creationWithEmptyArgumentsTest() {
        String stringNull = null;
        assertThrows(IllegalArgumentException.class, () -> new User(stringNull, "", UserType.SIMPLE));
        assertThrows(IllegalArgumentException.class, () -> new User("", "", UserType.SIMPLE));
        assertThrows(IllegalArgumentException.class, () -> new User("jbc", "", UserType.SIMPLE));
        assertThrows(IllegalArgumentException.class, () -> new User("Paulo Mordaz", "", UserType.SIMPLE));
        assertThrows(IllegalArgumentException.class, () -> new User("", "ijerrf", UserType.SIMPLE));
    }

    @Test
    void
    testSetUsername() {
        String stringNull = null;
        assertThrows(IllegalArgumentException.class, () -> user1.changeUsername(""));
        assertThrows(IllegalArgumentException.class, () -> user1.changeUsername("jtr"));
        assertThrows(IllegalArgumentException.class, () -> user1.changeUsername(stringNull));
        user1.changeUsername("Mateus Rocha");
        assertEquals("Mateus Rocha", user1.getUsername());
    }

    @Test
    void testSetPassword() {
        String stringNull = null;
        assertThrows(IllegalArgumentException.class, () -> user1.changePassword(""));
        assertThrows(IllegalArgumentException.class, () -> user1.changePassword("iks"));
        assertThrows(IllegalArgumentException.class, () -> user1.changePassword(stringNull));
        assertNotEquals(user1.getPassword(), user2.getPassword());
    }

    @Test
    void testGetUserType() {
        assertEquals(ROOT, user1.getUserType());
    }

    @Test
    void testSetUserType() {
        UserType userType = null;
        assertThrows(IllegalArgumentException.class, () -> user1.changeUserType(userType));
        user1.changeUserType(SIMPLE);
        assertEquals(SIMPLE, user1.getUserType());
        user1.changeUserType(MANAGER);
        assertEquals(MANAGER, user1.getUserType());
    }

    @Test
    void testLog() {
        user1.changeUserType(SIMPLE);
        user1.changeUserType(MANAGER);
        assertEquals(3, user1.getLogs().size());
    }

    @Test
    void testEquals() {
        assertNotEquals(user1, user2);
        assertEquals(user1, user1);
        assertEquals(user1, user3);
    }

    @Test
    void testHashCode() {
        assertNotEquals(user1.hashCode(), user2.hashCode());
        assertEquals(user1.hashCode(), user3.hashCode());
    }

    @Test
    void testAddAssetFinancial() {
        TermDeposit termDepositNull = null;
        assertThrows(IllegalArgumentException.class, () -> user1.addAssetFinancial(termDepositNull));
        user1.addAssetFinancial(termDeposit1);
        user1.addAssetFinancial(investmentFund1);
        user1.addAssetFinancial(rentalProperty1);
        assertEquals(3, user1.getFinancialAssets().size());
        assertEquals(4, user1.getLogs().size());
    }

    @Test
    void testFindFinancialAssetByType() {
        user1.addAssetFinancial(termDeposit1);
        user1.addAssetFinancial(investmentFund1);
        user1.addAssetFinancial(rentalProperty1);
        user1.addAssetFinancial(rentalProperty2);
        assertEquals(1, user1.findFinancialAsset(FOUND).size());
        assertEquals(1, user1.findFinancialAsset(DEPOSIT).size());
        assertEquals(2, user1.findFinancialAsset(PROPERTY).size());
    }

    @Test
    void testFindFinancialAssetByName() {
        String stringNull = null;
        user1.addAssetFinancial(termDeposit1);
        user1.addAssetFinancial(investmentFund1);
        user1.addAssetFinancial(rentalProperty1);
        user1.addAssetFinancial(rentalProperty2);
        assertEquals(1, user1.findFinancialAsset("Fundo Petro4").size());
        assertEquals(1, user1.findFinancialAsset("Praia").size());
        assertEquals(1, user1.findFinancialAsset("praia").size());
        assertEquals(3, user1.findFinancialAsset("Casa").size());
        assertThrows(IllegalArgumentException.class, () -> user1.findFinancialAsset(""));
        assertThrows(IllegalArgumentException.class, () -> user1.findFinancialAsset("dfg"));
        assertThrows(IllegalArgumentException.class, () -> user1.findFinancialAsset(stringNull));
    }

    @Test
    void testFindFinancialAssetByNameAndType() {
        String stringNull = null;
        user1.addAssetFinancial(termDeposit1);
        user1.addAssetFinancial(investmentFund1);
        user1.addAssetFinancial(rentalProperty1);
        user1.addAssetFinancial(rentalProperty2);
        assertEquals(1, user1.findFinancialAsset("Fundo Petro4", FOUND).size());
        assertEquals(0, user1.findFinancialAsset("Fundo Petro4", DEPOSIT).size());
        assertEquals(1, user1.findFinancialAsset("Praia", DEPOSIT).size());
        assertEquals(0, user1.findFinancialAsset("praia", FOUND).size());
        assertEquals(2, user1.findFinancialAsset("Casa", PROPERTY).size());
        assertEquals(1, user1.findFinancialAsset("Casa", DEPOSIT).size());
        assertThrows(IllegalArgumentException.class, () -> user1.findFinancialAsset(""));
        assertThrows(IllegalArgumentException.class, () -> user1.findFinancialAsset("dfg"));
        assertThrows(IllegalArgumentException.class, () -> user1.findFinancialAsset(stringNull));
    }

    @Test
    void testFilterByAmountInvested() {
        user1.addAssetFinancial(termDeposit1);
        user1.addAssetFinancial(investmentFund1);
        user1.addAssetFinancial(rentalProperty1);
        user1.addAssetFinancial(rentalProperty2);
        assertEquals(1, filterByAmountInvested(user1.getFinancialAssets(), EQUAL, new BigDecimal("5000")).size());
        assertEquals(2, filterByAmountInvested(user1.getFinancialAssets(), BIGGER_OR_EQUAL, new BigDecimal("1500.78")).size());
        assertEquals(1, filterByAmountInvested(user1.getFinancialAssets(), BIGGER_THEN, new BigDecimal("1500.78")).size());
        assertEquals(0, filterByAmountInvested(user1.getFinancialAssets(), LESS_THAN, new BigDecimal("1500.78")).size());
        assertEquals(1, filterByAmountInvested(user1.getFinancialAssets(), LESS_OR_EQUAL, new BigDecimal("1500.78")).size());
        assertThrows(IllegalArgumentException.class, () -> filterByAmountInvested(user1.getFinancialAssets(), LESS_OR_EQUAL, new BigDecimal("-333.98")));
        assertThrows(IllegalArgumentException.class, () -> filterByAmountInvested(user1.getFinancialAssets(), LESS_OR_EQUAL, new BigDecimal("0")));
        ArrayList<FinancialAsset> financialAssets = null;
        assertThrows(IllegalArgumentException.class, () -> filterByAmountInvested(financialAssets, LESS_OR_EQUAL, new BigDecimal("876.38")));
    }

    @Test
    void testGetFinancialAssetsActive() {
        user1.addAssetFinancial(termDeposit1);
        user1.addAssetFinancial(investmentFund1);
        user1.addAssetFinancial(rentalProperty1);
        user1.addAssetFinancial(rentalProperty2);
        termDeposit1.changeStartDate(LocalDate.now().plusMonths(9L));

        assertEquals(3, user1.getFinancialAssetsActive(LocalDate.now(), LocalDate.now().plusMonths(2L)).size());
        termDeposit1.changeStartDate(LocalDate.now().plusDays(1L));
        assertEquals(3, user1.getFinancialAssetsActive(LocalDate.now(), LocalDate.now().plusMonths(2L)).size());
    }

    @Test
    void testGetFinancialAssetsreverseOrderActive() {
        user1.addAssetFinancial(termDeposit1);
        user1.addAssetFinancial(investmentFund1);
        user1.addAssetFinancial(rentalProperty1);
        user1.addAssetFinancial(rentalProperty2);
        termDeposit1.changeStartDate(LocalDate.now().plusMonths(9L));
        assertEquals(3, user1.getFinancialAssetsreverseOrderActive().size());
        assertEquals(0, ((AssetWithInvestedValue) user1.getFinancialAssetsreverseOrderActive().get(0)).getAmountInvested().compareTo(new BigDecimal("450000")));
    }
}

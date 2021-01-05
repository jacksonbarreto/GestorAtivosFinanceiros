package test.model;

import model.RentalProperty;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class RentalPropertyTest {
    RentalProperty rentalProperty1 = new RentalProperty(5,
            new BigDecimal("0.15"),
            "Apartamento em Viana",
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

    RentalProperty rentalProperty3 = new RentalProperty(5,
            new BigDecimal("0.15"),
            "Apartamento em Viana",
            new BigDecimal("158000"),
            new BigDecimal("450.70"),
            new BigDecimal("63.50"),
            new BigDecimal("28.90"),
            "Rua Nogueira de Melo, 10, 3º Direito");

    @Test
    void equalsTest() {
        assertEquals(rentalProperty3, rentalProperty1);
        rentalProperty1.setStartDate(LocalDate.now().plusMonths(1L));
        assertNotSame(rentalProperty3, rentalProperty1);
    }

    @Test
    void hashCodeTest() {
        assertEquals(rentalProperty1.hashCode(), rentalProperty3.hashCode());
        rentalProperty1.setRentAmount(new BigDecimal("725.48"));
        assertNotEquals(rentalProperty1.hashCode(), rentalProperty3.hashCode());
    }

    @Test
    void setPropertyValueTest() {
        BigDecimal propertyValueNull = null;
        assertThrows(IllegalArgumentException.class, () -> rentalProperty1.setPropertyValue(new BigDecimal("0")));
        assertThrows(IllegalArgumentException.class, () -> rentalProperty1.setPropertyValue(new BigDecimal("-23")));
        assertThrows(IllegalArgumentException.class, () -> rentalProperty1.setPropertyValue(propertyValueNull));
        rentalProperty1.setPropertyValue(new BigDecimal("245000.35"));
        assertEquals(new BigDecimal("245000.35"), rentalProperty1.getPropertyValue());
    }

    @Test
    void setRentAmountTest() {
        BigDecimal rentAmountNull = null;
        assertThrows(IllegalArgumentException.class, () -> rentalProperty1.setRentAmount(new BigDecimal("0")));
        assertThrows(IllegalArgumentException.class, () -> rentalProperty1.setRentAmount(new BigDecimal("-453.99")));
        assertThrows(IllegalArgumentException.class, () -> rentalProperty1.setRentAmount(rentAmountNull));
        rentalProperty1.setRentAmount(new BigDecimal("678.49"));
        assertEquals(new BigDecimal("678.49"), rentalProperty1.getRentAmount());
    }

    @Test
    void setAnnualAmountOtherExpensesTest() {
        BigDecimal annualAmountOtherExpensesNull = null;
        assertThrows(IllegalArgumentException.class, () -> rentalProperty1.setAnnualAmountOtherExpenses(new BigDecimal("-178.62")));
        assertThrows(IllegalArgumentException.class, () -> rentalProperty1.setAnnualAmountOtherExpenses(annualAmountOtherExpensesNull));
        rentalProperty1.setAnnualAmountOtherExpenses(new BigDecimal("29.77"));
        assertEquals(new BigDecimal("29.77"), rentalProperty1.getAnnualAmountOtherExpenses());
    }

    @Test
    void setTaxTest() {
        BigDecimal taxNull = null;
        assertThrows(IllegalArgumentException.class, () -> rentalProperty1.DefineNewTax(taxNull));
    }

    @Test
    void setDesignationShortTest() {
        assertThrows(IllegalArgumentException.class, () -> rentalProperty1.setDesignation("xpt"));
    }

    @Test
    void setMonthlyCostCondominiumTest() {
        BigDecimal monthlyCostCondominiumNull = null;
        assertThrows(IllegalArgumentException.class, () -> rentalProperty1.setMonthlyCostCondominium(new BigDecimal("-191.73")));
        assertThrows(IllegalArgumentException.class, () -> rentalProperty1.setMonthlyCostCondominium(monthlyCostCondominiumNull));
        rentalProperty1.setMonthlyCostCondominium(new BigDecimal("123.97"));
        assertEquals(new BigDecimal("123.97"), rentalProperty1.getMonthlyCostCondominium());
    }

    @Test
    void setLocationTest() {
        String stringNull = null;
        assertThrows(IllegalArgumentException.class, () -> rentalProperty1.setLocation(stringNull));
        assertThrows(IllegalArgumentException.class, () -> rentalProperty1.setLocation(""));
        assertThrows(IllegalArgumentException.class, () -> rentalProperty1.setLocation("1aP"));
        rentalProperty1.setLocation("Rua verde, 23");
        assertEquals("Rua verde, 23", rentalProperty1.getLocation());
    }

    @Test
    void createPaymentsTest() {
        assertEquals(5, rentalProperty1.getPayments().size());
        assertEquals(12, rentalProperty2.getPayments().size());
        assertEquals(LocalDate.now().plusMonths(3L), rentalProperty1.getPayments().get(2).getDateOfPayment());
    }

    @Test
    void setDurationTest() {
        assertThrows(IllegalArgumentException.class, () -> rentalProperty1.setDuration(0));
        assertThrows(IllegalArgumentException.class, () -> rentalProperty1.setDuration(-1));
    }

    @Test
    void setStartDateTest() {
        LocalDate dateNull = null;
        assertThrows(IllegalArgumentException.class, () -> rentalProperty1.setStartDate(dateNull));
    }

    @Test
    void creationPropertyValueNull() {
        BigDecimal valueNull = null;
        assertThrows(IllegalArgumentException.class, () -> new RentalProperty(5,
                new BigDecimal("0.15"),
                "Apartamento em Viana",
                valueNull,
                new BigDecimal("450.70"),
                new BigDecimal("63.50"),
                new BigDecimal("28.90"),
                "Rua Nogueira de Melo, 10, 3º Direito"));
    }

    @Test
    void creationRentAmountNull() {
        BigDecimal valueNull = null;
        assertThrows(IllegalArgumentException.class, () -> new RentalProperty(5,
                new BigDecimal("0.15"),
                "Apartamento em Viana",
                new BigDecimal("158000"),
                valueNull,
                new BigDecimal("63.50"),
                new BigDecimal("28.90"),
                "Rua Nogueira de Melo, 10, 3º Direito"));
    }

    @Test
    void creationMonthlyCostCondominiumNull() {
        BigDecimal valueNull = null;
        assertThrows(IllegalArgumentException.class, () -> new RentalProperty(5,
                new BigDecimal("0.15"),
                "Apartamento em Viana",
                new BigDecimal("158000"),
                new BigDecimal("450.70"),
                valueNull,
                new BigDecimal("28.90"),
                "Rua Nogueira de Melo, 10, 3º Direito"));
    }

    @Test
    void creationAnnualAmountOtherExpensesNull() {
        BigDecimal valueNull = null;
        assertThrows(IllegalArgumentException.class, () -> new RentalProperty(5,
                new BigDecimal("0.15"),
                "Apartamento em Viana",
                new BigDecimal("158000"),
                new BigDecimal("450.70"),
                new BigDecimal("63.50"),
                valueNull,
                "Rua Nogueira de Melo, 10, 3º Direito"));
    }

    @Test
    void creationTaxNull() {
        BigDecimal valueNull = null;
        assertThrows(IllegalArgumentException.class, () -> new RentalProperty(5,
                valueNull,
                "Apartamento em Viana",
                new BigDecimal("158000"),
                new BigDecimal("450.70"),
                new BigDecimal("63.50"),
                new BigDecimal("28.90"),
                "Rua Nogueira de Melo, 10, 3º Direito"));
    }

    @Test
    void CreationTest() {
        String stringNull = null;
        assertThrows(IllegalArgumentException.class, () -> new RentalProperty(5,
                new BigDecimal("0.15"),
                "Apartamento em Viana",
                new BigDecimal("000"),
                new BigDecimal("450.70"),
                new BigDecimal("63.50"),
                new BigDecimal("28.90"),
                "Rua Nogueira de Melo, 10, 3º Direito"));

        assertThrows(IllegalArgumentException.class, () -> new RentalProperty(5,
                new BigDecimal("0.15"),
                "Apartamento em Viana",
                new BigDecimal("-234000"),
                new BigDecimal("450.70"),
                new BigDecimal("63.50"),
                new BigDecimal("28.90"),
                "Rua Nogueira de Melo, 10, 3º Direito"));

        assertThrows(IllegalArgumentException.class, () -> new RentalProperty(5,
                new BigDecimal("0.15"),
                "Apartamento em Viana",
                new BigDecimal("4000"),
                new BigDecimal("000000"),
                new BigDecimal("63.50"),
                new BigDecimal("28.90"),
                "Rua Nogueira de Melo, 10, 3º Direito"));

        assertThrows(IllegalArgumentException.class, () -> new RentalProperty(5,
                new BigDecimal("0.15"),
                "Apartamento em Viana",
                new BigDecimal("4000"),
                new BigDecimal("-670"),
                new BigDecimal("63.50"),
                new BigDecimal("28.90"),
                "Rua Nogueira de Melo, 10, 3º Direito"));

        assertThrows(IllegalArgumentException.class, () -> new RentalProperty(5,
                new BigDecimal("0.15"),
                "Apartamento em Viana",
                new BigDecimal("4000"),
                new BigDecimal("970"),
                new BigDecimal("-345"),
                new BigDecimal("28.90"),
                "Rua Nogueira de Melo, 10, 3º Direito"));

        assertThrows(IllegalArgumentException.class, () -> new RentalProperty(5,
                new BigDecimal("0.15"),
                "Apartamento em Viana",
                new BigDecimal("4000"),
                new BigDecimal("970"),
                new BigDecimal("345"),
                new BigDecimal("-98.90"),
                "Rua Nogueira de Melo, 10, 3º Direito"));

        assertThrows(IllegalArgumentException.class, () -> new RentalProperty(5,
                new BigDecimal("0.15"),
                "",
                new BigDecimal("4000"),
                new BigDecimal("970"),
                new BigDecimal("345"),
                new BigDecimal("28.90"),
                "Rua Nogueira de Melo, 10, 3º Direito"));

        assertThrows(IllegalArgumentException.class, () -> new RentalProperty(5,
                new BigDecimal("0.15"),
                "Roof Top de matosinho",
                new BigDecimal("4000"),
                new BigDecimal("970"),
                new BigDecimal("345"),
                new BigDecimal("62.90"),
                ""));
        assertThrows(IllegalArgumentException.class, () -> new RentalProperty(5,
                new BigDecimal("0.15"),
                "Roof Top de matosinho",
                new BigDecimal("4000"),
                new BigDecimal("970"),
                new BigDecimal("345"),
                new BigDecimal("62.90"),
                stringNull));
        assertThrows(IllegalArgumentException.class, () -> new RentalProperty(5,
                new BigDecimal("0.15"),
                stringNull,
                new BigDecimal("4000"),
                new BigDecimal("970"),
                new BigDecimal("345"),
                new BigDecimal("62.90"),
                "Rua do pinheiro Manso, 874"));
        assertThrows(IllegalArgumentException.class, () -> new RentalProperty(5,
                new BigDecimal("0.15"),
                stringNull,
                new BigDecimal("4000"),
                new BigDecimal("970"),
                new BigDecimal("345"),
                new BigDecimal("62.90"),
                "Rua do pinheiro Manso, 874"));

        assertThrows(IllegalArgumentException.class, () -> new RentalProperty(5,
                new BigDecimal("0.15"),
                "abc",
                new BigDecimal("4000"),
                new BigDecimal("970"),
                new BigDecimal("345"),
                new BigDecimal("62.90"),
                "Rua do pinheiro Manso, 874"));
        assertThrows(IllegalArgumentException.class, () -> new RentalProperty(5,
                new BigDecimal("0.15"),
                "longo nome",
                new BigDecimal("4000"),
                new BigDecimal("970"),
                new BigDecimal("345"),
                new BigDecimal("62.90"),
                "xpt"));
    }

}

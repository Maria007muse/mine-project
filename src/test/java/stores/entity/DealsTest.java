package stores.entity;

import static org.junit.jupiter.api.Assertions.*;
import jakarta.validation.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import stores.entity.Deals;

public class DealsTest {

    private static Validator validator;

    @BeforeAll
    public static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testDealsValidation() {
        Deals deal = new Deals();
        deal.setTicker("ABCD");
        deal.setOrderNumber("123456");
        deal.setDealNumber("123456");
        deal.setDealQuantity(5000);
        deal.setDealPrice(10.0);
        deal.setDealTotalCost(50000.0);
        deal.setDealTrader("Trader123");
        deal.setDealCommission(100.0);

        assertEquals(0, validator.validate(deal).size());
    }

    @Test
    public void testInvalidDealsValidation() {
        Deals deal = new Deals();
        assertEquals(8, validator.validate(deal).size());
    }
}


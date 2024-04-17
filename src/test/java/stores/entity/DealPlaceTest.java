package stores.entity;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeAll;
import jakarta.validation.*;
import stores.entity.DealPlace;

import java.util.Set;

public class DealPlaceTest {

    private static Validator validator;

    @BeforeAll
    public static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testValidDealPlace() {
        DealPlace dealPlace = new DealPlace();
        dealPlace.setDealPlaceFull("Full Place");
        dealPlace.setDealPlaceShort("Short Place");

        assertEquals(0, validator.validate(dealPlace).size());
    }

    @Test
    public void testInvalidDealPlacePattern() {
        DealPlace dealPlace = new DealPlace();
        dealPlace.setDealPlaceFull("FullPlace@");
        dealPlace.setDealPlaceShort("ShortPlace");

        Set<ConstraintViolation<DealPlace>> violations = validator.validate(dealPlace);
        assertFalse(violations.isEmpty(), "Expected violations for invalid pattern");
    }
}

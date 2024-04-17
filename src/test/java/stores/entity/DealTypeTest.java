package stores.entity;

import static org.junit.jupiter.api.Assertions.assertFalse;
import jakarta.validation.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import stores.entity.DealType;

import java.util.Set;

public class DealTypeTest {
    private Validator validator;
    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }
    @Test
    public void testInvalidType() {
        DealType dealType = new DealType();
        dealType.setType("");

        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<DealType>> violations = validator.validate(dealType);
        assertFalse(violations.isEmpty(), "Expected violations for invalid ticker");
    }
}



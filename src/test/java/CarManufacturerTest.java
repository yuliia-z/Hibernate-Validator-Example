import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CarManufacturerTest {

    private Validator validator;

    @Test
    public void manufacturerIsNull_Reject() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();

        Car car = new Car( null, "DD-AB-123", 4 );

        Set<ConstraintViolation<Car>> constraintViolations =
                validator.validate( car );

        System.out.println(constraintViolations.iterator().next().getPropertyPath() + " " + constraintViolations.iterator().next().getMessage());

        assertEquals( 1, constraintViolations.size() );
        assertEquals(
                "may not be null",
                constraintViolations.iterator().next().getMessage()
        );
    }
}

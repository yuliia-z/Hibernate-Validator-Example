import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static org.junit.Assert.assertEquals;

/**
 * Created by yuliia on 13.04.17.
 */
public class CarConstraintAnnotationTest {
    private Validator validator;

    @Test
    public void invalidLicensePlate() {

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();


        //invalid license plate
        Car car = new Car( "Morris", "dd-ab-123", 4 );
        Set<ConstraintViolation<Car>> constraintViolations =
                validator.validate( car );

        System.out.println(constraintViolations.iterator().next().getMessage());
        assertEquals( 1, constraintViolations.size() );
        assertEquals(
                "Case mode must be UPPER.",
                constraintViolations.iterator().next().getMessage()
        );

        //valid license plate
        car = new Car( "Morris", "DD-AB-123", 4 );

        constraintViolations = validator.validate( car );

        assertEquals( 0, constraintViolations.size() );
    }
}

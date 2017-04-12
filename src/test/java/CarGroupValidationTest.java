import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CarGroupValidationTest {

    private Validator validator;

    @Test
    public void groupValidation_Reject() {

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();

        Car car = new Car( "Morris", "DD-AB-123", 2 );

        Set<ConstraintViolation<Car>> constraintViolations = validator.validate( car );
        assertEquals( 0, constraintViolations.size() );

        constraintViolations = validator.validate( car, CarChecks.class );
        System.out.println(constraintViolations.iterator().next().getPropertyPath() + " " + constraintViolations.iterator().next().getMessage());

        assertEquals( 1, constraintViolations.size() );
        assertEquals(
                "The car has to pass the vehicle inspection first",
                constraintViolations.iterator().next().getMessage()
        );

        car.setPassedVehicleInspection( true );
        assertEquals( 0, validator.validate( car, CarChecks.class ).size() );

        Driver john = new Driver( "John Doe" );
        john.setAge( 18 );
        john.setPhone("0937846322");
        car.setDriver( john );

        constraintViolations = validator.validate( car, DriverChecks.class );
        System.out.println(constraintViolations.iterator().next().getPropertyPath() + " " + constraintViolations.iterator().next().getMessage());
        assertEquals( 1, constraintViolations.size() );
        assertEquals(
                "You first have to pass the driving test",
                constraintViolations.iterator().next().getMessage()
        );

    }
}

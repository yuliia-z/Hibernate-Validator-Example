import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.groups.Default;

import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CarTest {

    private static Validator validator;

    @BeforeClass
    public static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void manufacturerIsNull_Reject() {
        Car car = new Car( null, "DD-AB-123", 4 );

        Set<ConstraintViolation<Car>> constraintViolations =
                validator.validate( car );

        assertEquals( 1, constraintViolations.size() );
        assertEquals(
                "may not be null",
                constraintViolations.iterator().next().getMessage()
        );
    }

    @Test
    public void licensePlateTooShort_Reject() {
        Car car = new Car( "Morris", "D", 4 );

        Set<ConstraintViolation<Car>> constraintViolations =
                validator.validate( car );

        assertEquals( 1, constraintViolations.size() );
        assertEquals(
                "size must be between 2 and 14",
                constraintViolations.iterator().next().getMessage()
        );
    }

    @Test
    public void seatCountTooLow_Reject() {
        Car car = new Car( "Morris", "DD-AB-123", 1 );

        Set<ConstraintViolation<Car>> constraintViolations =
                validator.validate( car );

        assertEquals( 1, constraintViolations.size() );
        assertEquals(
                "must be greater than or equal to 2",
                constraintViolations.iterator().next().getMessage()
        );
    }

    @Test
    public void carIsValid_Reject() {
        Car car = new Car( "Morris", "DD-AB-123", 2 );

        Set<ConstraintViolation<Car>> constraintViolations =
                validator.validate( car );

        assertEquals( 0, constraintViolations.size() );
    }

    @Test
    public void groupValidation_Reject() {
        Car car = new Car( "Morris", "DD-AB-123", 2 );
        Set<ConstraintViolation<Car>> constraintViolations = validator.validate( car );
        assertEquals( 0, constraintViolations.size() );

        constraintViolations = validator.validate( car, CarChecks.class );
        assertEquals( 1, constraintViolations.size() );
        assertEquals(
                "The car has to pass the vehicle inspection first",
                constraintViolations.iterator().next().getMessage()
        );

        car.setPassedVehicleInspection( true );
        assertEquals( 0, validator.validate( car, CarChecks.class ).size() );

        Driver john = new Driver( "John Doe" );
        john.setAge( 18 );
        car.setDriver( john );
        constraintViolations = validator.validate( car, DriverChecks.class );
        assertEquals( 1, constraintViolations.size() );
        assertEquals(
                "You first have to pass the driving test",
                constraintViolations.iterator().next().getMessage()
        );

        john.passedDrivingTest( true );
        assertEquals( 0, validator.validate( car, DriverChecks.class ).size() );

        assertEquals( 0,
                validator.validate(
                        car,
                        Default.class,
                        CarChecks.class,
                        DriverChecks.class
                ).size()
        );
    }
}

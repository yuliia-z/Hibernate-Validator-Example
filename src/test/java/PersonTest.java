import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import org.junit.Test;

public class PersonTest {

    private Validator validator;

    @Test
    public void invalidPersonPhone() {

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();

        //invalid phone number
        Person person = new Person( "Kalyan", "093785413654");
        Set<ConstraintViolation<Person>> constraintViolations =
                validator.validate( person );

        System.out.println(constraintViolations.iterator().next().getMessage());

    }
}

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;
import java.util.Objects;

public class Person {

    @NotNull
    private String name;

    @NotNull
    private String phone;

    public Person(String name, String phone) {
        this.name = name;
        this.phone = phone;
    }

    public Person(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * In frontend helper - Available phone number formats:
     * +380630000000, 0630000000,
     * 063-000-0000, (063)-000-0000,
     * 063.000.0000, 063 000 0000
     */
    @AssertTrue(message = "Invalid phone number format")
    private boolean isValidPhoneNumber() {
        if (Objects.isNull(phone)) return true;
        if (phone.matches("[0-9*#+() -]{13}")) return true;
        if (phone.matches("\\d{10}")) return true;
        if (phone.matches("\\d{3}[-.\\s]\\d{3}[-.\\s]\\d{4}")) return true;
        if (phone.matches("\\(\\d{3}\\)-\\d{3}-\\d{4}")) return true;
        return false;
    }

}
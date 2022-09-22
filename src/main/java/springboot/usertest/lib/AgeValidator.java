package springboot.usertest.lib;

import java.time.LocalDate;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Value;

public class AgeValidator implements ConstraintValidator<ValidEmail, LocalDate> {
    @Value("${min.age")
    private int minAge;

    @Override
    public boolean isValid(LocalDate localDate,
                           ConstraintValidatorContext constraintValidatorContext) {
        int userAge = LocalDate.now().getYear() - localDate.getYear();
        return localDate.isBefore(LocalDate.now()) && userAge >= minAge;
    }
}

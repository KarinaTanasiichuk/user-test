package springboot.usertest.dto.request;

import java.time.LocalDate;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import springboot.usertest.lib.ValidAge;
import springboot.usertest.lib.ValidEmail;

@Setter
@Getter
public class UserRequestDto {
    @ValidEmail
    private String email;
    @NotNull(message = "First name may not be null")
    private String firstName;
    @NotNull(message = "Last name may not be null")
    private String lastName;
    @ValidAge
    private LocalDate birthDate;
    private String address;
    private String phone;
}

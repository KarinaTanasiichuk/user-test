package springboot.usertest.config;

import java.time.LocalDate;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import springboot.usertest.model.User;
import springboot.usertest.service.UserService;

@Component
@RequiredArgsConstructor
public class DataInitializer {
    private final UserService userService;

    @PostConstruct
    public void initialize() {
        User first = new User("first.user@gmail.com",
                "Name", "Last name",
                LocalDate.of(1998, 7, 25),
                "Address", "12345678");
        userService.create(first);

        User second = new User("second.user@gmail.com",
                "Name", "Last name",
                LocalDate.of(2000, 6, 7),
                "Address", "3456789");
        userService.create(second);

        User third = new User("third.user@gmail.com",
                "Name", "Last name",
                LocalDate.of(2022, 2, 24),
                "Address", "45674321");
        userService.create(third);
    }
}

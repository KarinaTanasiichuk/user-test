package springboot.usertest.service;

import java.time.LocalDate;
import java.util.List;
import springboot.usertest.model.User;

public interface UserService {
    User create(User user);

    User update(User user);

    void delete(Long id);

    List<User> findUsersBetweenBirthDate(LocalDate before, LocalDate after);

    List<User> getAll();

    User getUserById(Long id);
}

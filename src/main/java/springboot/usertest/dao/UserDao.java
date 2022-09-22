package springboot.usertest.dao;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import springboot.usertest.model.User;

public interface UserDao {

    User create(User user);

    User update(User user);

    void delete(Long id);

    List<User> findUsersBetweenBirthDate(LocalDate before, LocalDate after);

    List<User> getAll();

    Optional<User> getUserById(Long id);
}

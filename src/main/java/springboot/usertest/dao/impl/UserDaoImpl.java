package springboot.usertest.dao.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import org.springframework.stereotype.Repository;
import springboot.usertest.dao.UserDao;
import springboot.usertest.db.Storage;
import springboot.usertest.model.User;

@Repository
public class UserDaoImpl implements UserDao {
    @Override
    public User create(User user) {
        user.setId(generateUserId());
        Storage.userStorage.put(user.getId(), user);
        return user;
    }

    @Override
    public User update(User user) {
        Storage.userStorage.replace(user.getId(), user);
        return user;
    }

    @Override
    public void delete(Long id) {
        Storage.userStorage.remove(id);
    }

    @Override
    public List<User> findUsersBetweenBirthDate(LocalDate before, LocalDate after) {
        Predicate<User> findUsersBetweenBirthDate = user -> (user.getBirthDate().isEqual(before)
                || user.getBirthDate().isAfter(before)) && (user.getBirthDate().isEqual(after)
                || user.getBirthDate().isBefore(after));
        return Storage.userStorage.values()
                .stream()
                .filter(findUsersBetweenBirthDate)
                .toList();
    }

    @Override
    public List<User> getAll() {
        return Storage.userStorage.values()
                .stream()
                .toList();
    }

    @Override
    public Optional<User> getUserById(Long id) {
        return Optional.ofNullable(Storage.userStorage.get(id));
    }

    private Long generateUserId() {
        return Storage.userStorage.values()
                .stream()
                .map(User::getId)
                .mapToLong(id -> id)
                .max()
                .orElse(0L) + 1;
    }
}

package springboot.usertest.dao.impl;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import springboot.usertest.dao.UserDao;
import springboot.usertest.db.Storage;
import springboot.usertest.model.User;

class UserDaoImplTest {
    private static UserDao userDao;
    private static Map<Long, User> storage;

    @BeforeAll
    static void beforeAll() {
        userDao = new UserDaoImpl();
        storage = new HashMap<>();
    }

    @BeforeEach
    void initialize() {
        User first = new User(1L, "first.user@gmail.com",
                "Name", "Last name",
                LocalDate.of(1998, 7, 25),
                "Address", "12345678");
        storage.put(first.getId(), first);
        userDao.create(first);

        User second = new User(2L, "second.user@gmail.com",
                "Name", "Last name",
                LocalDate.of(2000, 6, 7),
                "Address", "3456789");
        storage.put(second.getId(), second);
        userDao.create(second);

        User third = new User(3L, "third.user@gmail.com",
                "Name", "Last name",
                LocalDate.of(2022, 2, 24),
                "Address", "45674321");
        storage.put(third.getId(), third);
        userDao.create(third);
    }

    @Test
    void create_is_ok() {
        User user = new User("fourth.user@gmail.com",
                "Name", "Last name",
                LocalDate.of(2019, 10, 3),
                "Address", "4890345");
        userDao.create(user);
        storage.put(user.getId(), user);
        Assertions.assertEquals(storage, Storage.userStorage);
        Assertions.assertEquals(storage.size(), Storage.userStorage.size());
    }

    @Test
    void update_is_ok() {
        User user = new User(1L, "update.user@gmail.com",
                "First name", "Surname",
                LocalDate.of(1989, 8, 8),
                "address", "000000");
        storage.put(user.getId(), user);
        userDao.update(user);
        Assertions.assertEquals(storage, Storage.userStorage);
        Assertions.assertEquals(storage.size(), Storage.userStorage.size());
    }

    @Test
    void delete_is_ok() {
        storage.remove(1L);
        userDao.delete(1L);
        Assertions.assertEquals(storage, Storage.userStorage);
        Assertions.assertEquals(storage.size(), Storage.userStorage.size());
    }

    @Test
    void findUsersBetweenBirthDate_is_ok() {
        int expected = 2;
        int actual = userDao.findUsersBetweenBirthDate(LocalDate.of(2000, 6, 7),
                LocalDate.of(2022, 2, 24)).size();
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void getAll_is_ok() {
        int expected = storage.size();
        int actual = userDao.getAll().size();
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void getUserById_is_ok() {
        User expected = storage.get(1L);
        User actual = userDao.getUserById(1L).orElse(new User());
        Assertions.assertEquals(expected, actual);
    }

    @AfterEach
    void tearDown() {
        Storage.userStorage.clear();
        storage.clear();
    }
}
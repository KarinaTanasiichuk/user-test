package springboot.usertest.service.impl;

import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import springboot.usertest.dao.UserDao;
import springboot.usertest.exception.UserNotFoundException;
import springboot.usertest.model.User;
import springboot.usertest.service.UserService;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserDao userDao;

    @Override
    public User create(User user) {
        return userDao.create(user);
    }

    @Override
    public User update(User user) {
        return userDao.update(user);
    }

    @Override
    public void delete(Long id) {
        userDao.delete(id);
    }

    @Override
    public List<User> findUsersBetweenBirthDate(LocalDate before, LocalDate after) {
        if (before.isAfter(after)) {
            throw new RuntimeException("The " + before + " must be before " + after);
        }
        return userDao.findUsersBetweenBirthDate(before, after);
    }

    @Override
    public List<User> getAll() {
        return userDao.getAll();
    }

    @Override
    public User getUserById(Long id) {
        return userDao.getUserById(id).orElseThrow(() ->
                new UserNotFoundException("User with id " + id + " not found"));
    }
}

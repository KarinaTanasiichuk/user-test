package springboot.usertest.db;

import java.util.HashMap;
import java.util.Map;
import springboot.usertest.model.User;

public class Storage {
    public static final Map<Long, User> userStorage = new HashMap<>();
}

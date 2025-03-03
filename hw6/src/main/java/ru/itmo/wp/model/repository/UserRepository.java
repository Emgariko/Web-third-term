package ru.itmo.wp.model.repository;

import ru.itmo.wp.model.database.DatabaseUtils;
import ru.itmo.wp.model.domain.User;

import javax.sql.DataSource;
import java.util.List;

public interface UserRepository {
    User find(long id);
    User findByLogin(String login);
    User findByEmail(String email);
    User findByLoginAndPasswordSha(String login, String passwordSha);
    User findByEmailAndPasswordSha(String login, String passwordSha);
    int findCount();
    List<User> findAll();
    void save(User user, String passwordSha);
}

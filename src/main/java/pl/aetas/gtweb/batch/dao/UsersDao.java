package pl.aetas.gtweb.batch.dao;

import pl.aetas.gtweb.batch.domain.User;

import java.util.List;

public interface UsersDao {
    List<User> getEnabledUsersList();
}

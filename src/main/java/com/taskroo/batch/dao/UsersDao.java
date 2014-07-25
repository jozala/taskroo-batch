package com.taskroo.batch.dao;

import com.taskroo.batch.domain.User;

import java.util.List;

public interface UsersDao {
    List<User> getEnabledUsersList();
}

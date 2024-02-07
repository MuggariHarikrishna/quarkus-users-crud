package org.localtest.service;

import org.apache.ignite.client.ClientCache;
import org.localtest.exception.UserAlreadyExistsException;
import org.localtest.exception.UserNotFoundException;

public interface UserService {

    String getUserById(int userId) throws UserNotFoundException;

    void delete(int userId) throws UserNotFoundException;

    void saveOrUpdate(int userId, String userName) throws UserAlreadyExistsException;
}

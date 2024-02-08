package org.localtest.service;

import org.jboss.logging.Logger;
import org.localtest.dao.UserDao;
import org.localtest.exception.UnableToAddUserException;
import org.localtest.exception.UserNotFoundException;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class UserServiceImpl implements UserService {

    @Inject
    public UserDao userDao;


    @Inject
    Logger logger;

    @Override
    public String getUserById(int userId) throws UserNotFoundException {
        String userName = userDao.getUserById(userId);
        logger.info("searching user with userId :: " + userId + ", response received from cache :: " + userName);
        if (userName == null || userName.length() == 0) {
            throw new UserNotFoundException(userId + " UserNotFound");
        }
        return userName + " : " + userId;
    }

    @Override
    public void delete(int userId) throws UserNotFoundException {
        Boolean status = userDao.delete(userId);
        logger.info("deleting user with userId :: " + userId + ", response received from cache :: " + status);
        if (!status) {
            throw new UserNotFoundException(userId + " UserNotFound");
        }
    }

    @Override
    public void saveOrUpdate(int userId, String userName) throws UnableToAddUserException {
        try {
            userDao.saveOrUpdate(userId, userName);
            logger.info("added user with userId :: " + userId);
        } catch (Exception e) {
            throw new UnableToAddUserException(userId + " Unable To Save");
        }
    }
}

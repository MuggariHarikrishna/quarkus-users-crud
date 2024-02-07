package org.localtest.service;

import org.apache.ignite.client.ClientCache;
import org.apache.ignite.client.IgniteClient;
import org.jboss.logging.Logger;
import org.localtest.dao.ApacheIgniteDao;
import org.localtest.exception.UnableToAddUserException;
import org.localtest.exception.UserNotFoundException;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class UserServiceImpl implements UserService {

    @Inject
    public ApacheIgniteDao apacheIgniteDao;

    @Inject
    Logger logger;

    @Override
    public String getUserById(int userId) throws UserNotFoundException {
        String userName = apacheIgniteDao.getUserById(userId);
        logger.info("searching user with userId :: " + userId + ", response received from cache :: " + userName);
        if (userName == null || userName.length() == 0) {
            throw new UserNotFoundException(userId + " UserNotFound");
        }
        return userName + " : " + userId;
    }

    @Override
    public void delete(int userId) throws UserNotFoundException {
        Boolean status = apacheIgniteDao.delete(userId);
        logger.info("deleting user with userId :: " + userId + ", response received from cache :: " + status);
        if (!status) {
            throw new UserNotFoundException(userId + " UserNotFound");
        }
    }

    @Override
    public void saveOrUpdate(int userId, String userName) throws UnableToAddUserException {
        try {
            apacheIgniteDao.saveOrUpdate(userId, userName);
            logger.info("added user with userId :: " + userId);
        } catch (Exception e) {
            throw new UnableToAddUserException(userId + " Unable To Save");
        }
    }
}

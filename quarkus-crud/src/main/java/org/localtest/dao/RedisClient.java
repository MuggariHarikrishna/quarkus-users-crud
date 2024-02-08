package org.localtest.dao;

import org.jboss.logging.Logger;
import org.localtest.exception.UserNotFoundException;
import org.redisson.api.RMapCache;
import org.redisson.api.RedissonClient;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.concurrent.TimeUnit;


@ApplicationScoped
public class RedisClient {

    @Inject
    RedissonClient redissonClient;

    String CACHE_NAME = "Users";
    @Inject
    Logger logger;

    public String getUserById(int userId) throws UserNotFoundException {
        RMapCache<String, String> userCache = redissonClient.getMapCache(CACHE_NAME);
        String userName = userCache.get(String.valueOf(userId));
        logger.info("REDIS >>> searching user with userId :: " + userId + ", response received from cache :: " + userName);
        return userName;
    }

    public Boolean delete(int userId) {
        RMapCache<String, String> userCache = redissonClient.getMapCache(CACHE_NAME);
        String respose = userCache.remove(String.valueOf(userId));
        logger.info("REDIS >>> response after deletion :: " + respose);
        if (respose == null) {
            return false;
        }
        return true;
    }

    public void saveOrUpdate(int userId, String userName) {
        RMapCache<String, String> userCache = redissonClient.getMapCache(CACHE_NAME);
        logger.info("REDIS >>> added user with userId :: " + userId);
        userCache.put(String.valueOf(userId), userName, 60, TimeUnit.SECONDS);
    }
}

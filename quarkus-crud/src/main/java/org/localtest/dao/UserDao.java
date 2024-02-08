package org.localtest.dao;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.localtest.AppConstants;
import org.localtest.exception.UserNotFoundException;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class UserDao {

    @ConfigProperty(name = "cache.client", defaultValue = AppConstants.CACHE_APACHE_IGNITE)
    String cacheClient;

    @Inject
    ApacheIgniteClient apacheIgniteClient;

    @Inject
    RedisClient redisClient;


    public String getUserById(int userId) throws UserNotFoundException {
        return getClient().getUserById(userId);
    }

    public Boolean delete(int userId) {
        return getClient().delete(userId);
    }

    public void saveOrUpdate(int userId, String userName) {
        getClient().saveOrUpdate(userId, userName);
    }

    public GenericCacheClient getClient() {
        GenericCacheClient genericCacheClient = apacheIgniteClient;
        switch (cacheClient) {
            case AppConstants.CACHE_REDIS: {
                genericCacheClient = redisClient;
                break;
            }
        }
        return genericCacheClient;
    }
}

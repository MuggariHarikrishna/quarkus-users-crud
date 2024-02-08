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

//    @Inject
//    @DefaultBean
//    GenericCacheClient genericCacheClient;

    public String getUserById(int userId) throws UserNotFoundException {
        if (cacheClient.equalsIgnoreCase(AppConstants.CACHE_APACHE_IGNITE)) {
            return apacheIgniteClient.getUserById(userId);
        } else {
            return redisClient.getUserById(userId);
        }
//        return getClient().getUserById(userId);
    }

    public Boolean delete(int userId) {
        if (cacheClient.equalsIgnoreCase(AppConstants.CACHE_APACHE_IGNITE)) {
            return apacheIgniteClient.delete(userId);
        } else {
            return redisClient.delete(userId);
        }
//        return getClient().delete(userId);
    }

    public void saveOrUpdate(int userId, String userName) {
        if (cacheClient.equalsIgnoreCase(AppConstants.CACHE_APACHE_IGNITE)) {
            apacheIgniteClient.saveOrUpdate(userId, userName);
        } else {
            redisClient.saveOrUpdate(userId, userName);
        }
//        getClient().saveOrUpdate(userId, userName);
    }

  /*  public GenericCacheClient getClient() {
        GenericCacheClient genericCacheClient = apacheIgniteClient;
        System.out.println("apacheIgniteClient :: " + apacheIgniteClient);
        System.out.println("cacheClient :: " + cacheClient);
        switch (cacheClient) {
            case AppConstants.CACHE_REDIS: {
                genericCacheClient = redisClient;
                break;
            }
        }
        return genericCacheClient;
    }*/
}

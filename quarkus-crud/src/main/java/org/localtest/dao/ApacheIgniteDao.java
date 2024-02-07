package org.localtest.dao;

import org.apache.ignite.client.ClientCache;
import org.apache.ignite.client.IgniteClient;
import org.localtest.exception.UnableToAddUserException;
import org.localtest.exception.UserNotFoundException;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class ApacheIgniteDao {

    @Inject
    IgniteClient igniteClient;

    String CACHE_NAME = "Users";

    public String getUserById(int userId) throws UserNotFoundException {
        ClientCache<Integer, String> clientCache = igniteClient.getOrCreateCache(CACHE_NAME);
        return clientCache.get(userId);
    }

    public Boolean delete(int userId) {
        ClientCache<Integer, String> clientCache = igniteClient.getOrCreateCache(CACHE_NAME);
        return clientCache.remove(userId);
    }

    public void saveOrUpdate(int userId, String userName) {
        ClientCache<Integer, String> clientCache = igniteClient.getOrCreateCache(CACHE_NAME);
        clientCache.put(userId, userName);
    }
}

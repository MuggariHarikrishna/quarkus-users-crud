package org.localtest.dao;

import org.apache.ignite.client.ClientCache;
import org.apache.ignite.client.IgniteClient;
import org.jboss.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class ApacheIgniteClient {

    @Inject
    IgniteClient igniteClient;

    String CACHE_NAME = "Users";

    @Inject
    Logger logger;

    public String getUserById(int userId) {
        ClientCache<Integer, String> clientCache = igniteClient.getOrCreateCache(CACHE_NAME);
        String userName = clientCache.get(userId);
        logger.info("APACHE IGNITE >>> searching user with userId :: " + userId + ", response received from cache :: " + userName);
        return userName;
    }

    public Boolean delete(int userId) {
        ClientCache<Integer, String> clientCache = igniteClient.getOrCreateCache(CACHE_NAME);
        Boolean status = clientCache.remove(userId);
        logger.info("APACHE IGNITE >>> response after deletion :: " + status);
        return status;
    }

    public void saveOrUpdate(int userId, String userName) {
        ClientCache<Integer, String> clientCache = igniteClient.getOrCreateCache(CACHE_NAME);
        logger.info("APACHE IGNITE >>> added user with userId :: " + userId);
        clientCache.put(userId, userName);
    }
}

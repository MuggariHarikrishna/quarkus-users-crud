package org.localtest.config;

import org.apache.ignite.Ignition;
import org.apache.ignite.client.IgniteClient;
import org.apache.ignite.configuration.ClientConfiguration;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.Produces;

@ApplicationScoped
public class AppConfig {


    @ConfigProperty(name = "apacheIgniteUrl", defaultValue = "127.0.0.1:10800")
    String apacheIgniteUrl;

    @ConfigProperty(name = "redisUrl", defaultValue = "redis://127.0.0.1:6379")
    String redisBaseUrl;

    @ConfigProperty(name = "cacheDuration", defaultValue = "60")
    public long cacheDuration;

    String CACHE_NAME = "Users";

    @Produces
    @ApplicationScoped
    public IgniteClient igniteClient() {
        ClientConfiguration cfg = new ClientConfiguration().setAddresses(apacheIgniteUrl);
        IgniteClient client = Ignition.startClient(cfg);
        return client;

        /*TcpDiscoveryVmIpFinder tcpDiscoveryFinder = new TcpDiscoveryVmIpFinder();
        String[] addresses = {apacheIgniteUrl};
        tcpDiscoveryFinder.setAddresses(Arrays.asList(addresses));
        TcpDiscoverySpi discoSpi = new TcpDiscoverySpi();
        discoSpi.setIpFinder(tcpDiscoveryFinder);

        CacheConfiguration cacheConfiguration = new CacheConfiguration();
        cacheConfiguration.setName(CACHE_NAME);
        cacheConfiguration.setEagerTtl(true);
        cacheConfiguration.setExpiryPolicyFactory(CreatedExpiryPolicy.factoryOf(new Duration(TimeUnit.SECONDS, Long.valueOf(cacheDuration))));
        IgniteConfiguration igniteConfiguration = new IgniteConfiguration();
        igniteConfiguration.setCacheConfiguration(cacheConfiguration);
        igniteConfiguration.setDiscoverySpi(discoSpi);
        Ignition.start(igniteConfiguration);
        Ignition.setClientMode(true);
        return Ignition.start(igniteConfiguration);*/
    }

    @Produces
    @ApplicationScoped
    public RedissonClient redisClient() {
        Config config = new Config();
        config.useSingleServer()
                .setAddress(redisBaseUrl);
        RedissonClient client = Redisson.create(config);
        return client;
    }
}

package com.hrapp.global.config;

import java.net.URI;
import java.util.concurrent.TimeUnit;

import javax.cache.configuration.MutableConfiguration;
import javax.cache.expiry.CreatedExpiryPolicy;
import javax.cache.expiry.Duration;

import org.hibernate.cache.jcache.ConfigSettings;
import org.redisson.Redisson;
import org.redisson.config.ClusterServersConfig;
import org.redisson.config.Config;
import org.redisson.config.SingleServerConfig;
import org.redisson.jcache.configuration.RedissonConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.info.BuildProperties;
import org.springframework.boot.info.GitProperties;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import tech.jhipster.config.JHipsterProperties;
import tech.jhipster.config.cache.PrefixedKeyGenerator;

@Configuration
@EnableCaching
@EnableConfigurationProperties(JHipsterProperties.class)
public class CacheConfiguration {

    private GitProperties gitProperties;
    private BuildProperties buildProperties;

    @Bean
    public javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration(JHipsterProperties jHipsterProperties) {
        MutableConfiguration<Object, Object> jcacheConfig = new MutableConfiguration<>();

        URI redisUri = URI.create(jHipsterProperties.getCache().getRedis().getServer()[0]);

        Config config = new Config();
        if (jHipsterProperties.getCache().getRedis().isCluster()) {
            ClusterServersConfig clusterServersConfig = config
                .useClusterServers()
                .setMasterConnectionPoolSize(jHipsterProperties.getCache().getRedis().getConnectionPoolSize())
                .setMasterConnectionMinimumIdleSize(jHipsterProperties.getCache().getRedis().getConnectionMinimumIdleSize())
                .setSubscriptionConnectionPoolSize(jHipsterProperties.getCache().getRedis().getSubscriptionConnectionPoolSize())
                .addNodeAddress(jHipsterProperties.getCache().getRedis().getServer());

            if (redisUri.getUserInfo() != null) {
                clusterServersConfig.setPassword(redisUri.getUserInfo().substring(redisUri.getUserInfo().indexOf(':') + 1));
            }
        } else {
            SingleServerConfig singleServerConfig = config
                .useSingleServer()
                .setConnectionPoolSize(jHipsterProperties.getCache().getRedis().getConnectionPoolSize())
                .setConnectionMinimumIdleSize(jHipsterProperties.getCache().getRedis().getConnectionMinimumIdleSize())
                .setSubscriptionConnectionPoolSize(jHipsterProperties.getCache().getRedis().getSubscriptionConnectionPoolSize())
                .setAddress(jHipsterProperties.getCache().getRedis().getServer()[0]);

            if (redisUri.getUserInfo() != null) {
                singleServerConfig.setPassword(redisUri.getUserInfo().substring(redisUri.getUserInfo().indexOf(':') + 1));
            }
        }
        jcacheConfig.setStatisticsEnabled(true);
        jcacheConfig.setExpiryPolicyFactory(
            CreatedExpiryPolicy.factoryOf(new Duration(TimeUnit.SECONDS, jHipsterProperties.getCache().getRedis().getExpiration()))
        );
        return RedissonConfiguration.fromInstance(Redisson.create(config), jcacheConfig);
    }

    @Bean
    public HibernatePropertiesCustomizer hibernatePropertiesCustomizer(CacheManager cm) {
        return hibernateProperties -> hibernateProperties.put(ConfigSettings.CACHE_MANAGER, cm);
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer(javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration) {
        return cm -> {
            createCache(cm, com.hrapp.global.repository.UserRepository.USERS_BY_LOGIN_CACHE, jcacheConfiguration);
            createCache(cm, com.hrapp.global.repository.UserRepository.USERS_BY_EMAIL_CACHE, jcacheConfiguration);
            createCache(cm, com.hrapp.global.entity.User.class.getName(), jcacheConfiguration);
            createCache(cm, com.hrapp.global.entity.Authority.class.getName(), jcacheConfiguration);
            createCache(cm, com.hrapp.global.entity.User.class.getName() + ".authorities", jcacheConfiguration);
            createCache(cm, com.hrapp.global.entity.Region.class.getName(), jcacheConfiguration);
            createCache(cm, com.hrapp.global.entity.Country.class.getName(), jcacheConfiguration);
            createCache(cm, com.hrapp.global.entity.Location.class.getName(), jcacheConfiguration);
            createCache(cm, com.hrapp.global.entity.Department.class.getName(), jcacheConfiguration);
            createCache(cm, com.hrapp.global.entity.Department.class.getName() + ".employees", jcacheConfiguration);
            createCache(cm, com.hrapp.global.entity.Task.class.getName(), jcacheConfiguration);
            createCache(cm, com.hrapp.global.entity.Task.class.getName() + ".jobs", jcacheConfiguration);
            createCache(cm, com.hrapp.global.entity.Employee.class.getName(), jcacheConfiguration);
            createCache(cm, com.hrapp.global.entity.Employee.class.getName() + ".jobs", jcacheConfiguration);
            createCache(cm, com.hrapp.global.entity.Job.class.getName(), jcacheConfiguration);
            createCache(cm, com.hrapp.global.entity.Job.class.getName() + ".tasks", jcacheConfiguration);
            createCache(cm, com.hrapp.global.entity.JobHistory.class.getName(), jcacheConfiguration);

        };
    }

    private void createCache(
        javax.cache.CacheManager cm,
        String cacheName,
        javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration
    ) {
        javax.cache.Cache<Object, Object> cache = cm.getCache(cacheName);
        if (cache != null) {
            cache.clear();
        } else {
            cm.createCache(cacheName, jcacheConfiguration);
        }
    }

    @Autowired(required = false)
    public void setGitProperties(GitProperties gitProperties) {
        this.gitProperties = gitProperties;
    }

    @Autowired(required = false)
    public void setBuildProperties(BuildProperties buildProperties) {
        this.buildProperties = buildProperties;
    }

    @Bean
    public KeyGenerator keyGenerator() {
        return new PrefixedKeyGenerator(this.gitProperties, this.buildProperties);
    }
}

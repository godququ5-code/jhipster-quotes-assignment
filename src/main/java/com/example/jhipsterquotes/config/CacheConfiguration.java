package com.example.jhipsterquotes.config;

import java.time.Duration;
import org.ehcache.config.builders.*;
import org.ehcache.jsr107.Eh107Configuration;
import org.hibernate.cache.jcache.ConfigSettings;
import org.springframework.boot.cache.autoconfigure.JCacheManagerCustomizer;
import org.springframework.boot.hibernate.autoconfigure.HibernatePropertiesCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.jhipster.config.JHipsterProperties;

@Configuration
@EnableCaching
public class CacheConfiguration {

    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        var ehcache = jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
            CacheConfigurationBuilder.newCacheConfigurationBuilder(
                Object.class,
                Object.class,
                ResourcePoolsBuilder.heap(ehcache.getMaxEntries())
            )
                .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(ehcache.getTimeToLiveSeconds())))
                .build()
        );
    }

    @Bean
    public HibernatePropertiesCustomizer hibernatePropertiesCustomizer(javax.cache.CacheManager cacheManager) {
        return hibernateProperties -> hibernateProperties.put(ConfigSettings.CACHE_MANAGER, cacheManager);
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            createCache(cm, com.example.jhipsterquotes.repository.UserRepository.USERS_BY_LOGIN_CACHE);
            createCache(cm, com.example.jhipsterquotes.repository.UserRepository.USERS_BY_EMAIL_CACHE);
            createCache(cm, com.example.jhipsterquotes.domain.User.class.getName());
            createCache(cm, com.example.jhipsterquotes.domain.Authority.class.getName());
            createCache(cm, com.example.jhipsterquotes.domain.User.class.getName() + ".authorities");
            createCache(cm, com.example.jhipsterquotes.domain.Region.class.getName());
            createCache(cm, com.example.jhipsterquotes.domain.Country.class.getName());
            createCache(cm, com.example.jhipsterquotes.domain.Location.class.getName());
            createCache(cm, com.example.jhipsterquotes.domain.Department.class.getName());
            createCache(cm, com.example.jhipsterquotes.domain.Department.class.getName() + ".employees");
            createCache(cm, com.example.jhipsterquotes.domain.Task.class.getName());
            createCache(cm, com.example.jhipsterquotes.domain.Task.class.getName() + ".jobs");
            createCache(cm, com.example.jhipsterquotes.domain.Employee.class.getName());
            createCache(cm, com.example.jhipsterquotes.domain.Employee.class.getName() + ".jobs");
            createCache(cm, com.example.jhipsterquotes.domain.Job.class.getName());
            createCache(cm, com.example.jhipsterquotes.domain.Job.class.getName() + ".tasks");
            createCache(cm, com.example.jhipsterquotes.domain.JobHistory.class.getName());
            // jhipster-needle-ehcache-add-entry
        };
    }

    private void createCache(javax.cache.CacheManager cm, String cacheName) {
        javax.cache.Cache<Object, Object> cache = cm.getCache(cacheName);
        if (cache != null) {
            cache.clear();
        } else {
            cm.createCache(cacheName, jcacheConfiguration);
        }
    }
}

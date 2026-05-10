package com.example.jhipsterquotes;

import com.example.jhipsterquotes.config.AsyncSyncConfiguration;
import com.example.jhipsterquotes.config.DatabaseTestcontainer;
import com.example.jhipsterquotes.config.JacksonConfiguration;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.context.ImportTestcontainers;

/**
 * Base composite annotation for integration tests.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@SpringBootTest(
    classes = {
        JhipsterQuotesApp.class,
        JacksonConfiguration.class,
        AsyncSyncConfiguration.class,
        com.example.jhipsterquotes.config.JacksonHibernateConfiguration.class,
    }
)
@ImportTestcontainers(DatabaseTestcontainer.class)
public @interface IntegrationTest {}

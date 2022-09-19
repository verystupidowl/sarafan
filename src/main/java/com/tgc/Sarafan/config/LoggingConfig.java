package com.tgc.Sarafan.config;

import io.sentry.servlet.SentryServletContainerInitializer;
import io.sentry.spring.SentryExceptionResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerExceptionResolver;

import javax.servlet.ServletContainerInitializer;

@Configuration
public class LoggingConfig {

    @Bean
    public HandlerExceptionResolver sentryExceptionResolver() {
        return new SentryExceptionResolver();
    }

    @Bean
    public ServletContainerInitializer sentryServletContextInitializer() {
        return new SentryServletContainerInitializer();
    }
}

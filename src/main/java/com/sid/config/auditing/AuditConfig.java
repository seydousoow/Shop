package com.sid.config.auditing;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

@Configuration
@EnableMongoAuditing
public class AuditConfig {

        @Bean
        public AuditorAware<String> myAuditorProvider() {
            return new AuditorAwareImpl();
        }
}

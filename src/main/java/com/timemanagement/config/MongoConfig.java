package com.timemanagement.config;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

import java.util.concurrent.TimeUnit;

@Configuration
@Profile("prod")
public class MongoConfig {

    @Value("${spring.data.mongodb.uri}")
    private String mongoUri;

    @Bean
    @Primary
    public MongoClient mongoClient() {
        ConnectionString connectionString = new ConnectionString(mongoUri);
        
        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .applyToConnectionPoolSettings(builder -> 
                    builder.maxConnectionIdleTime(60, TimeUnit.SECONDS)
                           .maxConnectionLifeTime(120, TimeUnit.SECONDS)
                           .minSize(1)
                           .maxSize(10)
                )
                .applyToSocketSettings(builder -> 
                    builder.connectTimeout(10, TimeUnit.SECONDS)
                           .readTimeout(10, TimeUnit.SECONDS)
                )
                .applyToClusterSettings(builder -> 
                    builder.serverSelectionTimeout(5, TimeUnit.SECONDS)
                )
                .retryWrites(true)
                .retryReads(true)
                .build();

        return MongoClients.create(settings);
    }
}

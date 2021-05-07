package com.mscorp.meepleserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({
        FileStorageProperties.class
})
public class MeepleServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(MeepleServerApplication.class, args);
    }

}

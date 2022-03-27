package io.github.mityavasilyev.eurekaregistryserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class EurekaRegistryServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(EurekaRegistryServerApplication.class, args);
    }

}

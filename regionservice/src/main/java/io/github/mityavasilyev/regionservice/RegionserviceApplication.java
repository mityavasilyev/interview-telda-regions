package io.github.mityavasilyev.regionservice;

import io.github.mityavasilyev.regionservice.model.Region;
import org.apache.ibatis.type.MappedTypes;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@MappedTypes(Region.class)
@MapperScan("io.github.mityavasilyev.regionservice.mapper")
@EnableEurekaClient
@EnableCaching
public class RegionserviceApplication {

    public static void main(String[] args) {
        SpringApplication.run(RegionserviceApplication.class, args);
    }

}
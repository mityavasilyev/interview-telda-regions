package io.github.mityavasilyev.regionservice;

import io.github.mityavasilyev.regionservice.model.Region;
import org.apache.ibatis.type.MappedTypes;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@MappedTypes(Region.class)
@MapperScan("io.github.mityavasilyev.regionservice.mapper")
@EnableCaching
public class RegionserviceApplication {

    public static void main(String[] args) {
        SpringApplication.run(RegionserviceApplication.class, args);
    }

}
package com.gundom;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.math.BigDecimal;

@SpringBootApplication
@MapperScan("com.gundom.DAO")
public class MoveveryfastApplication {



    public static void main(String[] args) {
        SpringApplication.run(MoveveryfastApplication.class, args);

    }

}

package com.meli.fuegoquasar;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class FuegoQuasarApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(FuegoQuasarApplication.class, args);
    }

    public void run(String... args) throws Exception {

    }

}

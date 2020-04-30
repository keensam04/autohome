package com.autohome;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Main {


    private static final Logger LOG = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        Runtime.getRuntime().addShutdownHook(new Thread(Main::logShutDown));
        SpringApplication.run(Main.class, args);
    }

    private static void logShutDown() {
        LOG.info("JVM shutting down");
    }

}

package com.github.torleifg.discriminator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    /*
    @Override
    public void run(String... args) throws Exception {
        final var scanner = new Scanner(System.in);

        while (scanner.hasNext()) {
            final var next = scanner.next();
        }
    }
     */
}
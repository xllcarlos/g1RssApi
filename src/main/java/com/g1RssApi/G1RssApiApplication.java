package com.g1RssApi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * G1 RSS API
 * Api do portal de noticias g1 na forma de RSS.
 *
 * @author Jhonatan Isaias
 * @author Carlos Santos
 * @author Gilson Teixeira
 */
@SpringBootApplication
public class G1RssApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(G1RssApiApplication.class, args);
    }

}
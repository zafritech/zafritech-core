package org.zafritech;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ZafritechApplication {

    public static void main(String[] args) {
        
        SpringApplication.run(ZafritechApplication.class, args);
    }
}

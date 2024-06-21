package jav.bot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.autoconfigure.security.servlet.ManagementWebSecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(exclude = {ManagementWebSecurityAutoConfiguration.class})
public class JavaBot2Application {
    public static void main(String[] args) {
        SpringApplication.run(JavaBot2Application.class, args);
    }
}
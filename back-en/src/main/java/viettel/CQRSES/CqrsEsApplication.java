package viettel.CQRSES;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication()
public class CqrsEsApplication {

    public static void main(String[] args) {
        SpringApplication.run(CqrsEsApplication.class, args);
    }

}

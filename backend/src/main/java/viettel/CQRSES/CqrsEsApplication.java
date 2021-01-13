package viettel.CQRSES;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

import java.util.concurrent.Executors;

@SpringBootApplication()
public class CqrsEsApplication {

    public static void main(String[] args) {
        SpringApplication.run(CqrsEsApplication.class, args);
    }
    @Bean
    public Scheduler jdbcScheduler() {
        return Schedulers.fromExecutor(Executors.newFixedThreadPool(8));
    }

}

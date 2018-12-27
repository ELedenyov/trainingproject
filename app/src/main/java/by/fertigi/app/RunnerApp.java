package by.fertigi.app;

import by.fertigi.app.service.StarterApp;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class RunnerApp {
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(RunnerApp.class, args);

        StarterApp bean = context.getBean(StarterApp.class);
        bean.run();
    }
}
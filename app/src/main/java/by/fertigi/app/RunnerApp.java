package by.fertigi.app;

import by.fertigi.app.configuration.ConfigApp;
import by.fertigi.app.configuration.DbConfig;
import by.fertigi.app.service.ConfigurationAppService;
import by.fertigi.app.service.StarterApp;
import by.fertigi.app.util.SQLCreator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootApplication
@Import({DbConfig.class, ConfigApp.class})
public class RunnerApp {
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(RunnerApp.class, args);

        StarterApp bean = context.getBean(StarterApp.class);
        bean.run();
//        bean.insertDataToDB(15);
    }
}
package by.fertigi.app;

import by.fertigi.app.configuration.DbConfig;
import by.fertigi.app.dao.EntityRepository;
import by.fertigi.app.dao.FillingDB;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(DbConfig.class)
public class RunnerApp {
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(RunnerApp.class, args);

        EntityRepository bean = context.getBean(EntityRepository.class);
//        bean.doQuery();
        bean.selectForUpdate();

//        добавление данных в базу
//        FillingDB bean = context.getBean(FillingDB.class);
//        bean.doAction();
    }
}
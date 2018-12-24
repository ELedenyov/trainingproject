package by.fertigi.app;

import by.fertigi.app.configuration.DbConfig;
import by.fertigi.app.dao.EntityRepository;
import by.fertigi.app.dao.FillingDB;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Import;

import java.sql.SQLException;

@SpringBootApplication
@Import(DbConfig.class)
public class RunnerApp {
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(RunnerApp.class, args);


//        добавление данных в базу
//        FillingDB fillingDB = context.getBean(FillingDB.class);
//        for (int i = 0; i < 15; i++) {
//            fillingDB.doAction();
//        }


//start
        EntityRepository entityRepository = context.getBean(EntityRepository.class);

        int countRow = entityRepository.countRow();
        int pool = Runtime.getRuntime().availableProcessors();
        int step = countRow/1000;

        System.out.println("!!!!!!!!!!!!!!!!!!        poool: " + pool);
        GetCountLimit getCountLimit = new GetCountLimit();
        getCountLimit.setCount(0);
        getCountLimit.setStep(step);

        Runnable task = ()->{
            int count = getCountLimit.getCount();
            while (count < countRow) {
                try {
                    entityRepository.update(count, step);
//                    entityRepository.selectForUpdate(count, step);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                count = getCountLimit.getCount();
            }
        };

        for (int i = 0; i < pool; i++) {
            new Thread(task).start();
            try {
                Thread.sleep(15);
            } catch (InterruptedException e) {
                System.out.println("exception!!!!");
            }
        }
        //finish
    }
}

class GetCountLimit {
    private Integer count = 0;
    private Integer step=0;

    public Integer getStep() {
        return step;
    }

    public void setStep(Integer step) {
        this.step = step;
    }

    public synchronized Integer getCount() {
        int returnCount = count;
        count = count + step;
        return returnCount;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
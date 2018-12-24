package by.fertigi.app.service;

import by.fertigi.app.dao.EntityRepository;
import by.fertigi.app.dao.FillingDB;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
public class StarterApp {
    private ConfigurationAppService config;
    private GetCountLimit getCountLimit;
    private EntityRepository entityRepository;
    private FillingDB fillingDB;

    public StarterApp(
            ConfigurationAppService config,
            GetCountLimit getCountLimit,
            EntityRepository entityRepository,
            FillingDB fillingDB) {
        this.config = config;
        this.getCountLimit = getCountLimit;
        this.entityRepository = entityRepository;
        this.fillingDB = fillingDB;
    }

    public void run(){
        ExecutorService executor = Executors.newFixedThreadPool(config.getAmountThread());

        int countRow = entityRepository.countRow();
        int pool = config.getAmountThread();
        int step = config.getStep();

        getCountLimit.setCount(0);
        getCountLimit.setStep(step);

        Runnable task = ()->{
            int count = getCountLimit.getCount();
            while (count < countRow) {
                try {
                    entityRepository.update(count, step);
//                    entityRepository.selectForUpdate(count, step);
                } catch (SQLException e) {
                    //TODO add logging
                    e.printStackTrace();
                }
                count = getCountLimit.getCount();
            }
        };

        for (int i = 0; i < pool; i++) {
            //new Thread(task).start();
            executor.submit(task);
            try {
                Thread.sleep(15);
            } catch (InterruptedException e) {
                System.out.println("exception!!!!");
            }
        }

        executor.shutdown();
    }

//        добавление данных в базу
    public void insertDataToDB(Integer amauntReplay){
        System.out.println("rows: " + amauntReplay*config.getBatchSize());
        for (int i = 0; i < amauntReplay; i++) {
            fillingDB.doAction(config.getBatchSize());
        }
    }
}

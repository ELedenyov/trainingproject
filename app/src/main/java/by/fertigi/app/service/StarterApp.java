package by.fertigi.app.service;

import by.fertigi.app.dao.EntityRepository;
import by.fertigi.app.dao.FillingDB;
import by.fertigi.app.util.SQLCreator;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
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

        Callable<String> task = ()->{
            int count = getCountLimit.getCount();
            StringBuilder builder = new StringBuilder("Completed: \n");
            while (count < config.getCountRow()) {
                try {
                    entityRepository.update(
                            count,
                            config.getStep(),
                            config.getSQL_SELECT(),
                            config.getSQL_UPDATE()
                    );
                    builder.append("[count: ").append(count).append(", ").append("step: ").append(config.getStep()).append("] ");
                } catch (Exception e) {
                    //TODO add logging
                    e.printStackTrace();
                }
                count = getCountLimit.getCount();
            }
            return builder.toString();
        };

        //TODO вынести в метод и размер зависит от config.getAmountThread()
        List<Callable<String>> taskList = new ArrayList<>();
        taskList.add(task);
        taskList.add(task);
        taskList.add(task);
        taskList.add(task);

        ExecutorService executorService = Executors.newFixedThreadPool(config.getAmountThread());

        for (Map.Entry<String, List<String>> entity : config.getEntityMap().entrySet()) {
            getCountLimit.setCount(0);
            getCountLimit.setStep(config.getStep());

            config.setSQL_SELECT(SQLCreator.sqlSelectCreator(entity.getKey(), entity.getValue()));
            config.setSQL_UPDATE(SQLCreator.sqlUpdateCreator(entity.getKey(), entity.getValue()));
            config.setSQL_SELECT_COUNT_ALL(SQLCreator.sqlSelectCountAllCreator(entity.getKey()));
            config.setCountRow(entityRepository.countRow(config.getSQL_SELECT_COUNT_ALL()));

            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            try {
                executorService.invokeAll(taskList).stream()
                        .map(f -> {
                            try {
                                return f.get();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            return null;
                        })
                        .forEach(r -> System.out.println("Work result = " + r));
                //sleep!
                Thread.sleep(20000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        executorService.shutdown();

    }

//        добавление данных в базу
    public void insertDataToDB(Integer amountReplay){
        for (int i = 0; i < amountReplay; i++) {
            fillingDB.doAction(config.getBatchSize());
        }
    }
}

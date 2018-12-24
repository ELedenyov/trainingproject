package by.fertigi.app.service;

import by.fertigi.app.dao.EntityRepository;
import by.fertigi.app.dao.FillingDB;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
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
        ExecutorService executor = Executors.newFixedThreadPool(config.getAmountThread());

        int countRow = entityRepository.countRow();
        int pool = config.getAmountThread();
        int step = config.getStep();

        getCountLimit.setCount(0);
        getCountLimit.setStep(step);

        Callable<String> task = ()->{
            int count = getCountLimit.getCount();
            StringBuilder builder = new StringBuilder("Completed: \n");
            while (count < countRow) {
                try {
                    entityRepository.update(count, step);
                    builder.append("count: ").append(count).append(", ").append("step: ").append(step).append("\n");
//                    entityRepository.selectForUpdate(count, step);
                } catch (SQLException e) {
                    //TODO add logging
                    e.printStackTrace();
                }
                count = getCountLimit.getCount();
            }
            return builder.toString();
        };

        List<Callable<String>> taskList = new ArrayList<>();
        taskList.add(task);
        taskList.add(task);
        taskList.add(task);
        taskList.add(task);

        ExecutorService executorService = Executors.newFixedThreadPool(config.getAmountThread());
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
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        executorService.shutdown();
//
//        for (int i = 0; i < pool; i++) {
//            //new Thread(task).start();
//            executor.submit(task);
//            try {
//                Thread.sleep(15);
//            } catch (InterruptedException e) {
//                System.out.println("exception!!!!");
//            }
//        }

        executor.shutdown();
    }

//        добавление данных в базу
    public void insertDataToDB(Integer amountReplay){
        System.out.println("rows: " + amountReplay*config.getBatchSize());
        for (int i = 0; i < amountReplay; i++) {
            fillingDB.doAction(config.getBatchSize());
        }
    }
}

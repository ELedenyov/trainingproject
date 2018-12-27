package by.fertigi.app.service;

import by.fertigi.app.dao.EntityRepository;
import by.fertigi.app.dao.FillingDB;
import by.fertigi.app.thread.ThreadTask;
import by.fertigi.app.util.SQLCreator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
public class StarterApp {
    private static final Logger logger = LogManager.getLogger(StarterApp.class);
    private ConfigurationAppService config;
    private EntityRepository entityRepository;
    private FillingDB fillingDB;

    public StarterApp(
            ConfigurationAppService config,
            EntityRepository entityRepository,
            FillingDB fillingDB) {
        this.config = config;
        this.entityRepository = entityRepository;
        this.fillingDB = fillingDB;
    }

    public void run() {

        List<Callable<String>> taskList
                = getListCallable(config.getAmountThread(), new ThreadTask(config, entityRepository));

        ExecutorService executorService = Executors.newFixedThreadPool(config.getAmountThread());

        for (Map.Entry<String, List<String>> entity : config.getEntityMap().entrySet()) {
            updateConfig(
                    SQLCreator.sqlSelectCreator(entity.getKey(), entity.getValue()),
                    SQLCreator.sqlUpdateCreator(entity.getKey(), entity.getValue()),
                    SQLCreator.sqlSelectCountAllCreator(entity.getKey()),
                    0,
                    config.getStep(),
                    entity.getValue()

            );
            try {
                executorService.invokeAll(taskList)
                        .stream()
                        .map(f -> {
                            try {
                                return f.get();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            return null;
                        })
                        .forEach(r -> logger.info("Work result = " + r));
            } catch (InterruptedException e) {
                logger.error("Message: " + e.getMessage() + "\n" + "StackTrace: \n" + e.getStackTrace());
                e.printStackTrace();
            }
        }

        executorService.shutdown();
    }

    public void insertDataToDB(Integer amountReplay) {
        for (int i = 0; i < amountReplay; i++) {
            fillingDB.doAction(config.getBatchSize());
        }
    }

    private List<Callable<String>> getListCallable(int size, Callable<String> task) {
        ArrayList<Callable<String>> tasks = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            tasks.add(task);
        }
        return tasks;
    }

    private void updateConfig(String SQL_SELECT, String SQL_UPDATE, String SQL_SELECT_COUNT_ALL, int start, int step, List<String> fields){
        config.setCount(start);
        config.setSQL_SELECT(SQL_SELECT);
        config.setSQL_UPDATE(SQL_UPDATE);
        config.setSQL_SELECT_COUNT_ALL(SQL_SELECT_COUNT_ALL);
        config.setCountRow(entityRepository.countRow(SQL_SELECT_COUNT_ALL));
        config.setFields(fields);
        logger.info("Update config: \n"
                + "start: " + start + ", step: " + step + "\n"
                + "sql_select: " + SQL_SELECT + "\n"
                + "sql_update: " + SQL_UPDATE + "\n"
                + "sql_select_count_all: " + SQL_SELECT_COUNT_ALL + "\n"
                + "count row: " + config.getCountRow() + "\n"
                + "fields: " + fields + "\n"
        );
    }
}
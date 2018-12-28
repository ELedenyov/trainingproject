package by.fertigi.app.service;

import by.fertigi.app.configuration.AppConfig;
import by.fertigi.app.dao.EntityRepository;
import by.fertigi.app.model.ConfigurationAppService;
import by.fertigi.app.model.EntityInfo;
import by.fertigi.app.thread.ThreadTask;
import by.fertigi.app.util.SQLCreator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
public class StarterApp {
    private static final Logger logger = LoggerFactory.getLogger(StarterApp.class);
    private final EntityRepository entityRepository;
    private final CallableCreatorList<String> callableCreatorList;
    private final AppConfig appConfig;

    @Autowired
    public StarterApp(
            EntityRepository entityRepository,
            CallableCreatorList<String> callableCreatorList,
            AppConfig appConfig) {
        this.entityRepository = entityRepository;
        this.callableCreatorList = callableCreatorList;
        this.appConfig = appConfig;
    }

    public void run() {
        ConfigurationAppService configurationAppService = new ConfigurationAppService();

        List<Callable<String>> taskList
                = callableCreatorList.getListCallable(
                        new ThreadTask(configurationAppService, entityRepository),
                        appConfig.getCorePoolSize()
        );

        ExecutorService executorService = Executors.newFixedThreadPool(appConfig.getCorePoolSize());

        for (EntityInfo entity: appConfig.getModels()) {
            updateConfig(entity, appConfig.getLimitStart(), configurationAppService);

            try {
                executorService.invokeAll(taskList)
                        .stream()
                        .map(f -> {
                            try {
                                return f.get();
                            } catch (Exception e) {
                                logger.error(e.getMessage(), e);
                            }
                            return "there was an exception";
                        })
                        .forEach(r -> logger.info("Work result = " + r));
            } catch (InterruptedException e) {
                logger.error(e.getMessage(), e);
            }
        }

        executorService.shutdown();
    }

    private void updateConfig(EntityInfo entity, Integer start, ConfigurationAppService config){
        config.setCount(start);
        config.setStep(appConfig.getStep());
        config.setSQL_SELECT(SQLCreator.sqlSelectCreator(entity.getTable(), entity.getFields(), entity.getIdName()));
        config.setSQL_UPDATE(SQLCreator.sqlUpdateCreator(entity.getTable(), entity.getFields(), entity.getIdName()));
        config.setSQL_SELECT_COUNT_ALL(SQLCreator.sqlSelectCountAllCreator(entity.getTable()));
        config.setCountRow(entityRepository.countRow(config.getSQL_SELECT_COUNT_ALL()));
        config.setEntity(entity);
        logger.info("Update config: \n"
                + "start: " + start + ", step: " + config.getStep() + "\n"
                + "sql_select: " + config.getSQL_SELECT() + "\n"
                + "sql_update: " + config.getSQL_UPDATE() + "\n"
                + "sql_select_count_all: " + config.getSQL_SELECT_COUNT_ALL() + "\n"
                + "count row: " + config.getCountRow() + "\n"
                + "fields: " + config.getEntity().getFields() + "\n"
        );
    }
}
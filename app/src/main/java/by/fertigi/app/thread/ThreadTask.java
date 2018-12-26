package by.fertigi.app.thread;

import by.fertigi.app.dao.EntityRepository;
import by.fertigi.app.service.ConfigurationAppService;
import by.fertigi.app.service.GetCountLimit;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.Callable;

public class ThreadTask implements Callable<String> {
    private GetCountLimit getCountLimit;
    private ConfigurationAppService config;
    private EntityRepository entityRepository;
    private static final Logger logger = LogManager.getLogger(ThreadTask.class);


    public ThreadTask(GetCountLimit getCountLimit, ConfigurationAppService config, EntityRepository entityRepository) {
        this.getCountLimit = getCountLimit;
        this.config = config;
        this.entityRepository = entityRepository;
    }


    @Override
    public String call() throws Exception {
        int count = getCountLimit.getCount();
        StringBuilder builder = new StringBuilder("Completed: \n");
        while (count < config.getCountRow()) {
            try {
                //TODO edit string.format
                logger.info(String.format("Count: %s, Step: %s, \nSQL_SELECT: %s \nSQL_UPDATE: %s"), count, config.getStep(), config.getSQL_SELECT(), config.getSQL_UPDATE());
                entityRepository.update(
                        count,
                        config.getStep(),
                        config.getSQL_SELECT(),
                        config.getSQL_UPDATE()
                );
                builder.append("[count: ").append(count).append(", ").append("step: ").append(config.getStep()).append("] ");
            } catch (Exception e) {
                //TODO add logging
                logger.error(e.getMessage() + "\n" + "StackTrace: \n" + e.getStackTrace());
                e.printStackTrace();
            }
            count = getCountLimit.getCount();
        }
        return builder.toString();
    }
}

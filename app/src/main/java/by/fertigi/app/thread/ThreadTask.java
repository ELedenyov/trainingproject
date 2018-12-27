package by.fertigi.app.thread;

import by.fertigi.app.dao.EntityRepository;
import by.fertigi.app.service.ConfigurationAppService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.Callable;

public class ThreadTask implements Callable<String> {
    private ConfigurationAppService config;
    private EntityRepository entityRepository;
    private static final Logger logger = LogManager.getLogger(ThreadTask.class);


    public ThreadTask(ConfigurationAppService config, EntityRepository entityRepository) {
        this.config = config;
        this.entityRepository = entityRepository;
    }


    @Override
    public String call() throws Exception {
        int count = config.getCount();
        StringBuilder builder = new StringBuilder("Completed: \n");
        while (count < config.getCountRow()) {
            try {
                int[] updateRows = entityRepository.updateWithJdbcTemplate(
                        count,
                        config.getStep(),
                        config.getSQL_SELECT(),
                        config.getSQL_UPDATE(),
                        config.getEntity().getFields(),
                        config.getEntity().getIdName()
                );
                builder.append("[count: ").append(count).append(", ").append("step: ").append(config.getStep()).append("] ");
            } catch (Exception e) {
                logger.error("Message: " + e.getMessage() + "\nStackTrace: \n" + e.getStackTrace());
                e.printStackTrace();
            }
            count = config.getCount();
        }
        return builder.toString();
    }
}

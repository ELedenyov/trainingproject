package by.fertigi.app.thread;

import by.fertigi.app.dao.EntityRepository;
import by.fertigi.app.model.ConfigurationAppService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Callable;

public class ThreadTask implements Callable<String> {
    private static final Logger logger = LoggerFactory.getLogger(ThreadTask.class);
    private final ConfigurationAppService config;
    private final EntityRepository entityRepository;

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
                entityRepository.updateWithJdbcTemplate(
                        count,
                        config.getStep(),
                        config.getSQL_SELECT(),
                        config.getSQL_UPDATE(),
                        config.getEntity().getFields(),
                        config.getEntity().getIdName()
                );
                builder.append("[count: ").append(count).append(", ").append("step: ").append(config.getStep()).append("] ");
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
            count = config.getCount();
        }
        return builder.toString();
    }
}

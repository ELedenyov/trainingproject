package by.fertigi.app.configuration;

import by.fertigi.app.service.ConfigurationAppService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Map;

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "app-config")
public class AppConfig {

    @Value("${app-config.config.amount-thread}")
    private Integer amountThread;

    @Value("${app-config.config.step}")
    private Integer step;

    @Value("${app-config.config.limit-start}")
    private Integer limitStart;

    @Value("${app-config.config.batch-size}")
    private Integer batchSize;

    private Map<String, List<String>> entity;

    public Map<String, List<String>> getEntity() {
        return entity;
    }

    public void setEntity(Map<String, List<String>> entity) {
        this.entity = entity;
    }


    @Bean
    public ConfigurationAppService getConfigurationAppService(){
        ConfigurationAppService configurationAppService = new ConfigurationAppService();
        configurationAppService.setAmountThread(amountThread);
        configurationAppService.setBatchSize(batchSize);
        configurationAppService.setLimitStart(limitStart);
        configurationAppService.setStep(step);
        configurationAppService.setEntityMap(entity);
        return configurationAppService;
    }
}

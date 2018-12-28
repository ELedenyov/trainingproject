package by.fertigi.app.configuration;

import by.fertigi.app.model.EntityInfo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "app-config")
public class AppConfig {

    @Value("${app-config.config.thread.core-pool-size}")
    private Integer corePoolSize;

    @Value("${app-config.config.step}")
    private Integer step;

    @Value("${app-config.config.limit-start}")
    private Integer limitStart;

    private List<EntityInfo> models;

    public List<EntityInfo> getModels() {
        return models;
    }

    public void setModels(List<EntityInfo> models) {
        this.models = models;
    }

    public Integer getStep() {
        return step;
    }

    public void setStep(Integer step) {
        this.step = step;
    }

    public Integer getLimitStart() {
        return limitStart;
    }

    public void setLimitStart(Integer limitStart) {
        this.limitStart = limitStart;
    }

    public Integer getCorePoolSize() {
        return corePoolSize;
    }

    public void setCorePoolSize(Integer corePoolSize) {
        this.corePoolSize = corePoolSize;
    }
}

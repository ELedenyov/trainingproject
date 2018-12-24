package by.fertigi.app.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ConfigurationAppService {
    @Value("${app.config.amount-thread}")
    private Integer amountThread;

    @Value("${app.config.step}")
    private Integer step;

    @Value("${app.config.limit-start}")
    private Integer limitStart;

    @Value("${app.config.batch-size}")
    private Integer batchSize;

    private Map<String, List<String>> entityMap;

    //TODO add initialize
    @PostConstruct
    public void init(){
        //initialize entityMap from config app
        entityMap = new HashMap<>();
    }

    public ConfigurationAppService() {
    }

    public Integer getBatchSize() {
        return batchSize;
    }

    public void setBatchSize(Integer batchSize) {
        this.batchSize = batchSize;
    }

    public Integer getLimitStart() {
        return limitStart;
    }

    public void setLimitStart(Integer limitStart) {
        this.limitStart = limitStart;
    }

    public Integer getAmountThread() {
        return amountThread;
    }

    public void setAmountThread(Integer amountThread) {
        this.amountThread = amountThread;
    }

    public Integer getStep() {
        return step;
    }

    public void setStep(Integer step) {
        this.step = step;
    }

    public Map<String, List<String>> getEntityMap() {
        return entityMap;
    }

    public void setEntityMap(Map<String, List<String>> entityMap) {
        this.entityMap = entityMap;
    }
}

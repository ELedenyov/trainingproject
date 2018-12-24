package by.fertigi.app.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

public class Config {
    Map<String, List<String>> entity;

    @Autowired
    public Config(Map<String, List<String>> entity) {
        this.entity = entity;
    }

    public Map<String, List<String>> getEntity() {
        return entity;
    }

    public void setEntity(Map<String, List<String>> entity) {
        this.entity = entity;
    }
}

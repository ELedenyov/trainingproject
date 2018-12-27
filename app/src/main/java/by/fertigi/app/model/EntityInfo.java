package by.fertigi.app.model;

import java.util.List;

public class EntityInfo {
    //table name
    private String table;
    //name idName
    private String idName;
    //name fields
    private List<String> fields;

    public EntityInfo() {
    }

    public EntityInfo(String tableName, String idName, List<String> fields) {
        this.table = tableName;
        this.idName = idName;
        this.fields = fields;
    }

    public String getTable() {
        return table;
    }

    public void setTable(String tableName) {
        this.table = tableName;
    }

    public String getIdName() {
        return idName;
    }

    public void setIdName(String idName) {
        this.idName = idName;
    }

    public List<String> getFields() {
        return fields;
    }

    public void setFields(List<String> fields) {
        this.fields = fields;
    }

    @Override
    public String toString() {
        return "EntityInfo{" +
                "table='" + table + '\'' +
                ", idName='" + idName + '\'' +
                ", fields=" + fields +
                '}';
    }
}

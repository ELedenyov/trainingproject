package by.fertigi.app.model;

public class ConfigurationAppService {
    private Integer amountThread;
    private Integer step;
    private Integer limitStart;
    private Integer batchSize;
    private String SQL_SELECT;
    private String SQL_UPDATE;
    private String SQL_SELECT_COUNT_ALL;
    private int countRow;
    private EntityInfo entity;
    private Integer count;

    public ConfigurationAppService() {
    }

    public synchronized Integer getCount() {
        int returnCount = count;
        count = count + step;
        return returnCount;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public EntityInfo getEntity() {
        return entity;
    }

    public void setEntity(EntityInfo entity) {
        this.entity = entity;
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

    public String getSQL_SELECT() {
        return SQL_SELECT;
    }

    public void setSQL_SELECT(String SQL_SELECT) {
        this.SQL_SELECT = SQL_SELECT;
    }

    public String getSQL_UPDATE() {
        return SQL_UPDATE;
    }

    public void setSQL_UPDATE(String SQL_UPDATE) {
        this.SQL_UPDATE = SQL_UPDATE;
    }

    public String getSQL_SELECT_COUNT_ALL() {
        return SQL_SELECT_COUNT_ALL;
    }

    public void setSQL_SELECT_COUNT_ALL(String SQL_SELECT_COUNT_ALL) {
        this.SQL_SELECT_COUNT_ALL = SQL_SELECT_COUNT_ALL;
    }

    public int getCountRow() {
        return countRow;
    }

    public void setCountRow(int countRow) {
        this.countRow = countRow;
    }
}

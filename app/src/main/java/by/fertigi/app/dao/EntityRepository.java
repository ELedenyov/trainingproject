package by.fertigi.app.dao;

public interface EntityRepository {
    int countRow(String tableName);
    void update(int start, int step, String SQL_SELECT, String SQL_UPDATE);
}

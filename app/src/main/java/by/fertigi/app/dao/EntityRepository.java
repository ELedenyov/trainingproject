package by.fertigi.app.dao;

import java.util.List;

public interface EntityRepository {
    int countRow(String tableName);
    void update(int start, int step, String SQL_SELECT, String SQL_UPDATE);
    int[] updateWithJdbcTemplate(Integer start, Integer step, String SQL_SELECT, String SQL_UPDATE, List<String> fields);
}

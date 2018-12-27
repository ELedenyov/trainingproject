package by.fertigi.app.dao;

import java.util.List;

public interface EntityRepository {
    Integer countRow(String SQL_SELECT_COUNT_ALL);
    int[] updateWithJdbcTemplate(Integer start, Integer step, String SQL_SELECT, String SQL_UPDATE, List<String> fields);
}

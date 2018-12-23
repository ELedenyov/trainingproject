package by.fertigi.app.dao;

import java.sql.SQLException;

public interface EntityRepository {
    void selectForUpdate(int selectRow, int step);
    int countRow();
    void update(int start, int step) throws SQLException;
}

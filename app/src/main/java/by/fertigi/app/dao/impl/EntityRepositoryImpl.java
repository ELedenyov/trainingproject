package by.fertigi.app.dao.impl;

import by.fertigi.app.dao.EntityRepository;
import by.fertigi.app.util.Validator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class EntityRepositoryImpl implements EntityRepository {
    private JdbcTemplate template;
    private static final Logger logger = LogManager.getLogger(EntityRepositoryImpl.class);

    @Autowired
    public EntityRepositoryImpl(JdbcTemplate template) {
        this.template = template;
    }

    public int countRow(String SQL_SELECT_COUNT_ALL) {
        return template.queryForObject(SQL_SELECT_COUNT_ALL, Integer.class);
    }

    @Transactional
    public void update(int start, int step, String SQL_SELECT, String SQL_UPDATE) {
        Connection connection = null;
        try {
            connection = template.getDataSource().getConnection();

            connection.setAutoCommit(false);
            PreparedStatement selectPS = null;
            PreparedStatement updatePS = null;

            try {
                selectPS = connection.prepareStatement(SQL_SELECT);
                selectPS.setInt(1, start);
                selectPS.setInt(2, step);

                updatePS = connection.prepareStatement(SQL_UPDATE);

                ResultSet resultSet = selectPS.executeQuery();
                int columns = resultSet.getMetaData().getColumnCount();

                while (resultSet.next()) {
                    for (int i = 1; i < columns; i++) {
                        updatePS.setString(i, Validator.replace(resultSet.getString(i)));
                    }
                    updatePS.setInt(columns, resultSet.getInt("id"));
                    updatePS.addBatch();
                }

                updatePS.executeBatch();
                updatePS.clearBatch();
                connection.commit();

                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                if (selectPS != null) {
                    selectPS.close();
                }
                if (updatePS != null) {
                    updatePS.close();
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    public int[] updateWithJdbcTemplate(Integer start, Integer step, String SQL_SELECT, String SQL_UPDATE, List<String> fields) {
        logger.info("updateWithJdbcTemplate ");
        List<List<String>> listRow = getListRow(SQL_SELECT, start, step, fields);
        int[] ints = batchUpdate(listRow, SQL_UPDATE);
        return ints;
    }

    private List<List<String>> getListRow(String SQL_SELECT, Integer limit, Integer step, List<String> fields) {
        logger.info("getListRow ");
        return template.query(
                SQL_SELECT,
                (RowMapper<List<String>>) (rs, rowNum) -> {
                    ArrayList<String> row = new ArrayList<>();
                    for (String field : fields) {
                        row.add(Validator.replace(rs.getString(field)));
                    }
                    return row;
                },
                limit,
                step);
    }

    private int[] batchUpdate(List<List<String>> fields, String SQL_UPDATE) {
        logger.info("batchUpdate ");
        List<Object[]> batch = new ArrayList<Object[]>();
        for (List<String> field : fields) {
            Object[] values = field.toArray();
            batch.add(values);
        }

        int[] updateCounts = template.batchUpdate(SQL_UPDATE, batch);
        return updateCounts;
    }
}

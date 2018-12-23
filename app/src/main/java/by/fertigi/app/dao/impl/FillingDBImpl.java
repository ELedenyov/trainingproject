package by.fertigi.app.dao.impl;

import by.fertigi.app.dao.FillingDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.sql.SQLException;

@Component
public class FillingDBImpl implements FillingDB {
    private JdbcTemplate template;

    @Autowired
    public FillingDBImpl(JdbcTemplate template) {
        this.template = template;
    }

    @Override
    @Transactional
    public void doAction() {
        String SQL_INSERT = "INSERT INTO test_table2 (field1, field2, field3, field4) VALUES (?, ?, ?, ?); ";

        template.batchUpdate(SQL_INSERT, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                ps.setString(1, "qwe¥rty");
                ps.setString(2, "asd¥fgh");
                ps.setString(3, "tiuo¥uio");
                ps.setString(4, "jhkl¥hjkl");
            }

            @Override
            public int getBatchSize() {
                return 100;
            }
        });
    }
}

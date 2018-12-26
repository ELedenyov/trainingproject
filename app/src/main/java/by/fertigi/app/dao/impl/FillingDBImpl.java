package by.fertigi.app.dao.impl;

import by.fertigi.app.dao.FillingDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Component
public class FillingDBImpl implements FillingDB {
    private JdbcTemplate template;

    @Autowired
    public FillingDBImpl(JdbcTemplate template) {
        this.template = template;
    }

    @Override
    @Transactional
    public void doAction(Integer batchSize) {
        String SQL_INSERT_patient_info = "INSERT INTO patient_info (" +
                "First_Name, Last_Name, Phone, City, State, ZIP, Address, Gender, DOB" +
                ") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?); ";
        LocalDateTime localDateTime = LocalDateTime.of(LocalDate.of(2018, 12, 24), LocalTime.of(12, 40, 30));


        template.batchUpdate(SQL_INSERT_patient_info, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                ps.setString(1, "qwe¥rty");
                ps.setString(2, "asdf¥gh");
                ps.setString(3, "1234679");
                ps.setString(4, "Gom¥el");
                ps.setString(5, "NY");
                ps.setString(6, "1565498");
                ps.setString(7, "chapae¥va 2 k 5 kv 29");
                ps.setString(8, "male");
                ps.setString(9, localDateTime.toString());
            }

            @Override
            public int getBatchSize() {
                return batchSize;
            }
        });

        String SQL_INSERT_physician_info = "INSERT INTO physician_info (" +
                "First_Name, Last_Name, Phone, City, State, ZIP, Address, NPI" +
                ") VALUES (?, ?, ?, ?, ?, ?, ?, ?); ";


        template.batchUpdate(SQL_INSERT_physician_info, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                ps.setString(1, "qwe¥rty");
                ps.setString(2, "asdf¥gh");
                ps.setString(3, "1234679");
                ps.setString(4, "Gom¥el");
                ps.setString(5, "NY");
                ps.setString(6, "1565498");
                ps.setString(7, "chapae¥va 2 k 5 kv 29");
                ps.setString(8, "123¥455");
            }

            @Override
            public int getBatchSize() {
                return batchSize;
            }
        });

        String SQL_INSERT_insurance_info = "INSERT INTO insurance_info (" +
                "Policy, Group_field" +
                ") VALUES (?, ?); ";


        template.batchUpdate(SQL_INSERT_insurance_info, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                ps.setString(1, "qwe¥rty");
                ps.setString(2, "asdf¥gh");
            }

            @Override
            public int getBatchSize() {
                return batchSize;
            }
        });
    }
}

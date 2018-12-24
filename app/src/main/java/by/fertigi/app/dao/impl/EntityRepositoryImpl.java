package by.fertigi.app.dao.impl;

import by.fertigi.app.dao.EntityRepository;
import by.fertigi.app.service.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class EntityRepositoryImpl implements EntityRepository{
    private JdbcTemplate template;
    private Map<String, List<String>> changeEntity = new HashMap<>();

    @Autowired
    public EntityRepositoryImpl(JdbcTemplate template) {
        this.template = template;
    }

    @Override
    public void selectForUpdate(int selectRow, int step) {
        try {
            template.getDataSource().getConnection().setAutoCommit(false);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        final String query = "select * from test_table2 LIMIT ?, ? for update";
        RowCallbackHandler rch = rs -> {
            // Количество колонок в результирующем запросе
            int columns = rs.getMetaData().getColumnCount();
            // Перебор строк с данными
            String str;
            boolean flag = false;
            do {
                for (int i = 1; i <= columns; i++){
                    str = rs.getString(i);
                    if(!Validator.contains(str)){
                        rs.updateString(i, Validator.replace(str));
                        flag = true;
                    }
                }
                if(flag){
                    rs.updateRow();
                }
            } while(rs.next());
        };

        PreparedStatementCreator psc = conn -> {
            PreparedStatement ps = conn.prepareStatement(query, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_UPDATABLE);
            ps.setInt(1, selectRow);
            ps.setInt(2, step);
            return ps;
        };

        template.query(psc, rch);
    }

    public int countRow(){
        Integer integer = template.queryForObject("select count(*) from patient_info", Integer.class);
        return integer;
    }

    @Transactional
    public void update(int start, int step) throws SQLException {
        String SQL_SELECT = "SELECT id, First_Name, Last_Name, Phone, City, State, ZIP, Address, Gender, DOB from patient_info LIMIT ?, ?";
        String SQL_FIELDS = "First_Name = ?, Last_Name = ?, Phone = ?, City = ?, State = ?, ZIP = ?, Address = ?, Gender = ?, DOB = ?";
        String SQL_UPDATE = "UPDATE patient_info set "+ SQL_FIELDS + " WHERE id = ?";
        Connection connection = template.getDataSource().getConnection();

        connection.setAutoCommit(false);

        PreparedStatement selectPS = connection.prepareStatement(SQL_SELECT);
        selectPS.setInt(1, start);
        selectPS.setInt(2, step);

        PreparedStatement updatePS = connection.prepareStatement(SQL_UPDATE);

        ResultSet resultSet = selectPS.executeQuery();
        int columns = resultSet.getMetaData().getColumnCount();
        System.out.println("columns: " + columns);


        String[] arrReplace = new String[11];


        while (resultSet.next()){
            updatePS.setInt(10, resultSet.getInt("id"));
            for (int i = 2; i <= columns; i++) {
                arrReplace[i] = Validator.replace(resultSet.getString(i));
            }
            updatePS.setString(1, arrReplace[2]);
            updatePS.setString(2, arrReplace[3]);
            updatePS.setString(3, arrReplace[4]);
            updatePS.setString(4, arrReplace[5]);
            updatePS.setString(5, arrReplace[6]);
            updatePS.setString(6, arrReplace[7]);
            updatePS.setString(7, arrReplace[8]);
            updatePS.setString(8, arrReplace[9]);
            updatePS.setString(9, arrReplace[10]);
            updatePS.addBatch();
        }

        updatePS.executeBatch();
        connection.commit();

        resultSet.close();
        selectPS.close();
        updatePS.close();
        connection.close();
    }
}

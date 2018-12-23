package by.fertigi.app.dao.impl;

import by.fertigi.app.dao.EntityRepository;
import by.fertigi.app.service.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Repository;

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
    public void doQuery() {
        int FETCH_SIZE = 500;//РАЗМЕР_ПОРЦИИ_КОТОРОЙ_ПОДКАЧИВАЮТСЯ_ДАННЫЕ
        template.setFetchSize(FETCH_SIZE);
        String sql = "select * from test_table";//нужный sql для этой гигантской выборки
        template.query(sql, rs -> {

            // Количество колонок в результирующем запросе
            int columns = rs.getMetaData().getColumnCount();
            System.out.println("Columns: " + columns);
            // Перебор строк с данными
            String str;
            while(rs.next()){
                for (int i = 1; i <= columns; i++){
                    str = rs.getString(i);
                    if(!Validator.contains(str)){
                        str = Validator.replace(str);
                    }
                    System.out.print(str + "\t");
                }
                System.out.println();
            }
        });
    }

    @Override
    public void selectForUpdate() {
        final String query = "select * from test_table for update";
        final String updateQuery = "update test_table2 set field1 = ?, set field2 = ?, set field3 = ?, set field4 = ?, where id = ?";
        RowCallbackHandler rch = rs -> {
            // Количество колонок в результирующем запросе
            int columns = rs.getMetaData().getColumnCount();
            // Перебор строк с данными
            String str;
            int count = 0;
            boolean flag = false;
            do{
                for (int i = 1; i <= columns; i++){
                    str = rs.getString(i);
                    if(!Validator.contains(str)){
                        rs.updateString(i, Validator.replace(str));
                        flag = true;
                    }
                }
                if(flag){
                    count++;
                    System.out.println(count);
                    rs.updateRow();
                }
            } while(rs.next());
        };

        PreparedStatementCreator psc = conn -> {
            PreparedStatement ps = conn.prepareStatement(query, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_UPDATABLE);
            return ps;
        };

        template.setFetchSize(500);
        template.query(psc, rch);
    }
}

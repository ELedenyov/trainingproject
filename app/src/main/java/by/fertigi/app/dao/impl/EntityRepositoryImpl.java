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

import java.util.ArrayList;
import java.util.List;

@Repository
public class EntityRepositoryImpl implements EntityRepository {
    private static final Logger logger = LogManager.getLogger(EntityRepositoryImpl.class);
    private JdbcTemplate template;

    @Autowired
    public EntityRepositoryImpl(JdbcTemplate template) {
        this.template = template;
    }

    public Integer countRow(String SQL_SELECT_COUNT_ALL) {
        return template.queryForObject(SQL_SELECT_COUNT_ALL, Integer.class);
    }

    @Transactional
    public int[] updateWithJdbcTemplate(Integer start, Integer step, String SQL_SELECT, String SQL_UPDATE, List<String> fields) {
        logger.info("updateWithJdbcTemplate: start");
        logger.info("limit: " + start +
                " step: " + step +
                "\nsql_select: " + SQL_SELECT +
                "\nsql_update: " + SQL_UPDATE
        );
        List<List<String>> listRow = getListRow(SQL_SELECT, start, step, fields);
        int[] ints = batchUpdate(listRow, SQL_UPDATE);
        logger.info("updateWithJdbcTemplate: batch update = " + ints.length);
        return ints;
    }

    private List<List<String>> getListRow(String SQL_SELECT, Integer limit, Integer step, List<String> fields) {
        logger.info("getListRow: " + SQL_SELECT + "; " + "\n" + "limit start: " + limit + " step: " + step);
        return template.query(
                SQL_SELECT,
                (RowMapper<List<String>>) (rs, rowNum) -> {
                    ArrayList<String> row = new ArrayList<>();
                    StringBuilder builder = new StringBuilder();
                    boolean flag = false;
                    for (String field : fields) {
                        String strRow = rs.getString(field);
                        builder.append(strRow);
                        row.add(Validator.replace(strRow));
                    }
                    if(!Validator.contains(builder.toString())){
                        row.add(rs.getString("id"));
                        return row;
                    } else {
                        return null;
                    }
                },
                limit,
                step);
    }

    private int[] batchUpdate(List<List<String>> fields, String SQL_UPDATE) {
        List<Object[]> batch = new ArrayList<Object[]>();
        for (List<String> field : fields) {
            if(field != null){
                Object[] values = field.toArray();
                batch.add(values);
            }
        }
        logger.info("batchUpdate: " + SQL_UPDATE + "; batch update row: " + batch.size());
        return template.batchUpdate(SQL_UPDATE, batch);
    }
}
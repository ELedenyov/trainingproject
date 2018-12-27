package by.fertigi.app.dao.impl;

import by.fertigi.app.dao.EntityRepository;
import by.fertigi.app.rowmapper.RowMapperEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Repository
public class EntityRepositoryImpl implements EntityRepository {
    private static final Logger logger = LoggerFactory.getLogger(EntityRepositoryImpl.class);
    private JdbcTemplate template;

    @Autowired
    public EntityRepositoryImpl(JdbcTemplate template) {
        this.template = template;
    }

    public Integer countRow(String SQL_SELECT_COUNT_ALL) {
        return template.queryForObject(SQL_SELECT_COUNT_ALL, Integer.class);
    }

    @Transactional
    public int[] updateWithJdbcTemplate(Integer start, Integer step, String SQL_SELECT, String SQL_UPDATE, List<String> fields, String idStr){
        logger.info("limit: " + start + " step: " + step + "\nsql_select: " + SQL_SELECT + "\nsql_update: " + SQL_UPDATE);
        List<List<String>> listRow = getListRow(SQL_SELECT, start, step, fields, idStr);
        return batchUpdate(listRow, SQL_UPDATE);
    }

    private List<List<String>> getListRow(String SQL_SELECT, Integer limit, Integer step, List<String> fields, String idStr) {
        return template.query(SQL_SELECT, new RowMapperEntity(fields, idStr), limit, step);
    }

    private int[] batchUpdate(List<List<String>> listFields, String SQL_UPDATE) {
        List<Object[]> batch = new ArrayList<Object[]>();
        for (List<String> fields : listFields) {
            if(fields != null & fields.size() == 0){
                Object[] values = fields.toArray();
                batch.add(values);
            }
        }
        logger.info("batchUpdate: " + SQL_UPDATE + ";\nbatch update row: " + batch.size());
        return template.batchUpdate(SQL_UPDATE, batch);
    }
}
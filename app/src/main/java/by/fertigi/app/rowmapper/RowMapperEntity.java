package by.fertigi.app.rowmapper;

import by.fertigi.app.util.Validator;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RowMapperEntity implements RowMapper<List<String>> {
    private List<String> fields;
    private String idStr;

    public RowMapperEntity(List<String> fields, String idStr) {
        this.fields = fields;
        this.idStr = idStr;
    }


    @Override
    public List<String> mapRow(ResultSet rs, int rowNum) throws SQLException {
        ArrayList<String> row = new ArrayList<>();
        StringBuilder builder = new StringBuilder();
        boolean flag = false;
        for (String field : fields) {
            String strRow = rs.getString(field);
            builder.append(strRow);
            row.add(Validator.replace(strRow));
        }
        if(!Validator.contains(builder.toString())){
            row.add(rs.getString(idStr));
            return row;
        } else {
            return null;
        }
    }
}

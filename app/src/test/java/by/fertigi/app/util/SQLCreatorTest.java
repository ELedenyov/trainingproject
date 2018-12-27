package by.fertigi.app.util;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class SQLCreatorTest {
    String tableName = "table_name";
    List<String> listFields = new ArrayList<>();
    String idStr = "id_table_name";

    @Before
    public void init(){
        listFields.add("field1");
        listFields.add("field2");
        listFields.add("field3");
        listFields.add("field4");
        listFields.add("field5");
    }

    @Test
    public void sqlSelectCreator() {
        String SQL_SELECT = "select field1, field2, field3, field4, field5, id_table_name from table_name LIMIT ?, ?";

        assertEquals(SQLCreator.sqlSelectCreator(tableName, listFields, idStr), SQL_SELECT);
    }

    @Test
    public void sqlUpdateCreator() {
        String SQL_UPDATE = "update table_name set field1 = ?, field2 = ?, field3 = ?, field4 = ?, field5 = ? WHERE id_table_name = ?";
        assertEquals(SQLCreator.sqlUpdateCreator(tableName, listFields, idStr), SQL_UPDATE);
    }

    @Test
    public void sqlSelectCountAllCreator() {
        String SQL_SELECT_COUNT_ALL = "select count(*) from table_name";
        assertEquals(SQLCreator.sqlSelectCountAllCreator(tableName), SQL_SELECT_COUNT_ALL);
    }
}
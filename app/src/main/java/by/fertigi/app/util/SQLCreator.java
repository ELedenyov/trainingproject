package by.fertigi.app.util;

import java.util.List;

public class SQLCreator {
    /**
     * This method create sql for select fields from table_name
     * @param tableName
     * @param listFields
     * @return String sql for select fields from tableName
     */
    public static String sqlSelectCreator(String tableName, List<String> listFields) {
        StringBuilder sqlBilder = new StringBuilder("select ");
        sqlBilder
                .append(
                        String.join(", ", listFields)
                )
                .append(", id")
                .append(" from ")
                .append(tableName)
                .append(" LIMIT ?, ?");
        return sqlBilder.toString();
    }

    /**
     * This method create sql for update fields from table_name
     * @param tableName
     * @param listFields
     * @return String sql for update fields from tableName
     */
    public static String sqlUpdateCreator(String tableName, List<String> listFields){
        StringBuilder sqlBilder = new StringBuilder("update ");
        sqlBilder
                .append(tableName)
                .append(" set ")
                .append(
                        String.join(" = ?, ", listFields)
                )
                .append(" = ?")
                .append(" WHERE id = ?");
        return sqlBilder.toString();
    }

    /**
     * Method of counting the number of records in the database
     * @param tableName
     * @return String sql select count(*) from table_name
     */
    public static String sqlSelectCountAllCreator(String tableName){
        return "select count(*) from " + tableName;
    }
}

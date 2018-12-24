package by.fertigi.app.util;

import java.util.List;

public class SQLCreator {
    /**
     * This metod create sql for select fields from table_name
     * @param tableName
     * @param listFields
     * @return String sql for select fields from tableName
     */
    public static String sqlSelectCreator(String tableName, List<String> listFields) {
        StringBuilder sqlBilder = new StringBuilder("select id, ");
        sqlBilder
                .append(
                        String.join(", ", listFields)
                )
                .append(" from ")
                .append(tableName)
                .append(" LIMIT ?, ?");
        return sqlBilder.toString();
    }

    /**
     *
     * @param tableName
     * @param listFields
     * @return
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
}

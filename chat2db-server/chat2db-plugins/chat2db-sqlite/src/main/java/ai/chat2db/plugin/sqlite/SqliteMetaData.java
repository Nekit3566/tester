package ai.chat2db.plugin.sqlite;

import ai.chat2db.spi.MetaData;
import ai.chat2db.spi.jdbc.DefaultMetaService;
import ai.chat2db.spi.sql.SQLExecutor;
import com.google.common.collect.Lists;

import java.sql.SQLException;
import java.util.List;

public class SqliteMetaData extends DefaultMetaService implements MetaData {
    @Override
    public String tableDDL(String databaseName, String schemaName, String tableName) {
        String sql = "SELECT sql FROM sqlite_master WHERE type='table' AND name='" + tableName + "'";
        return SQLExecutor.getInstance().executeSql(sql, resultSet -> {
            try {
                if (resultSet.next()) {
                    return resultSet.getString("sql");
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            return null;
        });
    }
    @Override
    public List<String> databases() {
        return Lists.newArrayList("main");
    }

    @Override
    public List<String> schemas(String databaseName) {
        return Lists.newArrayList();
    }
}

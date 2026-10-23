package de.lachcrafter.tns.common.database;

import de.lachcrafter.tns.common.util.LoggerUtil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private Connection sqlConnection;

    public DatabaseResult startConnection(ConnectionDetails connectionDetails) {
        try {
            sqlConnection = DriverManager.getConnection(
                    connectionDetails.getFormattedUrlWithDatabase(),
                    connectionDetails.user(),
                    connectionDetails.password()
            );
        } catch (SQLException e) {

            LoggerUtil.getLogger().severe(e.getMessage() + " State: " + e.getSQLState());
            return DatabaseResult.FAIL;
        }

        return DatabaseResult.SUCCESS;
    }

    public Connection getSqlConnection() {

        return sqlConnection;
    }
}

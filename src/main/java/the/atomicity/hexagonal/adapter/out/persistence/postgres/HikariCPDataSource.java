package the.atomicity.hexagonal.adapter.out.persistence.postgres;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class HikariCPDataSource {

    public static HikariDataSource INSTANCE;

    static HikariDataSource getHikariDataSource() {
        return new HikariDataSource(getHikariConfig());
    }

    static HikariConfig getHikariConfig() {
        final HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:postgresql://localhost:5432/postgres");
        config.setUsername("postgres");
        config.setPassword("postgres");
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        return config;
    }

    private HikariCPDataSource() {
    }

    public static Connection getConnection() throws SQLException {
        if (INSTANCE == null)
            INSTANCE = getHikariDataSource();
        return INSTANCE.getConnection();
    }

    public static void closeConnection() {
        INSTANCE.close();
    }
}
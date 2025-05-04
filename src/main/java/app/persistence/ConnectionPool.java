package app.persistence;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/***
 * Singleton pattern applied to handling a Hikari ConnectionPool
 */
public class  ConnectionPool {

    private static volatile ConnectionPool instance = null;
    private static HikariDataSource ds = null;
    private static final Logger LOGGER = Logger.getLogger(ConnectionPool.class.getName());

    /***
     * Private constructor to enforce Singleton pattern.
     */
    private ConnectionPool() {
        // Prevent instantiation
    }

    /***
     * Getting a singleton instance of a Hikari Connection Pool with specific credentials
     * and connection string. If an environment variable "DEPLOYED" exists, then environment variables
     * will be used instead of provided parameters.
     * @param user Database username
     * @param password Database password
     * @param url Database connection URL
     * @param db Database name
     * @return Singleton instance of ConnectionPool
     */

    /**
     * The {@code connectionPool} is a shared resource that manages a pool of reusable database connections.
     * <p>
     * Motivation:
     * <ul>
     *   <li><b>Efficiency:</b> Avoids the overhead of opening and closing connections repeatedly by reusing a fixed set.</li>
     *   <li><b>Scalability:</b> Improves performance in multi-user environments by reducing latency and managing concurrent access.</li>
     *   <li><b>Resource Management:</b> Prevents resource exhaustion by limiting the maximum number of simultaneous connections.</li>
     *   <li><b>Centralized Access:</b> Ensures consistent and controlled connection usage across all data access classes.</li>
     * </ul>
     */
    public static ConnectionPool getInstance(String user, String password, String url, String db) {
        if (instance == null) {
            synchronized (ConnectionPool.class) {
                if (instance == null) {  // Double-checked locking
                    if (System.getenv("DEPLOYED") != null) {
                        ds = createHikariConnectionPool(
                                System.getenv("JDBC_USER"),
                                System.getenv("JDBC_PASSWORD"),
                                System.getenv("JDBC_CONNECTION_STRING"),
                                System.getenv("JDBC_DB"));
                    } else {
                        ds = createHikariConnectionPool(user, password, url, db);
                    }
                    instance = new ConnectionPool();
                }
            }
        }
        return instance;
    }

    /***
     * Getting a live connection from the Hikari Connection Pool
     * @return a database connection
     * @throws SQLException if connection fails
     */
    public Connection getConnection() throws SQLException {
        if (ds == null) {
            throw new SQLException("DataSource is not initialized. Call getInstance() first.");
        }
        return ds.getConnection();
    }

    /***
     * Closing the Hikari Connection Pool
     */
    public void close() {
        if (ds != null) {
            LOGGER.log(Level.INFO, "Shutting down connection pool...");
            ds.close();
            ds = null;
            instance = null;
        }
    }

    /***
     * Configuring a Hikari DataSource ConnectionPool
     * @param user Database username
     * @param password Database password
     * @param url Database connection URL
     * @param db Database name
     * @return Configured HikariDataSource
     */
    private static HikariDataSource createHikariConnectionPool(String user, String password, String url, String db) {
        LOGGER.log(Level.INFO, "Initializing Connection Pool for database: {0}", db);

        HikariConfig config = new HikariConfig();
        config.setDriverClassName("org.postgresql.Driver");
        config.setJdbcUrl(String.format(url, db));
        config.setUsername(user);
        config.setPassword(password);

        // Connection Pool Configurations
        config.setMaximumPoolSize(10); // Default is 10
        config.setMinimumIdle(2);      // Ensures some connections are always available
        config.setIdleTimeout(30000);  // 30 seconds idle timeout
        config.setConnectionTimeout(30000); // Max wait time for a connection
        config.setPoolName("Postgresql-Pool");

        // Optimizations
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");

        return new HikariDataSource(config);
    }
}
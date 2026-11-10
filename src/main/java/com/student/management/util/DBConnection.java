package com.student.management.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.io.InputStream;

/**
 * Utility for establishing database connections.
 */
public class DBConnection
{
    private static final Properties props = new Properties();

    static
    {
        try (InputStream input = DBConnection.class.getClassLoader().getResourceAsStream("database.properties"))
        {
            if (input != null)
            {
                props.load(input);
            }
            else
            {
                throw new RuntimeException("Failed to locate database.properties in classpath.");
            }
        }
        catch (Exception e)
        {
            throw new RuntimeException("Error loading database configuration.", e);
        }
    }

    /**
     * Obtains an active database connection.
     *
     * @return active {@link Connection} instance
     * @throws SQLException if connection credentials are missing or database server is unreachable
     */
    public static Connection getConnection() throws SQLException
    {
        String url = props.getProperty("db.url");
        String username = props.getProperty("db.username");
        String password = props.getProperty("db.password");

        if (url == null || username == null || password == null)
        {
            throw new SQLException("Database credentials are missing in database.properties.");
        }

        return DriverManager.getConnection(url, username, password);
    }
}

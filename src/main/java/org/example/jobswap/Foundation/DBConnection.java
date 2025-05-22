package org.example.jobswap.Foundation;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * class to connect to Database
 */
public class DBConnection {

        private static final String URL = "jdbc:sqlserver://localhost;databaseName=Danfoss Jobswap DB";
        private static final String USERNAME = "sa";
        private static final String PASSWORD = "Abcd1234!";
        private static Connection conn;

        /**
         * Establishes connection to the Database.
         * @return return the connection to the Database.
         */
        public static Connection getConnection() {
            if (conn == null) {
                try
                {
                    Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                    conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
                    System.out.println("Connected to the database.");
                }
                catch (Exception e)
                {
                    System.out.println("Failed to connect to database.");
                }
            }
            return conn;
        }

        private DBConnection() {

        }

}


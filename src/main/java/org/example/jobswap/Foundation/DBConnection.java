package org.example.jobswap.Foundation;

import org.example.jobswap.Model.AccessLevel;
import java.sql.Connection;
import java.sql.DriverManager;

/**
 * class to connect to Database
 */
public class DBConnection {

        private static final String URL = "jdbc:sqlserver://localhost;databaseName=Danfoss Jobswap DB";
        private static String USERNAME = "LoginProfile";
        private static String PASSWORD = "Login123456!";
        private static Connection conn;

        /**
         * Establishes connection to the Database.
         * @return the connection to the Database.
         */
        public static Connection getConnection() {
            if (conn == null) {
                try
                {
                    Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                    conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
                    if (!conn.isClosed())
                    {
                        System.out.println("Connected to database");
                    }
                    else
                    {
                        System.out.println("Not connected to database");
                        conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
                    }
                }
                catch (Exception e)
                {
                    System.out.println("Failed to connect to database.");
                }
            }

            try {
                if (conn.isClosed())
                {
                    conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
                }
            }
            catch (Exception e)
            {
                System.out.println("Failed to connect to database.");
                e.printStackTrace();
                System.out.println(e.getMessage());
                System.exit(0);
            }


            return conn;
        }

        private DBConnection() {
        }

    /**
     * gives the user their acces based on their {@link AccessLevel}
     * @param accessLevel the level the employee has
     */
        public static void changeAccessLevelOnDatabase(AccessLevel accessLevel) {

            switch (accessLevel) {
                case EMPLOYEE: USERNAME = "UserProfile"; PASSWORD = "User123456!";break;
                case HR: USERNAME = "HRProfile"; PASSWORD = "Hr123456!"; break;
                case SYSADMIN: USERNAME = "SAProfile"; PASSWORD = "Sa123456!"; break;
                default: break;
            }
            if (conn != null){
                conn = null;
            }
        }
}


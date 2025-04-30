


import java.sql.Connection;
import java.sql.DriverManager;
import oracle.jdbc.OraclePreparedStatement;
import oracle.jdbc.OracleResultSet;

import javax.swing.JOptionPane;
public class ConnectDb {

    public static Connection setupConnection()
    {
        /*
         Specify the database you would like to connect with:
         - protocol (e.g., jdbc)
         - vendor (e.g., oracle)
         - driver (e.g., thin)
         - server (e.g., csitoracle.eku.edu)
         - port number (e.g., default 1521)
         - database instance name (e.g., cscpdb)
         */

        String jdbcDriver = "oracle.jdbc.driver.OracleDriver";
        String jdbcUrl = "jdbc:oracle:thin:@//csitoracle.eku.edu:1521/cscpdb";  // URL for the database

 	/*
         Specify the user account you will use to connect with the database:
         - username (e.g., myName)
         - password (e.g., myPassword)
         */
        String username = "HardwickJ545";
        String password = "Pa$$0253";

        try
        {
            // Load jdbc driver.
            Class.forName(jdbcDriver);

            // Connect to the Oracle database
            Connection conn = DriverManager.getConnection(jdbcUrl, username, password);
            return conn;
        }
        catch (Exception e)
        {
            JOptionPane.showMessageDialog(null, e);
        }
        return null;
    }

    static void close(Connection conn)
    {
        if(conn != null)
        {
            try
            {
                conn.close();
            }
            catch(Throwable whatever)
            {}
        }
    }

    static void close(OraclePreparedStatement st)
    {
        if(st != null)
        {
            try
            {
                st.close();
            }
            catch(Throwable whatever)
            {}
        }
    }

    static void close(OracleResultSet rs)
    {
        if(rs != null)
        {
            try
            {
                rs.close();
            }
            catch(Throwable whatever)
            {
                // do nothing
            }
        }
    }
}
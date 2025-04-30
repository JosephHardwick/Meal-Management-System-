

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.

import java.sql.Connection;
import javax.swing.JOptionPane;
import oracle.jdbc.OraclePreparedStatement;
import oracle.jdbc.OracleResultSet;

public class Main {
    public static void main(String[] args) {

        Connection conn = null;
        OraclePreparedStatement pst = null;
        OracleResultSet rs = null;

        conn = ConnectDb.setupConnection();


        try
        {
            String sqlStatement = "select * from coffees";

            pst = (OraclePreparedStatement) conn.prepareStatement(sqlStatement);
//            pst.setFloat(1, Float.valueOf(lowestPriceField.getText()));
//            pst.setFloat(2, Float.valueOf(highestPriceField.getText()));

            rs = (OracleResultSet) pst.executeQuery();
            // Now rs contains the rows from the COFFEES table. To access the data, use the method NEXT to access all rows in rs, one row at a time
            //System.out.println("Coffees between " + lowestPriceField.getText() + " and " + highestPriceField.getText() + " dolloars include:");
            while (rs.next())
            {
                String name = rs.getString("COF_NAME");
                float price = rs.getFloat("PRICE");

                // print names and prices and left align them
                System.out.printf("%-32s%-5.2f\n", name, price);
            }
        }
        catch (Exception e)
        {
            JOptionPane.showMessageDialog(null, e);
        }
        finally
        {
            ConnectDb.close(rs);
            ConnectDb.close(pst);
            ConnectDb.close(conn);
        }

    }
}
import oracle.jdbc.OraclePreparedStatement;
import oracle.jdbc.OracleResultSet;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;

public class RecipeManager {
    private JTable recipeTable;
    private JButton editRecipeButton;
    private JButton addRecipeButton;
    private JPanel mainPanel;

    public RecipeManager() {
        populateTable();
        recipeTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                int row = recipeTable.getSelectedRow();
                String name = (String) recipeTable.getValueAt(row, 0);
                SwingUtilities.invokeLater(() -> new RecipeView(name).showMenu());


            }
        });
    }

    public void showMenu(){
        JFrame frame = new JFrame("Recipe Manager");
        frame.setContentPane(mainPanel);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }


    private void populateTable() {

        recipeTable.setModel(new javax.swing.table.DefaultTableModel());
        String[] columnNames = {"name", "category"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);


        Connection conn = null;
        OraclePreparedStatement pst = null;
        OracleResultSet rs = null;

        conn = ConnectDb.setupConnection();

        try
        {

            String sqlStatement = "select * from Meal";

            pst = (OraclePreparedStatement) conn.prepareStatement(sqlStatement);

            rs = (OracleResultSet) pst.executeQuery();

            while (rs.next())
            {
                String name = rs.getString("NAME");
                String category = rs.getString("CATEGORY");


                // print names and prices and left align them
                // listModel.addElement(name);
                tableModel.addRow(new Object[]{name, category});

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


        // Set the model to the JList
        recipeTable.setModel(tableModel);
    }
}

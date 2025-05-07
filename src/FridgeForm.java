import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import oracle.jdbc.OraclePreparedStatement;
import oracle.jdbc.OracleResultSet;

public class FridgeForm {
    private JPanel mainPanel;
    private JTable table1;
    private JLabel titleLabel;
    private JButton deleteItemButton;
    private JButton addItemButton;


    public FridgeForm() {

        populateTable();
        deleteItemButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removeFromFridge();
            }
        });
        addItemButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showAddItemDialog();
            }
        });
    }

    public void showMenu(){
        JFrame frame = new JFrame("Meal Management System");
        frame.setContentPane(mainPanel);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        table1.setShowGrid(true);
        table1.setGridColor(Color.BLACK);
    }

    private void removeFromFridge(){
        int row = table1.getSelectedRow();
        if (row != -1) {
            String name = (String) table1.getValueAt(row, 0);
            Connection conn = null;
            OraclePreparedStatement pst = null;
            OracleResultSet rs = null;

            conn = ConnectDb.setupConnection();

            try
            {
                String sqlStatement = "update Ingredient set has = 0 where name = ?";

                pst = (OraclePreparedStatement) conn.prepareStatement(sqlStatement);
                pst.setString(1, name);

                int rowsUpdated = pst.executeUpdate();
                if (rowsUpdated > 0) {
                    //JOptionPane.showMessageDialog(null, "Item removed from fridge.");
                } else {
                    JOptionPane.showMessageDialog(null, "Item not found in fridge.");
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
        } else {
            JOptionPane.showMessageDialog(null, "Please select an item to remove.");
        }

        populateTable();
    }




    private void populateTable() {

        table1.setModel(new javax.swing.table.DefaultTableModel());
        String[] columnNames = {"Ingredient", "Protein", "Sugar", "sodium", "Fat", "Calories"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);


        Connection conn = null;
        OraclePreparedStatement pst = null;
        OracleResultSet rs = null;

        conn = ConnectDb.setupConnection();

        try
        {

            String sqlStatement = "select * from Ingredient where has = 1";


            pst = (OraclePreparedStatement) conn.prepareStatement(sqlStatement);

            rs = (OracleResultSet) pst.executeQuery();

            while (rs.next())
            {
                String name = rs.getString("NAME");
                String protein = rs.getString("PROTEIN");
                String sugar = rs.getString("SUGAR");
                String sodium = rs.getString("SODIUM");
                String fat = rs.getString("FAT");
                String calories = rs.getString("CALORIES");

                // print names and prices and left align them
                // listModel.addElement(name);
                tableModel.addRow(new Object[]{name, protein, sugar, sodium, fat, calories});
                System.out.println(name);;
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
        table1.setModel(tableModel);
    }

    private void showAddItemDialog() {
        JDialog dialog = new JDialog((JFrame) SwingUtilities.getWindowAncestor(mainPanel), "Add Item", true);
        dialog.setSize(300, 200);
        dialog.setLocationRelativeTo(mainPanel);
        dialog.setLayout(new BoxLayout(dialog.getContentPane(), BoxLayout.Y_AXIS));

        JLabel label = new JLabel("Select an item to add:");
        JComboBox<String> foodDropdown = new JComboBox<>();
        JButton selectButton = new JButton("Add to Fridge");

        //center button and label horizontally
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        selectButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Populate dropdown with available foods from DB
        Connection conn = null;
        OraclePreparedStatement pst = null;
        OracleResultSet rs = null;

        conn = ConnectDb.setupConnection();
        try {
            String sqlStatement = "SELECT name FROM Ingredient WHERE has = 0";
            pst = (OraclePreparedStatement) conn.prepareStatement(sqlStatement);
            rs = (OracleResultSet) pst.executeQuery();

            while (rs.next()) {
                foodDropdown.addItem(rs.getString("name"));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        } finally {
            ConnectDb.close(rs);
            ConnectDb.close(pst);
            ConnectDb.close(conn);
        }

        selectButton.addActionListener(e -> {
            String selectedFood = (String) foodDropdown.getSelectedItem();
            if (selectedFood != null) {
                addToFridge(selectedFood);
                dialog.dispose();
            } else {
                JOptionPane.showMessageDialog(dialog, "Please select an item.");
            }
        });

        dialog.add(label);
        dialog.add(foodDropdown);
        dialog.add(selectButton);
        dialog.setVisible(true);
    }

    private void addToFridge(String foodName) {
        Connection conn = null;
        OraclePreparedStatement pst = null;

        conn = ConnectDb.setupConnection();
        try {
            String sqlStatement = "UPDATE Ingredient SET has = 1 WHERE name = ?";
            pst = (OraclePreparedStatement) conn.prepareStatement(sqlStatement);
            pst.setString(1, foodName);

            int rowsUpdated = pst.executeUpdate();
            if (rowsUpdated > 0) {
               // JOptionPane.showMessageDialog(null, "Item added to fridge.");
            } else {
                JOptionPane.showMessageDialog(null, "Failed to add item.");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        } finally {
            ConnectDb.close(pst);
            ConnectDb.close(conn);
        }

        populateTable(); // Refresh the table
    }


}

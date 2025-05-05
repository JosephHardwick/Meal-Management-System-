import oracle.jdbc.OraclePreparedStatement;
import oracle.jdbc.OracleResultSet;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.util.LinkedList;

public class MealPlan {
    private JTable table1;
    private JButton viewShoppingListButton;
    private JPanel mainPanel;
    private JLabel titleLabel;

    private int curRow;
    private int curCol;

    //linked list to hold the selected items
    private LinkedList<String> foods = new LinkedList<>();


    private DefaultTableModel tableModel;

    public MealPlan() {
        String[] columnNames = {"Day", "Breakfast", "Lunch", "Dinner"};
        Object[][] data = {
                {"Monday", "", "", ""},
                {"Tuesday", "", "", ""},
                {"Wednesday", "", "", ""},
                {"Thursday", "", "", ""},
                {"Friday", "", "", ""},
                {"Saturday", "", "", ""},
                {"Sunday", "", "", ""}
        };

         tableModel = new DefaultTableModel(data, columnNames);
        table1.setModel(tableModel);
        table1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent ev) {
                super.mouseClicked(ev);
                curRow = table1.getSelectedRow();
                curCol = table1.getSelectedColumn();

               showAddItemDialog();

            }
        });
        viewShoppingListButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                outerLoop:
                for (int i = 0; i < tableModel.getRowCount(); i++) {
                    for (int j = 0; j < tableModel.getColumnCount(); j++) {
                    if (tableModel.getValueAt(i, j) != null && !tableModel.getValueAt(i, j).toString().isEmpty()) {
                            String food = tableModel.getValueAt(i, j).toString();
                            foods.add(food);
                        }
                    else {
                        //popup to say that a meal is missing
                        JOptionPane.showMessageDialog(null, "Please select a meal for all days");
                        break outerLoop;
                    }
                    }
                }
                LinkedList foods2 = new LinkedList<>();
                String[] days = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
                for (String food : foods) {
                    boolean isDay = false;
                    for (String day : days) {
                        if (food.equalsIgnoreCase(day)) {
                            isDay = true;
                            System.out.println(food+"not inserted");

                            break;
                        }
                    }
                    if (!isDay) {

                        foods2.add(food);
                        System.out.println("Inserting food: " + food + " into param list");
                    }
                }

                SwingUtilities.invokeLater(() -> new ShoppingList(foods2).showMenu());


            }


        });
    }

    public void showMenu(){
        JFrame frame = new JFrame("Meal Planner");
        frame.setContentPane(mainPanel);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        table1.setShowGrid(true);
        table1.setGridColor(Color.BLACK);


    }

    private void showAddItemDialog() {
        JDialog dialog = new JDialog((JFrame) SwingUtilities.getWindowAncestor(mainPanel), "Add Meal", true);
        dialog.setSize(300, 200);
        dialog.setLocationRelativeTo(mainPanel);
        dialog.setLayout(new BoxLayout(dialog.getContentPane(), BoxLayout.Y_AXIS));

        JLabel label = new JLabel("Select an item to add:");
        JComboBox<String> foodDropdown = new JComboBox<>();
        JButton selectButton = new JButton("Add to Meal Plan");

        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        selectButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Populate dropdown with available foods from DB
        Connection conn = null;
        OraclePreparedStatement pst = null;
        OracleResultSet rs = null;

        conn = ConnectDb.setupConnection();
        try {
            String sqlStatement = "SELECT * FROM Meal ";
            pst = (OraclePreparedStatement) conn.prepareStatement(sqlStatement);
            rs = (OracleResultSet) pst.executeQuery();

            while (rs.next()) {
                String name = rs.getString("name");

                String cat = rs.getString("category");
                foodDropdown.addItem(name+" : "+cat);
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
                String food = selectedFood.substring(0, selectedFood.indexOf(':')-1);
                tableModel.setValueAt(food, curRow, curCol);
                //System.out.println(food);

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
}

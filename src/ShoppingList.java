import oracle.jdbc.OraclePreparedStatement;
import oracle.jdbc.OracleResultSet;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.util.LinkedList;

public class ShoppingList {
    private JPanel mainPanel;
    private JButton closeButton;
    private JList list1;
    private LinkedList<String> foods;





    public ShoppingList(LinkedList<String> l) {

        System.out.println("start shopping list");

        System.out.println("l size: " + l.size());
        if (l.size() > 21) {
            foods = new LinkedList<>(l.subList(l.size() - 21, l.size()));
        } else {
            foods = l;
        }
        System.out.println("foods size: " + foods.size());

        Connection conn = null;
        OraclePreparedStatement pst = null;
        OracleResultSet rs = null;
        DefaultListModel<String> listModel = new DefaultListModel<>();

        conn = ConnectDb.setupConnection();
        try {
            String sqlStatement = "SELECT DISTINCT i.name AS ingredient_name " +
                    "FROM Meal m " +
                    "         JOIN MealIngredient mi ON m.mealID = mi.meal_ID " +
                    "         JOIN Ingredient i ON mi.ingredient_ID = i.ingredientID " +
                    "WHERE m.name IN (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) " +
                    "  AND i.has = 0 " +
                    "ORDER BY i.name";

            pst = (OraclePreparedStatement) conn.prepareStatement(sqlStatement);

            int index = 1;


            System.out.println("foods size: " + foods.size());
            System.out.println("start filter logic");
            // Filter out day names

            for (String food : foods) {



                    pst.setString(index, food);
                    index++;
                    System.out.println("Inserting food: " + food + " into param list");

            }
            System.out.println("exicuting query");
            rs = (OracleResultSet) pst.executeQuery();



            while (rs.next()) {

                System.out.println("add output to list");
                String name = rs.getString("ingredient_name");
                //add to swing list
                listModel.addElement(name);


            }
            list1.setModel(listModel);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        } finally {
            ConnectDb.close(rs);
            ConnectDb.close(pst);
            ConnectDb.close(conn);
        }
        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //close form

                ((JFrame) SwingUtilities.getWindowAncestor(mainPanel)).dispose();
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



    }
}

import oracle.jdbc.OraclePreparedStatement;
import oracle.jdbc.OracleResultSet;

import javax.swing.*;
import java.sql.Connection;

public class RecipeView {

    public RecipeView(String s) {
        // Initialize components here
        mealName = s;
        populateTable();
    }
    private String mealName;
    private JTextArea textArea1;
    private JList<String> list1;
    private JPanel mainPanel;
    private JLabel nameLabel;
    private JLabel categoryLabel;


    public void showMenu(){
        JFrame frame = new JFrame("Recipe for " + mealName);
        frame.setContentPane(mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

private void populateTable() {
    DefaultListModel<String> listModel = new DefaultListModel<>();

    list1.setModel(listModel);



    JList<String> list = new JList<>(listModel);

    Connection conn = null;
    OraclePreparedStatement pst = null;
    OracleResultSet rs = null;

    conn = ConnectDb.setupConnection();

    try
    {

        String sqlStatement = "select * from meal where name = ? ";


        pst = (OraclePreparedStatement) conn.prepareStatement(sqlStatement);

        pst.setString(1, mealName);

        rs = (OracleResultSet) pst.executeQuery();

        if (rs.next())
        {
            System.out.println("rs next exec");
            String name = rs.getString("NAME");
            String category = rs.getString("category");
            String instruction = rs.getString("instruction");

            nameLabel.setText(name + " Recipe");
            categoryLabel.setText("Category: "+category);
            textArea1.setText(instruction);


            System.out.println(name);;
        }
        else{
            System.out.println("rs next not exec");
        }

        ConnectDb.close(rs);
        ConnectDb.close(pst);


        // Second query
        String secondQuery = "select INGREDIENT.name from meal inner join mealingredient on meal.MEALID = mealingredient.meal_id " +
                "INNER JOIN ingredient ON mealingredient.ingredient_id = ingredient.INGREDIENTID " +
                "where MEAL.NAME = ?";
        pst = (OraclePreparedStatement) conn.prepareStatement(secondQuery);
        pst.setString(1, mealName);

        rs = (OracleResultSet) pst.executeQuery();

        while (rs.next()) {
            String name = rs.getString("NAME");

            listModel.addElement(name);
            System.out.println(name);

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
    list1.setModel(listModel);
}

}

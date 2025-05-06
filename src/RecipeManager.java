import oracle.jdbc.OraclePreparedStatement;
import oracle.jdbc.OracleResultSet;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.util.Vector;

public class RecipeManager {
    private JTable recipeTable;
    private JButton editRecipeButton;
    private JButton addRecipeButton;
    private JPanel mainPanel;
    private JTextField searchField;
    private JList ingredientList;
    private JList categoryList;
    private DefaultTableModel tableModel;
    private Vector<Vector<Object>> originalData;

    public RecipeManager() {
        populateTable();
        setupSearchField();

        recipeTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = recipeTable.getSelectedRow();
                String name = (String) recipeTable.getValueAt(row, 0);
                SwingUtilities.invokeLater(() -> new RecipeView(name).showMenu());
            }
        });
        ingredientList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                filterTable();
            }
        });

        categoryList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                filterTable();
            }
        });
    }

    private void setupSearchField() {
        searchField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                filterTable();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                filterTable();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                filterTable();
            }
        });
    }

    private void filterTable() {
        String searchText = searchField.getText().toLowerCase();
        Object selectedCategory = categoryList.getSelectedValue();
        String categoryFilter = selectedCategory != null ? selectedCategory.toString().toLowerCase() : "";
        java.util.List<Object> selectedIngredients = ingredientList.getSelectedValuesList();

        Vector<Vector<Object>> filteredData = new Vector<>();

        for (Vector<Object> row : originalData) {
            String name = row.get(0).toString().toLowerCase();
            String category = row.get(1).toString().toLowerCase();

            boolean name_search = name.contains(searchText);
            boolean category_search = categoryFilter.isEmpty() || category.contains(categoryFilter);

            boolean ingredients_search = selectedIngredients.isEmpty() || //if nothing is selected
                    meal_ingredients.stream().anyMatch(mealRow -> mealRow.get(0).equals(row.get(0)) && //check if meal name matches between meal_ingredients and the row
                            selectedIngredients.stream().allMatch(ingredient -> mealRow.contains(ingredient)));//additonally check if all selected ingredients are in the meal_ingredients vector

            if (name_search && category_search && ingredients_search) {
                filteredData.add(row);
            }
        }

        tableModel.setDataVector(filteredData, getColumnNames());
    }


    private Vector<Vector<Object>> meal_ingredients; //store meals and associated ingredients
    private void populateTable() {
        String[] columnNames = {"name", "category"};
        tableModel = new DefaultTableModel(columnNames, 0);
        originalData = new Vector<>();
        meal_ingredients = new Vector<>();

        Connection conn = null;
        OraclePreparedStatement pst = null;
        OracleResultSet rs = null;
        conn = ConnectDb.setupConnection();

        try {
            String sqlStatement = "SELECT meal.name AS meal_name, category, ingredient.name AS ingredient_name " +
                    "FROM meal LEFT JOIN mealingredient ON meal.mealID = mealingredient.meal_id LEFT JOIN ingredient ON mealingredient.ingredient_id = ingredient.ingredientID";
            pst = (OraclePreparedStatement) conn.prepareStatement(sqlStatement);
            rs = (OracleResultSet) pst.executeQuery();

            while (rs.next()) {
                String mealName = rs.getString("meal_name");
                String category = rs.getString("category");
                String ingredient = rs.getString("ingredient_name");

                // Add to originalData for table display
                Vector<Object> existingRow = null;
                for (Vector<Object> row : originalData) {
                    if (row.get(0).equals(mealName)) {
                        existingRow = row;
                        break;
                    }
                }

                if (existingRow == null) {
                    Vector<Object> row = new Vector<>();
                    row.add(mealName);
                    row.add(category);
                    originalData.add(row);
                }


                Vector<Object> existingMealRow = null;//add ingredients to meal_ingredients vector
                for (Vector<Object> mealRow : meal_ingredients) {
                    if (mealRow.get(0).equals(mealName)) {
                        existingMealRow = mealRow;
                        break;
                    }
                }

                if (existingMealRow == null) {//if meal name not found in meal_ingredients vector, create a new row
                    Vector<Object> mealRow = new Vector<>();
                    mealRow.add(mealName);
                    mealRow.add(ingredient);
                    meal_ingredients.add(mealRow);
                } else {
                    existingMealRow.add(ingredient);//if meal name found, add the ingredient to the existing row
                }
            }

            rs.close();
            pst.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        tableModel.setDataVector(originalData, getColumnNames());
        recipeTable.setModel(tableModel);
    }

    private Vector<String> getColumnNames() {
        Vector<String> columnNames = new Vector<>();
        columnNames.add("name");
        columnNames.add("category");
        return columnNames;
    }

    public void showMenu() {
        JFrame frame = new JFrame("Recipe Manager");
        frame.setContentPane(mainPanel);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
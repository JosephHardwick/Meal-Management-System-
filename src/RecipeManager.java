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
        Object selectedIngredient = ingredientList.getSelectedValue();
        String ingredientFilter = selectedIngredient != null ? selectedIngredient.toString().toLowerCase() : "";

        Vector<Vector<Object>> filteredData = new Vector<>();

        for (Vector<Object> row : originalData) {
            String name = row.get(0).toString().toLowerCase();
            String category = row.get(1).toString().toLowerCase();


            if (name.contains(searchText) && (categoryFilter.isEmpty() || category.contains(categoryFilter))) {
                filteredData.add(row);
            }
        }

        tableModel.setDataVector(filteredData, getColumnNames());
    }

    private void populateTable() {
        String[] columnNames = {"name", "category"};
        tableModel = new DefaultTableModel(columnNames, 0);
        originalData = new Vector<>();

        Connection conn = null;
        OraclePreparedStatement pst = null;
        OracleResultSet rs = null;
        conn = ConnectDb.setupConnection();

        try  {
            String sqlStatement = "SELECT meal.name, category, ingredient.name FROM meal LEFT JOIN mealingredient ON meal.mealID = mealingredient.meal_id " +
                    "LEFT JOIN ingredient ON mealingredient.ingredient_id = ingredient.ingredientID";
            pst = (OraclePreparedStatement) conn.prepareStatement(sqlStatement);
            rs = (OracleResultSet) pst.executeQuery();

            while (rs.next()) {
                Vector<Object> row = new Vector<>();
                row.add(rs.getString("name"));
                row.add(rs.getString("category"));
                originalData.add(row);
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
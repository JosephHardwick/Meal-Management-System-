import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainMenu {
    private JPanel panel1;
    private JPanel mainPanel;
    private JLabel titleLabel;
    private JButton fridgeButton;
    private JButton recipeButton;
    private JButton mealPlanButton;


    public MainMenu() {
        fridgeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Fridge button clicked");
                SwingUtilities.invokeLater(() -> new FridgeForm().showMenu());

            }
        });
        recipeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SwingUtilities.invokeLater(() -> new RecipeManager().showMenu());

            }
        });
        mealPlanButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SwingUtilities.invokeLater(() -> new MealPlan().showMenu());

            }
        });
    }

    public void showMenu(){
        JFrame frame = new JFrame("Main Menu");
        frame.setContentPane(panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}

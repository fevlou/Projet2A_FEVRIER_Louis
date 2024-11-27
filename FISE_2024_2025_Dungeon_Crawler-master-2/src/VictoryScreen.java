import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class VictoryScreen extends JPanel {
    public VictoryScreen(ActionListener restartAction, ActionListener exitAction) {
        setLayout(new BorderLayout());

        // Titre de victoire
        JLabel victoryLabel = new JLabel("Bien joué : JavaBien", JLabel.CENTER);
        victoryLabel.setFont(new Font("Arial", Font.BOLD, 60));
        victoryLabel.setForeground(Color.GREEN);

        // Boutons
        JButton restartButton = new JButton("Rejouer");
        restartButton.addActionListener(restartAction);
        JButton exitButton = new JButton("Quitter");
        exitButton.addActionListener(exitAction);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        buttonPanel.add(restartButton);
        buttonPanel.add(exitButton);

        // Ajouter au panneau principal
        add(victoryLabel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }
}

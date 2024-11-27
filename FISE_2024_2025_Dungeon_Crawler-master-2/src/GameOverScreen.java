import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class GameOverScreen extends JPanel {

    public GameOverScreen(ActionListener onRestart, ActionListener onQuit) {
        setLayout(new BorderLayout());

        // Message de Game Over
        JLabel gameOverLabel = new JLabel("GAME OVER\n : JavaPAS", JLabel.CENTER);
        gameOverLabel.setFont(new Font("Arial", Font.BOLD, 60));
        gameOverLabel.setForeground(Color.RED);

        // Boutons
        JButton restartButton = new JButton("Rejouer");
        restartButton.addActionListener(onRestart);

        JButton quitButton = new JButton("Quitter");
        quitButton.addActionListener(onQuit);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(restartButton);
        buttonPanel.add(quitButton);

        // Ajout des composants
        add(gameOverLabel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }
}

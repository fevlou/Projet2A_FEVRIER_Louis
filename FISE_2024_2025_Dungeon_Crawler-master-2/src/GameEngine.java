import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

public class GameEngine implements Engine, KeyListener {
    private DynamicSprite hero;
    private ArrayList<Enemy> enemies;
    private ArrayList<Sprite> environment = new ArrayList<>();
    private Main main; // Référence à Main pour appeler triggerGameOver

    public GameEngine(DynamicSprite hero, ArrayList<Enemy> enemies, Main main) {
        this.hero = hero;
        this.enemies = enemies != null ? enemies : new ArrayList<>();
        this.main = main;
    }

    // Getter pour récupérer environment
    public ArrayList<Sprite> getEnvironment() {
        return environment;
    }

    @Override
    public void update() {
        if (hero == null || !hero.getHealth().isAlive()) {
            main.triggerGameOver();
            return;
        }

        checkCollisions(); // Vérification des collisions
        checkVictory();    // Vérification de la victoire
    }

    // Vérification de la victoire
    private void checkVictory() {
        Point victoryPoint = main.getPlayground().getVictoryPoint();

        if (victoryPoint != null) {
            Rectangle heroBounds = hero.getBounds();

            // Mise en place d'une tolérance
            int victoryRadius = 5; // Marge de 5 pixels autour du point de victoire.
            Rectangle victoryZone = new Rectangle(victoryPoint.x - victoryRadius, victoryPoint.y - victoryRadius, victoryRadius * 2, victoryRadius * 2);

            if (heroBounds.intersects(victoryZone)) {
                main.triggerVictory();
            }
        }
    }


    // Gestion des collisions avec les ennemis et les pièges
    private void checkCollisions() {

        for (Enemy enemy : enemies) {
            if (hero.getBounds().intersects(enemy.getBounds())) {
                hero.takeDamage(10);
                System.out.println("Collision avec un ennemi !");
            }
        }

        for (Sprite sprite : environment) {
            if (sprite instanceof TrapSprite) {
                TrapSprite trap = (TrapSprite) sprite;
                if (hero.getBounds().intersects(trap.getBounds())) {
                    System.out.println("Collision avec un piège !");
                    trap.trigger(hero);
                }
            }
        }
    }

    // Méthodes de KeyListener
    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP:
                hero.setDirection(Direction.NORTH);
                break;
            case KeyEvent.VK_DOWN:
                hero.setDirection(Direction.SOUTH);
                break;
            case KeyEvent.VK_LEFT:
                hero.setDirection(Direction.WEST);
                break;
            case KeyEvent.VK_RIGHT:
                hero.setDirection(Direction.EAST);
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.KeyListener;
import java.io.File;
import java.util.ArrayList;

public class GameController{
    private Main main;
    private JFrame displayZoneFrame;

    public GameController(Main main, JFrame displayZoneFrame) {
        this.main = main;
        this.displayZoneFrame = displayZoneFrame;
    }

    public void restartGame() {
        try {
            displayZoneFrame.getContentPane().removeAll();

            // Recréer le héros
            DynamicSprite hero = new DynamicSprite(75, 75,
                    ImageIO.read(new File("./img/heroTileSheetLowRes.png")), 48, 50, 500);

            // Recréer les ennemis
            Enemy enemy = new Enemy( 440, 320,ImageIO.read(new File("./img/tileSetCompleted.png")),6,10,60,50,7,6,15,
                    0, 370,500);
            Enemy enemy1 = new Enemy( 460, 190,ImageIO.read(new File("./img/tileSetCompleted.png")),6,10,60,50,7,6,25,
                    0, 370,500);
            Enemy enemy2 = new Enemy( 500, 700,ImageIO.read(new File("./img/tileSetCompleted.png")),6,10,60,50,7,6,15,
                    0, 330,500);

            ArrayList<Enemy> enemies = new ArrayList<>();
            enemies.add(enemy);
            enemies.add(enemy1);
            enemies.add(enemy2);

            // Réinitialiser les moteurs
            main.renderEngine = new RenderEngine(displayZoneFrame, hero);
            main.physicEngine = new PhysicEngine();
            main.gameEngine = new GameEngine(hero, enemies, main);

            // Réinitialiser les timers
            main.renderTimer = new Timer(50, (time) -> main.renderEngine.update());
            main.gameTimer = new Timer(50, (time) -> main.gameEngine.update());
            main.physicTimer = new Timer(50, (time) -> main.physicEngine.update());

            main.renderTimer.start();
            main.gameTimer.start();
            main.physicTimer.start();

            // Recharger les sprites et le niveau
            Playground level = new Playground("./data/level1.txt");
            main.renderEngine.addToRenderList(level.getSpriteList());
            main.renderEngine.addToRenderList(hero);
            main.renderEngine.addToRenderList(enemy);
            main.renderEngine.addToRenderList(enemy1);
            main.renderEngine.addToRenderList(enemy2);
            main.physicEngine.addToMovingSpriteList(hero);
            main.physicEngine.setEnvironment(level.getSolidSpriteList());
            main.gameEngine.getEnvironment().addAll(level.getAllSprites());

            displayZoneFrame.requestFocus();// Permet de rendre les touches opérationnelles
            displayZoneFrame.getContentPane().add(main.renderEngine);
            displayZoneFrame.revalidate();
            displayZoneFrame.repaint();


            for (KeyListener kl : displayZoneFrame.getKeyListeners()) {
                displayZoneFrame.removeKeyListener(kl);
            } // Permet de réiitialiser les clés pour se déplacer
            displayZoneFrame.addKeyListener(main.gameEngine);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

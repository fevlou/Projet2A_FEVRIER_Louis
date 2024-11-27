import javax.imageio.ImageIO;
import javax.swing.*;
import java.io.File;
import java.util.ArrayList;

import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

public class Main {
    private Playground playground;

    JFrame displayZoneFrame;
    RenderEngine renderEngine;
    GameEngine gameEngine;
    PhysicEngine physicEngine;
    GameController gameController;

    // Déclarez les timers comme attributs de la classe
    Timer renderTimer;
    Timer gameTimer;
    Timer physicTimer;

    public  Main() throws Exception{
        displayZoneFrame = new JFrame("Java Labs");
        displayZoneFrame.setSize(960,960);
        displayZoneFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Chargement du niveau
        playground = new Playground("./data/level1.txt");

        // Création du héro
        DynamicSprite hero = new DynamicSprite(75,75,
                ImageIO.read(new File("./img/heroTileSheetLowRes.png")),48,50,500);

        // Création des ennemis
        Enemy enemy = new Enemy( 440, 320,ImageIO.read(new File("./img/tileSet.png")),6,10,60,50,7,6,15,
                0, 370,500);
        Enemy enemy1 = new Enemy( 460, 190,ImageIO.read(new File("./img/tileSet.png")),6,10,60,50,7,6,25,
                0, 370,500);
        Enemy enemy2 = new Enemy( 500, 700,ImageIO.read(new File("./img/tileSet.png")),6,10,60,50,7,6,15,
                0, 330,500);

        // Création d'une liste  d'ennemis
        ArrayList<Enemy> enemies = new ArrayList<>();
        enemies.add(enemy);
        enemies.add(enemy1);
        enemies.add(enemy2);


        // Initialisation des moteurs
        renderEngine = new RenderEngine(displayZoneFrame, hero);
        physicEngine = new PhysicEngine();
        gameEngine = new GameEngine(hero, enemies,this);
        gameController = new GameController(this, displayZoneFrame);


        // Initialisation des timers
        renderTimer = new Timer(50,(time)-> renderEngine.update());
        gameTimer = new Timer(50,(time)-> gameEngine.update());
        physicTimer = new Timer(50,(time)-> physicEngine.update());

        // Démarrage des timers
        renderTimer.start();
        gameTimer.start();
        physicTimer.start();

        // Configuration de l'affichage
        displayZoneFrame.getContentPane().add(renderEngine);
        displayZoneFrame.setVisible(true);

        // Chargement du niveau et ajout des objets au moteur de rendu et de physique
        Playground level = new Playground("./data/level1.txt");

        renderEngine.addToRenderList(level.getSpriteList());
        renderEngine.addToRenderList(hero);
        renderEngine.addToRenderList(enemy);// On fait apparaitre notre ennemi
        renderEngine.addToRenderList(enemy1);// On fait apparaitre notre ennemi
        renderEngine.addToRenderList(enemy2);// On fait apparaitre notre ennemi
        physicEngine.addToMovingSpriteList(hero);
        physicEngine.setEnvironment(level.getSolidSpriteList());
        gameEngine.getEnvironment().addAll(level.getAllSprites());

        displayZoneFrame.addKeyListener(gameEngine);
    }

    public Playground getPlayground() {
        return playground;
    }

    //Code à déclencher si jeu perdu
    public void triggerGameOver() {
        // Arrêter les timers de jeu
        renderTimer.stop();
        gameTimer.stop();
        physicTimer.stop();

        // Créer un écran gameover
        GameOverScreen gameOverScreen = new GameOverScreen(
                e -> gameController.restartGame(), // Délégation à GameController
                e -> System.exit(0)
        );

        //Remplacer l'écran actuel par l'écrn gameover
        displayZoneFrame.getContentPane().removeAll(); // Efface le contenu actuel
        displayZoneFrame.getContentPane().add(gameOverScreen); // Ajoute l'écran Game Over
        displayZoneFrame.revalidate(); // Revalide le contenu
        displayZoneFrame.repaint(); // Repeint la fenêtre
    }

    //Code à déclencher si jeu gagné
    public void triggerVictory() {
        // Arrêter les timers de jeu
        renderTimer.stop();
        gameTimer.stop();
        physicTimer.stop();

        // Créer un écran de victoire
        VictoryScreen victoryScreen = new VictoryScreen(
                e -> gameController.restartGame(),  // Rejouer
                e -> System.exit(0)  // Quitter
        );

        // Remplacer l'écran actuel par l'écran de victoire
        displayZoneFrame.getContentPane().removeAll();  // Efface le contenu actuel
        displayZoneFrame.getContentPane().add(victoryScreen);  // Ajoute l'écran de victoire
        displayZoneFrame.revalidate();  // Revalide le contenu
        displayZoneFrame.repaint();  // Repeint la fenêtre
    }


    public static void main (String[] args) throws Exception {
        Main main = new Main();
    }

}

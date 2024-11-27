import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;

public class Enemy extends Sprite implements Collidable {
    private int dx; // Déplacement horizontal
    private int dy; // Déplacement vertical
    private int range; // Portée du mouvement (distance max)

    private int initialX; // Position initiale X
    private int initialY; // Position initiale Y
    private Health health; // Santé de l'ennemi



    // Nouveau constructeur pour charger une sous-image
    public Enemy(int x, int y, BufferedImage image1, int spriteCol, int spriteRow, int spriteWidth, int spriteHeight,
                 int marginCol, int marginRow, int dx, int dy, int range, int maxHealth) throws Exception {
        super(x, y, loadSubImage(image1, spriteCol, spriteRow, spriteWidth, spriteHeight, marginRow, marginCol), spriteWidth, spriteHeight);
        this.initialX = x;
        this.initialY = y;
        this.dx = dx;
        this.dy = dy;
        this.range = range;
        this.health = new Health(maxHealth);
    }

    //Constructeur simple (sans chargement d'un sprite issu d'une SpriteSheet)
    public Enemy(int x, int y, Image image, int width, int height, int dx, int dy, int range, int maxHealth) {
        super(x, y, image, width, height);
        this.initialX = x;
        this.initialY = y;
        this.dx = dx;
        this.dy = dy;
        this.range = range;
        this.health = new Health(maxHealth);
    }

    // Méthode pour charger une sous-image
    private static BufferedImage loadSubImage(BufferedImage image1, int col, int row, int spriteWidth, int spriteHeight, int marginRow,int marginCol) throws Exception {
        int x = col * (spriteWidth + marginCol); // Inclure la marge horizontale
        int y = row * (spriteHeight + marginRow); // Inclure la marge verticale
        return image1.getSubimage(x, y, spriteWidth, spriteHeight);
    }


    public Health getHealth() {
        return health;
    }

    @Override
    public Rectangle2D getHitBox() {
        return new Rectangle2D.Double(x, y, width, height);
    }

    @Override
    public boolean intersect(Rectangle2D.Double hitBox) {
        return this.getHitBox().intersects(hitBox);
    }

    public void move() {
        // Déplace l'ennemi dans une direction
        x += dx;
        y += dy;

        // Si l'ennemi dépasse sa portée, inverse la direction
        if (Math.abs(x - initialX) >= range) {
            dx = -dx;
        }
        if (Math.abs(y - initialY) >= range) {
            dy = -dy;
        }
    }

    public int getPriority(){
        return 10;
    }
}


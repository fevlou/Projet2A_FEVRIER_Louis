import java.awt.*;
import java.awt.geom.Rectangle2D;

public class TrapSprite extends Sprite {
    public TrapSprite(int x, int y, Image image, int width, int height) {
        super(x, y, image, width, height);
    }

    public Rectangle2D getHitBox() {
        return new Rectangle2D.Double(x, y, width, height);
    }

    @Override
    public boolean intersect(Rectangle2D.Double hitBox) {
        return this.getHitBox().intersects(hitBox);
    }

    // Méthode pour infliger des dégâts
    public void trigger(DynamicSprite hero) {
        hero.getHealth().setCurrentHealth(0); // Réduit la vie du héros à zéro
        System.out.println("Le héros est mort en marchant sur un piège !");
    }

}

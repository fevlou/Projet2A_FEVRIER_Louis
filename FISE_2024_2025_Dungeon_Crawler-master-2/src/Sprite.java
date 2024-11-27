import java.awt.*;
import java.awt.geom.Rectangle2D;

public abstract class Sprite implements Displayable{
    protected double x;
    protected double y;
    protected final Image image;
    protected final double width;
    protected final double height;

    public Sprite(double x, double y, Image image, double width, double height) {
        this.x = x;
        this.y = y;
        this.image = image;
        this.width = width;
        this.height = height;
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(image,(int)x,(int)y,null);
    }

    public int getPriority() {
        return 1; // Priorité pour les Sprites (inférieur à celle des ennemis)
    }
    public Rectangle getBounds() {
        return new Rectangle((int) x, (int) y, (int) width, (int) height);
    }

    public abstract boolean intersect(Rectangle2D.Double hitBox);
}

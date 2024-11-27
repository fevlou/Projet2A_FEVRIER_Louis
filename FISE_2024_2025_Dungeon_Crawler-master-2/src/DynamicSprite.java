import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

public class DynamicSprite extends SolidSprite{
    private Direction direction = Direction.EAST;
    private double speed = 10;
    private double timeBetweenFrame = 250;
    private boolean isWalking =true;
    private final int spriteSheetNumberOfColumn = 10;
    private Health health;

    public DynamicSprite(double x, double y, Image image, double width, double height,int maxHealth) {
        super(x, y, image, width, height);
        this.health = new Health(maxHealth);
    }

    //Renvoyer l'état de santé
    public Health getHealth() {
        return health;
    }

    public Rectangle getBounds() {
        return new Rectangle((int) x, (int) y, (int) width, (int) height);
    }

    //Gère l'apparition de dommage (enlever de la vie)
    public void takeDamage(int damage) {
        health.setCurrentHealth(health.getCurrentHealth() - damage);
        if (health.getCurrentHealth() < 0) {
            health.setCurrentHealth(0); // Optionnel : garantir que la santé reste >= 0
        }
    }

    //Permet de gérer les collisions entre sprite
    private boolean isMovingPossible(ArrayList<Sprite> environment){
        Rectangle2D.Double moved = new Rectangle2D.Double();
        switch(direction){
            case EAST: moved.setRect(super.getHitBox().getX()+speed,super.getHitBox().getY(),
                                    super.getHitBox().getWidth(), super.getHitBox().getHeight());
                break;
            case WEST:  moved.setRect(super.getHitBox().getX()-speed,super.getHitBox().getY(),
                    super.getHitBox().getWidth(), super.getHitBox().getHeight());
                break;
            case NORTH:  moved.setRect(super.getHitBox().getX(),super.getHitBox().getY()-speed,
                    super.getHitBox().getWidth(), super.getHitBox().getHeight());
                break;
            case SOUTH:  moved.setRect(super.getHitBox().getX(),super.getHitBox().getY()+speed,
                    super.getHitBox().getWidth(), super.getHitBox().getHeight());
                break;
        }

        for (Sprite s : environment){
            if ((s instanceof SolidSprite) && (s!=this)){
                if (((SolidSprite) s).intersect(moved)){
                    return false;
                }
            }
        }
        return true;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    private void move(){
        switch (direction){
            case NORTH -> {
                this.y-=speed;
            }
            case SOUTH -> {
                this.y+=speed;
            }
            case EAST -> {
                this.x+=speed;
            }
            case WEST -> {
                this.x-=speed;
            }
        }
    }

    public void moveIfPossible(ArrayList<Sprite> environment){
        if (isMovingPossible(environment)){
            move();
        }
    }

    @Override //Dessin du sprite
    public void draw(Graphics g) {
        int index= (int) (System.currentTimeMillis()/timeBetweenFrame%spriteSheetNumberOfColumn);

        g.drawImage(image,(int) x, (int) y, (int) (x+width),(int) (y+height),
                (int) (index*this.width), (int) (direction.getFrameLineNumber()*height),
                (int) ((index+1)*this.width), (int)((direction.getFrameLineNumber()+1)*this.height),null);
    }
}

import java.awt.*;

public class Health {
    private int maxHealth;
    private int currentHealth;

    public Health(int maxHealth) {
        this.maxHealth = maxHealth;
        this.currentHealth = maxHealth; // Initialiser au maximum
    }

    //Retourne état de la vie
    public int getCurrentHealth() {
        return currentHealth;
    }

    //Paramètre état de la vie
    public void setCurrentHealth(int currentHealth) {
        this.currentHealth = Math.max(0, Math.min(currentHealth, maxHealth));
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    //Affichage de la vie
    public void drawHealthBar(Graphics g, DynamicSprite hero) {
        int currentHealth = hero.getHealth().getCurrentHealth();
        int maxHealth = hero.getHealth().getMaxHealth();

        g.setColor(Color.RED);
        g.fillRect(20, 20, 200, 20);
        g.setColor(Color.GREEN);
        g.fillRect(20, 20, (int) ((currentHealth / (double) maxHealth) * 200), 20);
        g.setColor(Color.BLACK);
        g.drawRect(20, 20, 200, 20); // Contour
    }




    public void heal(int amount) {
        currentHealth += amount;
        if (currentHealth > maxHealth) {
            currentHealth = maxHealth; // Ne dépasse pas la santé maximale
        }
    }

    //Vérification que le héros à toujours de la vie
    public boolean isAlive() {
        return currentHealth > 0;
    }
}


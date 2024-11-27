import javax.swing.*;
import java.awt.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Comparator;

public class RenderEngine extends JPanel implements Engine{
    private ArrayList<Displayable> renderList;
    private DynamicSprite hero;

    public RenderEngine(JFrame frame, DynamicSprite hero) {
        this.renderList = new ArrayList<>();
        this.hero = hero;
        frame.getContentPane().add(this);
    }

    public void addToRenderList(Displayable displayable){
        if (!renderList.contains(displayable)){
            renderList.add(displayable);
        }
    }

    public void addToRenderList(ArrayList<Displayable> displayables){ // Renommage correct ici
        for (Displayable item : displayables) { // Renommage de la variable locale
            if (!renderList.contains(item)) {
                renderList.add(item);
            }
        }

    }


    @Override
    public void paint(Graphics g) {
        super.paint(g);
        for (Displayable renderObject:renderList) {
            renderObject.draw(g);
        }
        if (hero != null) {
            hero.getHealth().drawHealthBar(g, hero);
        }
        // Tri des objets par priorité
        renderList.sort(Comparator.comparingInt(Displayable::getPriority));
    }


    @Override
    public void update(){
        this.repaint();
        for (Displayable renderObject : renderList) {
            if (renderObject instanceof Enemy) {
                ((Enemy) renderObject).move();  // Déplace l'ennemi
            }
        }

    }

}

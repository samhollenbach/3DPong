import java.awt.*;
import java.util.ArrayList;

/**
 * Created by samhollenbach on 10/26/15.
 */
public class MainMenu implements Window {

    ArrayList<Button> buttons = new ArrayList<Button>();
    Button play;


    public MainMenu(){


        play = new Button("Play", 100, 100, 100, 50);
        play.setButtonColor(Color.BLUE);
        play.setHighlightColor(Color.CYAN);

        buttons.add(play);



    }




    @Override
    public void draw(Graphics g) {
        g.setColor(new Color(248, 148, 6));
        g.fillRect(0,0,Main.getWindowWidth(),Main.getWindowHeight());
        for(Button b : buttons){
            b.drawButton(g);
        }
    }

    @Override
    public void update() {


    }

    @Override
    public void checkButtons() {
        if(play.isHovered()){
            Main.display = new GameScreen();
        }
    }
}

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

/**
 * Created by samhollenbach on 10/6/15.
 */
public class Main extends JFrame implements KeyListener, MouseListener, MouseMotionListener{

    private boolean isRunning = true;
    private int fps = 60;

    private static int windowWidthInit = 900;
    private static int windowHeightInit = 600;

    private static int windowWidth = windowWidthInit;
    private static int windowHeight = windowHeightInit;

    BufferedImage backBuffer;

    static Window display;

    public static int mouseX = 0;
    public static int mouseY = 0;


    public static void main(String[] args) {
        Main m = new Main();
        m.run();
    }

    public Main(){

    }

    public void run(){
        initialize();
        while(isRunning)
        {

            long time = System.currentTimeMillis();

            update();
            draw();

            //  delay for each frame  -   time it took for one frame
            time = (1000 / fps) - (System.currentTimeMillis() - time);
            //System.out.println(time);
            if (time > 0)
            {
                try
                {
                    Thread.sleep(time);
                }
                catch(Exception e){}
            }
        }

        setVisible(false);
        System.exit(0);
    }

    public void initialize(){

        windowWidth = windowWidthInit;
        windowHeight = windowHeightInit;
        setTitle("3D Pong");
        setSize(windowWidth, windowHeight);
        setResizable(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);

        addKeyListener(this);
        addMouseListener(this);
        addMouseMotionListener(this);

        backBuffer = new BufferedImage(windowWidth, windowHeight, BufferedImage.TYPE_INT_RGB);
        display = new MainMenu();



    }

    public void draw(){

        Graphics g = getGraphics();
        //double buffer graphics object
        Graphics bbg = backBuffer.getGraphics();

//        Color backgroundBlue = new Color(75, 119, 190);
//        Color baseColor = new Color(108, 122, 137);
//        bbg.setColor(backgroundBlue);
//        bbg.fillRect(0, 0, windowWidth, windowHeight);


        display.draw(bbg);

        g.drawImage(backBuffer, 0, 0, this);

        bbg.dispose();
        g.dispose();

    }

    public void update(){
        display.update();
    }

    public static Window getDisplay() {
        return display;
    }


    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        display.checkButtons();
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        display.checkButtons();
        mouseX = e.getX();
        mouseY = e.getY();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();
    }

    public static int getWindowWidth() {
        return windowWidth;
    }

    public static int getWindowHeight() {
        return windowHeight;
    }
}

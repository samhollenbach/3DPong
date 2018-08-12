import java.awt.*;
import java.util.ArrayList;

/**
 * Created by samhollenbach on 10/26/15.
 */
public class GameScreen implements Window {



    private int width = 650;
    private int height = 400;
    private int length = 600;

    private int gameOffsetX = Main.getWindowWidth()/2 - width/2;
    private int gameOffsetY = Main.getWindowHeight()/2 - height/2;


    private double distanceSizeScale = .25;
    private int opponentX, opponentY;
    private int opponentWidth,opponentHeight;
    private int paddleCenterX, paddleCenterY;

    private Ball ball;
    private Paddle paddle;
    private Paddle paddleAI;

    private int paddleSize = 125;

    private boolean AIon = true;

    private boolean ballMotion = true;
    private boolean serveReady = true;

    private int paddleContactTimeMax = 30;

    private int currentMouseX, currentMouseY;

    private double accelCoefficient = 0.1;
    private double spinCoefficient = 1.3;
    private int AIvel;


    private int levelCap = 7;
    private int[] lives = {5,5,4,4,3,3,2,2};
    private int[] enemyLives = {4,4,4,4,4,4,4,4};
    private int[] AIspeeds = {3,4,5,6,6,7,7,8};
    private int currentEnemyLives = 4;
    private int currentLives = 4;
    private int currentLevel = 0;
    private boolean gameOver = false;
    private boolean win = false;


    private ArrayList<SplashText> splashTexts = new ArrayList<SplashText>();

    public GameScreen(){
        ball = new Ball();
        paddle = new Paddle(false);
        paddleAI = new Paddle(true);

        currentMouseX = Main.mouseX;
        currentMouseY = Main.mouseY;

        opponentX = gameOffsetX + (width-(int)(width*distanceSizeScale))/2;
        opponentY = gameOffsetY + (height-(int)(height*distanceSizeScale))/2;
        opponentWidth = (int)(width*distanceSizeScale);
        opponentHeight = (int)(height*distanceSizeScale);

        double paddleRatio = 1.614;

        paddleCenterX = width/2 - paddleSize/2;
        paddleCenterY = height/2 - (int)(paddleSize / paddleRatio)/2;

        paddleAI.setPosX(paddleCenterX);
        paddleAI.setPosY(paddleCenterY);
        paddleAI.setSizeX(paddleSize);
        paddleAI.setSizeY((int) (paddleSize / paddleRatio));

        paddle.setPosX(paddleCenterX);
        paddle.setPosY(paddleCenterY);
        paddle.setSizeX(paddleSize);
        paddle.setSizeY((int) (paddleSize / paddleRatio));



        ball.setBallSize(50);
        ball.setPosX(width/2 - ball.getBallSize() / 2);
        ball.setPosY(height / 2 - ball.getBallSize() / 2);
        ball.setPosZ(0);
        ball.setVelX(0);
        ball.setVelY(0);
        ball.setVelZ(3);

        AIvel = AIspeeds[currentLevel];

        stopBall();

    }

    @Override
    public void draw(Graphics g) {
        g.setFont(new Font("Arial", Font.BOLD, 14));
        Color c = new Color(52, 73, 94);
        g.setColor(c);
        g.fillRect(0, 0, Main.getWindowWidth(), Main.getWindowHeight());

        g.setColor(Color.BLACK);
        g.fillRect(gameOffsetX, gameOffsetY, width, height);

        g.setColor(new Color(27, 188, 254));
        g.drawRect(gameOffsetX, gameOffsetY, width, height);
        g.drawRect(opponentX, opponentY,
                opponentWidth, opponentHeight);

        g.drawLine(gameOffsetX, gameOffsetY, opponentX, opponentY);
        g.drawLine(gameOffsetX, gameOffsetY + height, opponentX, opponentY + opponentHeight);
        g.drawLine(gameOffsetX + width, gameOffsetY, opponentX + opponentWidth, opponentY);
        g.drawLine(gameOffsetX + width, gameOffsetY + height, opponentX + opponentWidth, opponentY + opponentHeight);


        int lines = 50;
        for(double i = 0; i < lines; i+=2+i/3){
            double rTemp = 1-i/lines;
            g.drawRect((int)(gameOffsetX+rTemp*(opponentX-gameOffsetX)),(int)(gameOffsetY+rTemp*(opponentY-gameOffsetY)),
                    (int)(width-rTemp*(width-opponentWidth)),(int)(height-rTemp*(height-opponentHeight)));
        }



        g.setColor(new Color(236, 236, 236, 200));
        double zRatio = (double)ball.getPosZ()/length;
        g.drawRect((int) (gameOffsetX + zRatio * (opponentX - gameOffsetX)), (int) (gameOffsetY + zRatio * (opponentY - gameOffsetY)), (int) (width - zRatio * (width - opponentWidth)), (int) (height - zRatio * (height - opponentHeight)));


        drawPaddle(paddleAI, opponentX, opponentY, distanceSizeScale, g);

        drawBall(ball, gameOffsetX, gameOffsetY, g);

        drawLives(g);

        drawPaddle(paddle, gameOffsetX, gameOffsetY, 1, g);

        drawSplashTexts(g);

        if(gameOver){
            g.setFont(new Font("Arial", Font.BOLD, 60));
            g.setColor(Color.WHITE);
            g.drawString("Game Over", gameOffsetX + 150, gameOffsetY + 200);
        }else if(win){
            g.setFont(new Font("Arial", Font.BOLD, 60));
            g.setColor(Color.WHITE);
            g.drawString("You Win!", gameOffsetX + 150, gameOffsetY + 200);
        }


    }

    public void drawLives(Graphics g){
        g.setColor(Color.WHITE);
        g.drawString("Level " + (currentLevel+1), gameOffsetX + width/2-30, gameOffsetY + 20);

        g.drawString("Lives: ", gameOffsetX + 30, gameOffsetY + 20);

        g.drawString("Lives: ", gameOffsetX + width - 100, gameOffsetY + 20);

        g.setColor(new Color(100, 100, 236));
        for(int i = 0; i < currentLives; i++){
            g.fillOval(gameOffsetX + 30 + (i*20), gameOffsetY+30, 10,10);
        }

        g.setColor(new Color(236, 100, 100));
        for(int i = 0; i < currentEnemyLives; i++){
            g.fillOval(gameOffsetX + width - 100 + (i*20), gameOffsetY+30, 10,10);
        }

    }

    public void drawSplashTexts(Graphics g){
        for(SplashText s : splashTexts){
            if(!s.isExpired()){
                g.setColor(Color.WHITE);
                g.drawString(s.getText(), s.getX(), s.getY());
            }



        }
    }

    //TODO: Find proper X and Y positions of ball depending on Z
    public void drawBall(Ball b, int offsetX, int offsetY, Graphics g){

        if(ballStopped() && !serveReady){
            g.setColor(new Color(207, 0, 15));
        }else{
            g.setColor(new Color(46, 204, 113));
        }
        double scaleTemp = distanceSizeScale + (double)(length-b.getPosZ())*(1-distanceSizeScale)/length;
        int midX = width/2;
        int midY = height/2;
        int difX = b.getPosX()-midX;
        int dixY = b.getPosY()-midY;
        difX *= scaleTemp;
        dixY *= scaleTemp;
        int newX = midX + difX;
        int newY = midY + dixY;
        g.fillOval(offsetX + newX, offsetY + newY, getBallDrawSize(b), getBallDrawSize(b));
    }

    public int getBallDrawSize(Ball b){
        int sizeMax = b.getBallSize();
        int sizeMin = (int)(b.getBallSize()*distanceSizeScale);
        double scaleTemp = (double)(length-b.getPosZ())/length;
        return sizeMin + (int)(scaleTemp*(sizeMax-sizeMin));
    }

    public void drawPaddle(Paddle p, int offsetX, int offsetY, double scale, Graphics g){
        int transparencyBase = 100;
        int transparency;
        if(p.contact()){
            transparency = transparencyBase + (int)(((paddleContactTimeMax-p.getContactTimeCount())/(double)paddleContactTimeMax)*75);
        }else{
            transparency = transparencyBase;
        }
        if(p.isAIControlled()){
            g.setColor(new Color(236, 100, 100, transparency));
        }else{
            g.setColor(new Color(100, 100, 236, transparency));
        }


        g.fillRoundRect(offsetX + (int) (p.getPosX() * scale), offsetY + (int) (p.getPosY() * scale),
                (int) (p.getSizeX() * scale), (int) (p.getSizeY() * scale), (int) (15 * scale), (int) (15 * scale));
    }


    @Override
    public void update() {

        //Set paddle previous tick positions
        paddle.setPrevPosX(paddle.getPosX());
        paddle.setPrevPosY(paddle.getPosY());
        paddleAI.setPrevPosX(paddleAI.getPosX());
        paddleAI.setPrevPosY(paddleAI.getPosY());

        //Paddle mouse control
        if(currentMouseX != Main.mouseX || currentMouseY != Main.mouseY){

            currentMouseX = Main.mouseX;
            currentMouseY = Main.mouseY;
            int x = currentMouseX;
            int y = currentMouseY;
            if(x < gameOffsetX+paddle.getSizeX()/2){
                x = gameOffsetX+paddle.getSizeX()/2;
            }else if(x > gameOffsetX+width-paddle.getSizeX()/2){
                x = gameOffsetX+width-paddle.getSizeX()/2;
            }
            if(y < gameOffsetY+paddle.getSizeY()/2){
                y = gameOffsetY+paddle.getSizeY()/2;
            }else if(y > gameOffsetY+height-paddle.getSizeY()/2){
                y = gameOffsetY+height-paddle.getSizeY()/2;
            }
            paddle.setPosX(x - gameOffsetX - paddle.getSizeX()/2);
            paddle.setPosY(y - gameOffsetY - paddle.getSizeY() / 2);
        }

        //Stop ball when win/lose
        if(!ballMotion && (ball.getVelX()!=0 || ball.getVelY()!=0 || ball.getVelZ()!=0)){
            stopBall();
        }

        //Paddle-Ball contact indicator
        if(paddle.contact()){
            paddle.incrementContactTimeCount();
            if(paddle.getContactTimeCount() > paddleContactTimeMax){
                paddle.resetContactTimeCount();
                paddle.setContact(false);
            }
        }
        if(paddleAI.contact()){
            paddleAI.incrementContactTimeCount();
            if(paddleAI.getContactTimeCount() > paddleContactTimeMax){
                paddleAI.resetContactTimeCount();
                paddleAI.setContact(false);
            }
        }

        //Ball side collision
        if((ball.getPosX()>=width-ball.getBallSize() && ball.getVelX() > 0) || (ball.getPosX()<=0 && ball.getVelX() < 0)){
            ball.setVelX(-ball.getVelX());
        }
        if((ball.getPosY()>=height-ball.getBallSize() && ball.getVelY() > 0) || (ball.getPosY()<=0 && ball.getVelY() < 0)){
            ball.setVelY(-ball.getVelY());
        }
        if(ball.getPosZ()>=length && ball.getVelZ() > 0){
            if(checkPaddleCollision(paddleAI,ball)){
                paddleAI.setContact(true);
                ball.setVelZ(-ball.getVelZ());
                curveBall(ball, (ball.getAccelX() * accelCoefficient + paddleAI.instantaneousVelX() / 100), ball.getAccelY() * accelCoefficient + paddleAI.instantaneousVelY() / 100, paddleAI);
            }else{
                AILoseLife();
            }
        }else if(ball.getPosZ()<=6 && ball.getVelZ() < 0){
            if(checkPaddleCollision(paddle,ball)){
                paddle.setContact(true);
                curveBall(ball,paddle.instantaneousVelX() / 100,paddle.instantaneousVelY() / 100, paddle);


                ball.setVelZ(-ball.getVelZ());
            }else{
                playerLoseLife();
            }
        }



        //AI Control
        if(AIon){

            int distX = (paddleAI.getPosX() + paddleAI.getSizeX()/2)  - (ball.getPosX() + ball.getBallSize()/2);
            int distY = (paddleAI.getPosY() + paddleAI.getSizeY()/2)  - (ball.getPosY() + ball.getBallSize()/2);

            if (distX >= 0){
                int move = (distX > AIvel) ? AIvel : distX;
                paddleAI.setPosX(paddleAI.getPosX() - move);
            }else if(distX < 0){
                int move = (-distX > AIvel) ? -AIvel : distX;
                paddleAI.setPosX(paddleAI.getPosX() - move);
            }
            if (distY >= 0){
                int move = (distY > AIvel) ? AIvel : distY;
                paddleAI.setPosY(paddleAI.getPosY() - move);
            }else if(distY < 0){
                int move = (-distY > AIvel) ? -AIvel : distY;
                paddleAI.setPosY(paddleAI.getPosY() - move);
            }

            if(paddleAI.getPosX() > width-paddleAI.getSizeX()){
                paddleAI.setPosX(width - paddleAI.getSizeX());
            }else if(paddleAI.getPosX() < 0){
                paddleAI.setPosX(0);
            }
            if(paddleAI.getPosY() > height-paddleAI.getSizeY()){
                paddleAI.setPosY(height-paddleAI.getSizeY());
            }else if(paddleAI.getPosY() < 0){
                paddleAI.setPosY(0);
            }
        }

        updateSplashTexts();
        updateBallPosition();
    }

    public void updateSplashTexts(){
        for(SplashText s : splashTexts){
            s.incrementCounter();
            if(s.getCounter() >= s.getTime()){
                s.setExpired(true);
            }
        }
    }

    public void updateBallPosition(){
        ball.setPosX((int) (ball.getPosX() + ball.getVelX()));
        ball.setPosY((int) (ball.getPosY() + ball.getVelY()));
        ball.setPosZ((int) (ball.getPosZ() + ball.getVelZ()));
        ball.setVelX(ball.getVelX() + ball.getAccelX() * spinCoefficient);
        ball.setVelY(ball.getVelY() + ball.getAccelY() * spinCoefficient);
        ball.setVelZ(ball.getVelZ() + ball.getAccelZ()* spinCoefficient);
    }


    public void stopBall(){
        ball.setVelX(0);
        ball.setVelY(0);
        ball.setVelZ(0);
        ball.setAccelX(0);
        ball.setAccelY(0);
        ball.setAccelZ(0);
        AIon = false;
    }

    public void startBall(int velX, int velY, int velZ){
        ball.setVelX(velX);
        ball.setVelY(velY);
        ball.setVelZ(velZ);
        double randomX = 3*(0.5-Math.random());
        double randomY = 3*(0.5-Math.random());
        curveBall(ball, ((paddle.instantaneousVelX()+randomX)/100), ((paddle.instantaneousVelY()+randomY)/100), paddle);
        AIon = true;
        serveReady = false;
    }


    public void curveBall(Ball b, double accelX, double accelY, Paddle source){
        b.setAccelX(accelX);
        b.setAccelY(accelY);
        if(source == paddle && (Math.abs(accelX) > 0.25 || Math.abs(accelY) > 0.25)){
            splashTexts.add(new SplashText("Super Curve!", gameOffsetX+b.getPosX()+50,gameOffsetY+b.getPosY()+50, 100,0));
        }else if(source == paddle && (Math.abs(accelX) > 0.1 || Math.abs(accelY) > 0.1)){
            splashTexts.add(new SplashText("Curve!", gameOffsetX+b.getPosX()+50,gameOffsetY+b.getPosY()+50, 100,0));
        }
    }

    public boolean ballStopped(){
        return (ball.getVelX() == 0 && ball.getVelY() == 0 && ball.getVelZ() == 0);
    }

    public boolean checkPaddleCollision(Paddle p, int x, int y){
        if(p.getPosX() > x || p.getPosX()+p.getSizeX() < x
                || p.getPosY() > y || p.getPosY()+p.getSizeY() < y){
            return false;
        }
        return true;
    }

    public boolean checkPaddleCollision(Paddle p, Ball b){
        //int ballCenterX = b.getPosX()+b.getBallSize()/2;
        //int ballCenterY = b.getPosY()+b.getBallSize()/2;



        if(p.getPosX() > b.getPosX()+b.getBallSize() || p.getPosX()+p.getSizeX() < b.getPosX()
                || p.getPosY() > b.getPosY()+b.getBallSize() || p.getPosY()+p.getSizeY() < b.getPosY()){

            //Corner intereaction
            //TODO: FIX CORNER INTERACTION
//            int paddleCenterX = p.getPosX()+p.getSizeX()/2;
//            int paddleCenterY = p.getPosY()+p.getSizeY()/2;
//            int distMax = (int)Math.sqrt(Math.pow((p.getSizeX()/2 + p.getPosY()/2),2)) + b.getBallSize()/2;
//            int dist = (int)(Math.sqrt(Math.pow((paddleCenterX-ballCenterX + paddleCenterY-ballCenterY),2)));
//            if(dist < distMax){
//                return true;
//            }

            return false;
        }
        return true;
    }

    //Checks all clicks
    @Override
    public void checkButtons() {
        if(gameOver){
            gameOver = false;
            setLevel(0);
        }else if(win) {
            win = false;
            setLevel(0);
        }else if(serveReady && checkPaddleCollision(paddle,ball)){
            paddle.setContact(true);
            startBall(0,0,8);
        }else if(ballStopped()){
            ball.setPosX(width/2-ball.getBallSize() / 2);
            ball.setPosY(height/2-ball.getBallSize() / 2);
            ball.setPosZ(0);
            paddleAI.setPosX(paddleCenterX);
            paddleAI.setPosY(paddleCenterY);
            serveReady = true;
        }
    }

    public void playerLoseLife(){
        stopBall();
        currentLives--;
        if(currentLives == 0){
            loseGame();
        }
    }

    public void AILoseLife(){
        stopBall();
        currentEnemyLives--;
        if(currentEnemyLives == 0){
            levelUp();
        }
    }

    public void setLevel(int level){
        currentLevel = level;
        if(currentLevel > 7){
            win = true;
        }
        currentEnemyLives = enemyLives[currentLevel];
        currentLives = lives[currentLevel];
        AIvel = AIspeeds[currentLevel];
    }

    public void levelUp(){
        setLevel(currentLevel+1);
    }

    public void loseGame(){
        gameOver = true;
    }
}

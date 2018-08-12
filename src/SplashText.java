/**
 * Created by samhollenbach on 11/4/15.
 */
public class SplashText {

    private String text;
    private int time;
    private int fadeTime = 10;
    private int x, y;
    private int counter = 0;
    private boolean expired = false;

    public SplashText(String text,int x, int y, int time, int fadeTime) {
        this.text = text;
        this.x = x;
        this.y = y;
        this.time = time;
        this.fadeTime = fadeTime;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getFadeTime() {
        return fadeTime;
    }

    public void setFadeTime(int fadeTime) {
        this.fadeTime = fadeTime;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getCounter() {
        return counter;
    }

    public void incrementCounter(){
        counter++;
    }

    public boolean isExpired() {
        return expired;
    }

    public void setExpired(boolean expired) {
        this.expired = expired;
    }
}

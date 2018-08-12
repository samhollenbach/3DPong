import java.awt.*;

/**
 * Created by samhollenbach on 10/26/15.
 */
public class Button {

    private String buttonText = "";
    private int x,y,width,height;
    private Color buttonColor = new Color(52, 152, 219);
    private Color highlightColor = buttonColor.brighter();
    private Color textColor = Color.WHITE;

    public Button(String buttonText, int x, int y, int width, int height) {
        this.buttonText = buttonText;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }


    public String getButtonText() {
        return buttonText;
    }

    public void setButtonText(String buttonText) {
        this.buttonText = buttonText;
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

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public Color getButtonColor() {
        return buttonColor;
    }

    public void setButtonColor(Color buttonColor) {
        this.buttonColor = buttonColor;
    }

    public Color getHighlightColor() {
        return highlightColor;
    }

    public void setHighlightColor(Color highlightColor) {
        this.highlightColor = highlightColor;
    }

    public Color getTextColor() {
        return textColor;
    }

    public void setTextColor(Color textColor) {
        this.textColor = textColor;
    }

    public boolean isInsideButton(int x1, int y1){
        return (x1 >= x && x1 <= (x+width)) && (y1 >= y && y1 <= (y+height));
    }

    public boolean isHovered(){
        return isInsideButton(Main.mouseX,Main.mouseY);
    }

    public void drawButton(Graphics g){
        if(isHovered()){
            g.setColor(getHighlightColor());
        }else{
            g.setColor(getButtonColor());
        }
        g.fillRect(getX(),getY(),getWidth(),getHeight());
        g.setColor(getTextColor());
        g.drawString(getButtonText(),getX()+(int)(getWidth()/1.8),getY()+getHeight()/2+10);
    }
}

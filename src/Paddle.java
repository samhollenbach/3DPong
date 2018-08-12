/**
 * Created by samhollenbach on 10/26/15.
 */
public class Paddle {

    private int sizeX, sizeY;
    private boolean AIControlled;
    private int posX, posY, prevPosX, prevPosY;
    private boolean contact = false;
    private int contactTimeCount = 0;


    public Paddle(boolean AIControlled){
        this.AIControlled = AIControlled;
    }

    public double getInstantaneousVel(){
        return 0;
    }

    public int getSizeX() {
        return sizeX;
    }

    public void setSizeX(int sizeX) {
        this.sizeX = sizeX;
    }

    public int getSizeY() {
        return sizeY;
    }

    public void setSizeY(int sizeY) {
        this.sizeY = sizeY;
    }

    public int getPosX() {
        return posX;
    }

    public void setPosX(int posX) {
        prevPosX = this.posX;
        this.posX = posX;
    }

    public int getPosY() {
        return posY;
    }

    public void setPosY(int posY) {
        prevPosY = this.posY;
        this.posY = posY;
    }

    public int getPrevPosX() {
        return prevPosX;
    }

    public void setPrevPosX(int prevPosX) {
        this.prevPosX = prevPosX;
    }

    public int getPrevPosY() {
        return prevPosY;
    }

    public void setPrevPosY(int prevPosY) {
        this.prevPosY = prevPosY;
    }

    public double instantaneousVelX(){
        return prevPosX-posX;
    }

    public double instantaneousVelY(){
        return prevPosY-posY;
    }

    public boolean contact() {
        return contact;
    }

    public void setContact(boolean contact) {
        this.contact = contact;
    }

    public void incrementContactTimeCount(){
        contactTimeCount++;
    }

    public void resetContactTimeCount(){
        contactTimeCount = 0;
    }

    public int getContactTimeCount(){
        return contactTimeCount;
    }

    public boolean isAIControlled() {
        return AIControlled;
    }
}

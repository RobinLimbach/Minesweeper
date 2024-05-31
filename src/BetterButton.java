import javax.swing.*;

public class BetterButton extends JButton{
    boolean enabled = true;
    boolean flagged = false;
    boolean mouseOn = false;

    public BetterButton(){

    }

    public void disable1(){
        enabled = false;
    }

    public void enable1(){
        enabled = true;
    }

    public boolean isEnabled1(){
        return enabled;
    }

    public void setFlagged(boolean f){
        flagged = f;
    }

    public boolean getFlagged(){
        return flagged;
    }

    public void setMouseOn(boolean m){
        mouseOn = m;
    }

    public boolean getMouseOn(){
        return mouseOn;
    }
}

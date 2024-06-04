import javax.swing.*;
import java.awt.*;

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

    public void toggleFlag(int buttonWidth, int buttonHeight){
        if(!flagged && enabled){
            disable1();
            setForeground(Color.red);
            setFont(new Font("Comic Sans", Font.BOLD, 20));
            setMargin(new Insets(0, 0, 0, 0));
            flagged = true;
            ImageIcon icon = new ImageIcon("american_flag.png");
            Image image = icon.getImage();
            image = image.getScaledInstance(buttonWidth, buttonHeight, Image.SCALE_SMOOTH);
            icon = new ImageIcon(image);
            setIcon(icon);
        }
        else if (getText().isEmpty()) {

            setIcon(null);
            enable1();
            flagged = false;
        }

    }

    public void colorButton(int num){
        if(num == 0){
            setBackground(new Color(0, 150, 200));
            setText("0");
        }
        if(num == 1){
            setBackground(new Color(0, 200, 100));
            setText("1");
        }
        if(num == 2){
            setBackground(new Color(200, 200, 0));
            setText("2");
        }
        if(num == 3){
            setBackground(new Color(200, 100, 20));
            setText("3");
        }
        if(num == 4){
            setBackground(new Color(200, 50, 0));
            setText("4");
        }
        if(num == 5){
            setBackground(new Color(150, 0, 0));
            setText("5");
        }
        if(num == 6){

            setBackground(new Color(100, 0, 10));
            setText("6");
        }
        if(num ==7){

            setBackground(new Color(60, 5, 20));
            setText("7");
        }
        if(num == 8){

            setBackground(new Color(30, 5, 10));
            setText("8");
        }
        setFont(new Font("Comic Sans", Font.BOLD, 20));
        setMargin(new Insets(0, 0, 0, 0));
        setForeground(Color.BLACK);



    }
}

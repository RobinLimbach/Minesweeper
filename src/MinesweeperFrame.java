import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MinesweeperFrame extends JFrame implements KeyListener, MouseListener {

    //variables which can be set by the player in the menu
    int width; //number of squares across in the game
    int height; //number of squares up/down in the game
    int numMines; //number of mines in the game
    char flagToggleKey; //key which the player can use to mark a square with a flag, instead of right click
    char buttonActionKey; //key which the player can use to reveal a square

    //panels
    InfoDisplay info; //panel that contains the score, number of flags remaining, watch, and menu button
    GameBoard gameBoard; //panel that contains the game (grid of buttons, width by height)
    Menu menu; //panel that contains textFields to set flagToggleKey, buttonActionKey, width, height, numMines, & start new game

    Stopwatch watch; //label that contains a timer for the current game


    BetterButton[][] buttons; //each button is one square in the gameBoard grid
    MineField minefield; //this class holds a 2d array of booleans representing where the mines are on the board.


    boolean finished = false; //is set to true when game finishes, so player can not make more moves when the game is done.
    boolean isFirstClick = true;
    boolean inMenu;

    //used for setting the size of images (flags, mines)
    int buttonHeight;
    int buttonWidth;






    //constructor creates frame with menu visible in it
    public MinesweeperFrame(){

        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLayout(null);
        this.getContentPane().setBackground(Color.black);
        this.addKeyListener(this);
        this.setVisible(true);
        menu = new Menu();
        this.add(menu);
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setFocusable(true);
    }

    /*
        does the first click of a game on the specified button (i, j). It initializes minefield randomly over and over
        until the button at (i, j) is not adjacent to any mines. Then it uncovers the clicked button
        (which uncovers surrounding buttons), sets isFirstClick to false, and starts the stopwatch.
    */
    public void firstClick(int i, int j){
        if(!buttons[i][j].getFlagged()){
            int count = 1;
            while(minefield.numCloseMines(i, j) != 0){
                System.out.println(count);
                System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
                minefield = new MineField(width, height, numMines);
                count++;



            }
            System.out.println(count);
            for(int i1 = 0; i1 < height; i1++){
                for(int j1 = 0; j1 < width; j1++){
                    if(buttons[i1][j1].getFlagged()){
                        buttons[i1][j1].toggleFlag(buttonWidth, buttonHeight);
                        info.updateFlagCount(i1, j1);
                    }
                }
            }


            isFirstClick = false;
            gameBoard.uncoverButton(i, j);
            if(info.getScore() != 0){
                watch.resume();
            }
        }



    }

    //stops the watch and sets finished to true so no modifications can be made to the board.
    public void finish(){
        watch.pause();
        finished = true;

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    /*
        if the key pressed is the flagToggleKey, a flag is toggled on the place where the mouse is hovering.
        if the key pressed is the buttonActionKey, the place where the mouse is hovering is uncovered.
        if the key pressed is esc, menuButton is clicked and the menu opens (or closes if inMenu is true)
     */
    public void keyPressed(KeyEvent e) {
        char key = e.getKeyChar();


        if(key == flagToggleKey || key == buttonActionKey){
            for(int i = 0; i < height; i++){
                for(int j = 0; j < width; j++){
                    if(buttons[i][j].getMouseOn() && !finished){
                        if(key == flagToggleKey) {
                            buttons[i][j].toggleFlag(buttonWidth, buttonHeight);
                            info.updateFlagCount(i, j);
}
                        if(key == buttonActionKey){
                            gameBoard.uncoverButton(i, j);
                        }
                    }
                }
            }
        }
        if(e.getKeyCode() == 27){
            info.menuButton.doClick();
        }


    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    /*
        right click toggles flag, left click uncovers the square.
     */
    public void mousePressed(MouseEvent e) {

        if(e.getSource() instanceof BetterButton){
            for(int i = 0; i < height; i++) {
                for(int j = 0; j < width; j++){
                    if(e.getSource() == buttons[i][j]){
                        if(SwingUtilities.isRightMouseButton(e)){
                            buttons[i][j].toggleFlag(buttonWidth, buttonHeight);
                            info.updateFlagCount(i, j);
                        }
                        else if(SwingUtilities.isLeftMouseButton(e)){
                            gameBoard.uncoverButton(i, j);
                        }
                    }
                }
            }


        }


    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    /*
        every BetterButton object within the buttons array has a mouseOn boolean value which is false unless
        the mouse is currently hovering over that button. This allows keyListener to trigger a specific button
        when a key is pressed.
        mouseEntered and mouseExited update those boolean values whenever the mouse moves.
     */
    public void mouseEntered(MouseEvent e) {
        if(e.getSource() instanceof JButton){
            for(int i = 0; i < height; i++){
                for(int j = 0; j < width; j++){
                    if(e.getSource() == buttons[i][j]){
                        buttons[i][j].setMouseOn(true);
                    }
                }
            }
        }

    }

    @Override
    public void mouseExited(MouseEvent e) {

        if(e.getSource() instanceof JButton){
            for(int i = 0; i < height; i++){
                for(int j = 0; j < width; j++){
                    if(e.getSource() == buttons[i][j]){
                        buttons[i][j].setMouseOn(false);
                    }
                }
            }
        }
    }

    //nested class extending JPanel which represents the game menu and holds all
    //the textFields and labels for the menu.
    public class Menu extends JPanel implements ActionListener{
        JTextField[] textFields = new JTextField[5];
        JLabel header = new JLabel();
        JLabel[] labels = new JLabel[5];
        JButton startButton = new JButton("Start New Game");


        public Menu(){
            setLayout(new GridLayout(12, 1));
            setBounds(540, 80, 500, 700);
            fillPanel();


        }

        public void fillPanel(){
            for(int i = 0; i < 5; i++){
                textFields[i] = new JTextField();
                textFields[i].setFont(new Font("Comic Sans", Font.BOLD, 40));
                textFields[i].setHorizontalAlignment(JTextField.CENTER);

                labels[i] = new JLabel();
                labels[i].setFont(new Font("Comic Sans", Font.BOLD, 40));
                labels[i].setHorizontalAlignment(JLabel.CENTER);


            }

            labels[0].setText("Flag Hotkey:");
            add(labels[0]);
            add(textFields[0]);
            labels[1].setText("Reveal Hotkey:");
            add(labels[1]);
            add(textFields[1]);


            header.setFont(new Font("Comic Sans", Font.BOLD, 60));
            header.setForeground(Color.blue);
            header.setHorizontalAlignment(JLabel.CENTER);
            header.setText("New Game");
            add(header);

            labels[2].setText("Width:");
            labels[2].setForeground(Color.blue);
            add(labels[2]);
            add(textFields[2]);
            labels[3].setText("Height:");
            labels[3].setForeground(Color.blue);
            add(labels[3]);
            add(textFields[3]);
            labels[4].setText("Number of Mines:");
            labels[4].setForeground(Color.blue);
            add(labels[4]);
            add(textFields[4]);

            startButton.setFont(new Font("Comic Sans", Font.PLAIN, 50));
            startButton.setForeground(Color.RED);
            startButton.setFocusable(false);
            startButton.addActionListener(this);

            add(startButton);


        }


        @Override
        public void actionPerformed(ActionEvent e) {
            if(e.getSource() == startButton){

                flagToggleKey = textFields[0].getText().charAt(0);
                buttonActionKey = textFields[1].getText().charAt(0);

                width = Integer.parseInt(textFields[2].getText());
                height = Integer.parseInt(textFields[3].getText());
                numMines = Integer.parseInt(textFields[4].getText());

                gameBoard = new GameBoard();
                if(info != null) {
                  MinesweeperFrame.this.remove(info);
                }
                info = new InfoDisplay();
            }

        }
    }

    //nested class extending JPanel which represents the game board and holds
    //methods regarding the board and revealing buttons.
    public class GameBoard extends JPanel implements ActionListener{

        public GameBoard(){
            setLayout(new GridLayout(height, width));
            setBounds(20, 100, 1500, 660);
            setBackground(Color.black);
            MinesweeperFrame.this.add(this);
            createNewGame();

        }

        public void createNewGame(){
            inMenu = false;
            isFirstClick = true;
            finished = false;

            if(info != null) {
                info.createLabels();
                info.menuButton.setText("Options");
            }
            MinesweeperFrame.this.remove(menu);

            buttons = new BetterButton[height][width];
            createButtons();

            buttonHeight = this.getHeight()/height;
            buttonWidth = this.getWidth()/width;
            minefield = new MineField(width, height, numMines);


            MinesweeperFrame.this.revalidate();
            MinesweeperFrame.this.repaint();




        }

        public void createButtons(){
            for(int i = 0; i < height; i++){
                for(int j = 0; j < width; j++){

                    buttons[i][j] = new BetterButton();
                    buttons[i][j].setFocusable(false);
                    buttons[i][j].setOpaque(true);
                    buttons[i][j].setBorderPainted(false);
                    buttons[i][j].addMouseListener(MinesweeperFrame.this);
                    buttons[i][j].addKeyListener(MinesweeperFrame.this);
                    if((i + j) % 2 == 0){
                        buttons[i][j].setBackground(new Color(170, 170, 170));
                    }
                    else{
                        buttons[i][j].setBackground(new Color(150, 150, 150));
                    }
                    this.add(buttons[i][j]);
                }
            }
        }

        public void uncoverButton(int i, int j){
            if(isFirstClick){
                firstClick(i, j);

            }

            else if(!finished && buttons[i][j].isEnabled1()){
                info.setScore(info.getScore()-1);
                if(minefield.numCloseMines(i, j) == -1){
                    gameBoard.revealMines(i, j);
                    info.setScore(-1);
                }
                buttons[i][j].colorButton(minefield.numCloseMines(i, j));
                buttons[i][j].disable1();
                uncoverSurroundingButtons(i, j);

            }
            if(minefield.numCloseMines(i, j) != 0){
                gameBoard.revalidate();
                gameBoard.repaint();
            }

        }

        public void uncoverSurroundingButtons(int i , int j){
            if(minefield.numCloseMines(i, j) == 0){


                if(i>0 && j>0 && buttons[i-1][j-1].isEnabled1()){
                    uncoverButton(i-1, j-1);
                }
                if(i>0 && buttons[i-1][j].isEnabled1()){
                    uncoverButton(i-1, j);
                }
                if(i>0 && j< width-1 && buttons[i-1][j+1].isEnabled1()){
                    uncoverButton(i-1, j+1);
                }
                if(j>0 && buttons[i][j-1].isEnabled1()){
                    uncoverButton(i, j-1);
                }
                if(j< width-1 && buttons[i][j+1].isEnabled1()){
                    uncoverButton(i, j+1);
                }
                if(i< height - 1 && j>0 && buttons[i+1][j-1].isEnabled1()){
                    uncoverButton(i+1, j-1);
                }
                if(i< height - 1 && buttons[i+1][j].isEnabled1()){
                    uncoverButton(i+1, j);
                }
                if(i< height-1 && j< width-1 && buttons[i+1][j+1].isEnabled1()) {
                    uncoverButton(i+1, j+1);
                }
            }
        }

        public void revealMines(int k, int l){
            finish();
            for(int i = 0; i < height; i++){
                for(int j = 0; j < width; j++){
                    if(!buttons[i][j].getFlagged()) {
                        if (i == k && j == l) {
                            ImageIcon mine = new ImageIcon("Explosion-8.png");
                            Image image = mine.getImage();
                            image = image.getScaledInstance(buttonWidth, buttonHeight, Image.SCALE_SMOOTH);
                            mine = new ImageIcon(image);
                            buttons[i][j].setIcon(mine);
                            buttons[i][j].setBackground(Color.red);
                        }
                        else if (minefield.numCloseMines(i, j) == -1) {
                            ImageIcon mine = new ImageIcon("Explosion-8.png");
                            Image image = mine.getImage();
                            image = image.getScaledInstance(buttonWidth, buttonHeight, Image.SCALE_SMOOTH);
                            mine = new ImageIcon(image);
                            buttons[i][j].setIcon(mine);
                            //buttons[i][j].setBackground(Color.black);
                        }
                    }
                    if(buttons[i][j].getFlagged() && minefield.numCloseMines(i, j) != -1){
                        buttons[i][j].setForeground(Color.red);
                        buttons[i][j].setHorizontalTextPosition(JButton.CENTER);
                        ImageIcon wrongflag = new ImageIcon("american_flag_x.png");
                        Image image = wrongflag.getImage();
                        image = image.getScaledInstance(buttonWidth, buttonHeight, Image.SCALE_SMOOTH);
                        wrongflag = new ImageIcon(image);
                        buttons[i][j].setIcon(wrongflag);
                    }
                }
            }
        }



        @Override
        public void actionPerformed(ActionEvent e) {

        }
    }

    //nested class extending JPanel which holds the info above the game (score, flags, timer, menu button).
    public class InfoDisplay extends JPanel implements ActionListener{
        int score;
        int flagCount;
        JLabel scoreLabel;
        JLabel flagCountLabel;
        JButton menuButton;



        public InfoDisplay(){
            score = height * width - numMines;
            flagCount = numMines;

            this.setLayout(null);
            this.setBounds(0, 0, 1500, 80);
            this.setBackground(Color.black);
            MinesweeperFrame.this.add(this);

            createLabels();
            createMenuButton();

            watch = new Stopwatch();
            add(watch);



        }

        public void createLabels(){

            scoreLabel = new JLabel("Non Mines Left: " + score);
            scoreLabel.setBounds(20, 0, 1200, 90);
            scoreLabel.setForeground(new Color(50, 200, 255));
            scoreLabel.setFont(new Font("Comic Sans", Font.BOLD, 50));
            this.add(scoreLabel);


            flagCountLabel = new JLabel("Flags Left: " + flagCount);
            flagCountLabel.setFont(new Font("Comic Sans", Font.BOLD, 40));
            flagCountLabel.setForeground(Color.blue);
            flagCountLabel.setBounds(600, 25, 300, 50);
            this.add(flagCountLabel);

        }

        public void createMenuButton(){
            menuButton = new JButton("Options");
            menuButton.addActionListener(this);
            menuButton.setBounds(1200, 25, 100, 50);
            menuButton.setFocusable(false);
            this.add(menuButton);
        }

        public void setScore(int score){
            this.score = score;
            if(score == -1){
                scoreLabel.setText("WOMP WOMP");
                finish();
            }
            else if(score == 0){
                scoreLabel.setText("You Win!");
                finish();
            }
            else {
                scoreLabel.setText("Non Mines Left: " + score);
            }
        }

        public int getScore(){
            return score;
        }

        public void updateFlagCount(int i, int j){
            if(buttons[i][j].getFlagged()){
                flagCount--;
            }
            if(!buttons[i][j].getFlagged() && buttons[i][j].isEnabled1()){
                flagCount++;
            }

            flagCountLabel.setText("Flags Left: " + flagCount);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if(!inMenu) {
                MinesweeperFrame.this.remove(gameBoard);
                MinesweeperFrame.this.add(menu);
                menuButton.setText("Return");
                MinesweeperFrame.this.revalidate();
                MinesweeperFrame.this.repaint();
                inMenu = true;

                for(int k = 0; k < height; k++){
                    for(int l = 0; l < width; l++){
                        buttons[k][l].setMouseOn(false);
                    }
                }
                watch.pause();
            }
            else{
                flagToggleKey = menu.textFields[0].getText().charAt(0);
                buttonActionKey = menu.textFields[1].getText().charAt(0);
                MinesweeperFrame.this.remove(menu);
                MinesweeperFrame.this.add(gameBoard);
                menuButton.setText("Options");
                MinesweeperFrame.this.revalidate();
                MinesweeperFrame.this.repaint();
                inMenu = false;
                if(!finished){
                    watch.resume();
                }
            }
        }
    }

    //nested class extending JLabel which holds a timer display
    public static class Stopwatch extends JLabel implements Runnable{
        ScheduledExecutorService scheduler;
        long startTime;
        long elapsedTime;
        double seconds = 0;
        int minutes = 0;
        int hours = 0;
        String secondsString;
        String minutesString;
        String hoursString;
        String fullString;
        boolean running = false;


        public Stopwatch(){

            secondsString = String.format("%04.1f", seconds);
            minutesString = String.format("%02d", minutes);
            hoursString = String.format("%01d", hours);
            fullString = hoursString + ":" + minutesString + ":" + secondsString;
            setText(fullString);
            setBounds(885, 25, 300, 40);

            setForeground(Color.green);
            setOpaque(true);
            setBackground(Color.black);
            setFont(new Font("Comic Sans", Font.BOLD, 50));
            setHorizontalAlignment(JTextField.CENTER);


            scheduler = Executors.newScheduledThreadPool(1);
            startTime = System.currentTimeMillis()/100;



        }

        @Override
        public void run() {
            if(running){
                elapsedTime = (System.currentTimeMillis()/100 - startTime);
                seconds = (elapsedTime / 10.0) % 60;
                minutes = (int)(elapsedTime / 600) % 60;
                hours = (int)(elapsedTime / 36000);
                secondsString = String.format("%04.1f", seconds);
                minutesString = String.format("%02d", minutes);
                hoursString = String.format("%01d", hours);
                fullString = hoursString + ":" + minutesString + ":" + secondsString;
                setText(fullString);

                System.out.println(elapsedTime);
            }

        }

        public void pause(){
            if(running){
                running = false;
                elapsedTime = (System.currentTimeMillis()/100 - startTime);
            }
        }

        public void resume(){
            if(!running){
                running = true;
                startTime = System.currentTimeMillis()/100 - elapsedTime;
                scheduler.scheduleAtFixedRate(this, 0, 100,TimeUnit.MILLISECONDS);


            }

        }


    }




}
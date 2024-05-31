//import javax.swing.*;
//import java.awt.*;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//
//public class Menu extends JPanel implements ActionListener {
//
//    JTextField[] textFields = new JTextField[5];
//    JLabel header = new JLabel();
//    JLabel[] labels = new JLabel[5];
//    JButton startButton = new JButton("Start New Game");
//
//
//    public Menu(){
//        setLayout(new GridLayout(12, 1));
//        setBounds(30, 30, 500, 700);
//        fillPanel();
//
//    }
//
//    public void fillPanel(){
//        for(int i = 0; i < 5; i++){
//            textFields[i] = new JTextField();
//            textFields[i].setFont(new Font("Comic Sans", Font.BOLD, 40));
//            textFields[i].setHorizontalAlignment(JTextField.CENTER);
//
//            labels[i] = new JLabel();
//            labels[i].setFont(new Font("Comic Sans", Font.BOLD, 40));
//            labels[i].setHorizontalAlignment(JLabel.CENTER);
//
//        }
//
//        labels[0].setText("Flag Hotkey:");
//        add(labels[0]);
//        add(textFields[0]);
//        labels[1].setText("Reveal Hotkey");
//        add(labels[1]);
//        add(textFields[1]);
//
//
//        header.setFont(new Font("Comic Sans", Font.BOLD, 60));
//        header.setForeground(Color.blue);
//        header.setHorizontalAlignment(JLabel.CENTER);
//        header.setText("New Game");
//        add(header);
//
//        labels[2].setText("Width:");
//        labels[2].setForeground(Color.blue);
//        add(labels[2]);
//        add(textFields[2]);
//        labels[3].setText("Height:");
//        labels[3].setForeground(Color.blue);
//        add(labels[3]);
//        add(textFields[3]);
//        labels[4].setText("Number of Mines:");
//        labels[4].setForeground(Color.blue);
//        add(labels[4]);
//        add(textFields[4]);
//
//        startButton.setFont(new Font("Comic Sans", Font.PLAIN, 50));
//        startButton.setForeground(Color.RED);
//        startButton.setFocusable(false);
//        startButton.addActionListener(this);
//
//        add(startButton);
//
//
//    }
//
//
//    @Override
//    public void actionPerformed(ActionEvent e) {
//        if(e.getSource() == startButton){
//
//            MinesweeperFrame.flagToggleKey = textFields[0].getText().charAt(0);
//            MinesweeperFrame.buttonActionKey = textFields[1].getText().charAt(0);
//
//            MinesweeperFrame.width = Integer.parseInt(textFields[2].getText());
//            MinesweeperFrame.height = Integer.parseInt(textFields[3].getText());
//            MinesweeperFrame.numMines = Integer.parseInt(textFields[4].getText());
//
//        }
//
//    }
//}

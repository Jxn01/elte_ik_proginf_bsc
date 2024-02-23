package javaapplication10;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class JavaApplication10{
  private JFrame frame;
  private JPanel panel;
  private JButton[][] buttons;
  private RubikCube2DTable table;
  private int size;
  private int buttonSize;
  private int buttonGap;
  private int buttonBorder;
  private int buttonBackgroundColor;

  public JavaApplication10(int size, int buttonSize, int buttonGap, int buttonBorder, int buttonBackgroundColor){
    this.size = size;
    this.buttonSize = buttonSize;
    this.buttonGap = buttonGap;
    this.buttonBorder = buttonBorder;
    this.buttonBackgroundColor = buttonBackgroundColor;
    table = new RubikCube2DTable(size);
    frame = new JFrame("Rubik's Cube 2D Table");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(size * (buttonSize + buttonGap) + buttonGap, size * (buttonSize + buttonGap) + buttonGap);
    frame.setLocationRelativeTo(null);
    frame.setResizable(false);
    panel = new JPanel();
    panel.setLayout(null);
    buttons = new JButton[size][size];
    for(int i = 0; i < size; i++){
      for(int j = 0; j < size; j++){
        buttons[i][j] = new JButton();
        buttons[i][j].setBounds(buttonGap + j * (buttonSize + buttonGap), buttonGap + i * (buttonSize + buttonGap), buttonSize, buttonSize);
        buttons[i][j].setBorder(BorderFactory.createLineBorder(new Color(buttonBorder, buttonBorder, buttonBorder)));
        buttons[i][j].setBackground(new Color(buttonBackgroundColor, buttonBackgroundColor, buttonBackgroundColor));
        buttons[i][j].addActionListener(new ActionListener(){
          public void actionPerformed(ActionEvent e){
            JButton button = (JButton) e.getSource();
            int x = button.getX();
            int y = button.getY();
            int i = (y - buttonGap) / (buttonSize + buttonGap);
            int j = (x - buttonGap) / (buttonSize + buttonGap);
            table.rotate(i, j);
            update();
          }
        });
        panel.add(buttons[i][j]);
      }
    }
    frame.add(panel);
    frame.setVisible(true);
    update();
  }

  public void update(){
    for(int i = 0; i < size; i++){
      for(int j = 0; j < size; j++){
        buttons[i][j].setBackground(new Color(table.get(i, j), table.get(i, j), table.get(i, j)));
      }
    }
  }

  public static void main(String[] args){
    JavaApplication10 gui = new JavaApplication10(3, 100, 10, 0, 25);
  }

}

class RubikCube2DTable{
  private int[][] table;
  private int size;

  public RubikCube2DTable(int size){
    this.size = size;
    table = new int[size][size];
    for(int i = 0; i < size; i++){
      for(int j = 0; j < size; j++){
        table[i][j] = 255;
      }
    }
  }

  public void rotate(int i, int j){
    if(i < 0 || i >= size || j < 0 || j >= size){
      return;
    }
    int temp = table[i][j];
    table[i][j] = table[size - 1 - j][i];
    table[size - 1 - j][i] = table[size - 1 - i][size - 1 - j];
    table[size - 1 - i][size - 1 - j] = table[j][size - 1 - i];
    table[j][size - 1 - i] = temp;
  }

  public int get(int i, int j){
    return table[i][j];
  }

  public void set(int i, int j, int value){
    table[i][j] = value;
  }

  public int getSize(){
    return size;
  }

  
}

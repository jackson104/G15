package FiveChess;

import java.awt.event.*;
import java.awt.*;

import javax.swing.*;

//製作按鈕

public class button extends JFrame {
  private ChessBoard chessBoard;
  private JPanel toolbar;
  private JButton startButton,backButton,exitButton;
  
  private JMenuBar menuBar;
  private JMenu sysMenu;
  private JMenuItem startMenuItem,exitMenuItem,backMenuItem;
  //按鈕選項：重新開始，離開遊戲，悔棋
  public button(){
      set("五子棋");
      chessBoard=new ChessBoard();
    
      
      Container contentPane=getContentPane();
      contentPane.add(chessBoard);
      chessBoard.setOpaque(true);
      //此事件設置控制是否透明。true表示不透明，false表示透明。
      
      
      //建立選單
      menuBar =new JMenuBar();
      sysMenu=new JMenu("選項");
      //建立選單項目
      startMenuItem=new JMenuItem("重新開始");
      exitMenuItem =new JMenuItem("離開遊戲");
      backMenuItem =new JMenuItem("悔棋");
      //將選單加入項目裡
      sysMenu.add(startMenuItem);
      sysMenu.add(exitMenuItem);
      sysMenu.add(backMenuItem);
      //初始化按紐事件
      MyItemListener listener=new MyItemListener();
      //將三個項目放置到監聽器介面上 參考課本278
      this.startMenuItem.addActionListener(listener);
      backMenuItem.addActionListener(listener);
      exitMenuItem.addActionListener(listener);
      menuBar.add(sysMenu);
      setJMenuBar(menuBar);
      
      toolbar=new JPanel();
      //三個按鈕初始化
      startButton=new JButton("重新開始");
      exitButton=new JButton("離開遊戲");
      backButton=new JButton("悔棋");
      
      toolbar.setLayout(new FlowLayout(FlowLayout.RIGHT));
      
      toolbar.add(startButton);
      toolbar.add(exitButton);
      toolbar.add(backButton);
      //按鈕中添加事件
      startButton.addActionListener(listener);
      exitButton.addActionListener(listener);
      backButton.addActionListener(listener);
      
      add(toolbar,BorderLayout.NORTH);
      add(chessBoard);
      
      pack();
      
  }
  
  private void set(String string) {
	
	
}

private class MyItemListener implements ActionListener{
      public void actionPerformed(ActionEvent e){
          Object object=e.getSource();
          if(object==button.this.startMenuItem||object==startButton){
              //重新开始
              System.out.println("重新开始");
              chessBoard.restartGame();
          }
          else if (object==exitMenuItem||object==exitButton)
              System.exit(0);
          else if (object==backMenuItem||object==backButton){
              System.out.println("悔棋...");
              chessBoard.goback();
          }
      }
  }
  
  public static void main(String[] args){
      button frame=new button();
      frame.setVisible(true);
      //課本250頁
  }
}
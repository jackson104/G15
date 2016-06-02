package FiveChess;


import java.awt.Color;

//棋子

public class Chess {
  private int x;//棋子在格線中座標
  private int y;
  private Color color;
  public static int DIAMETER=30;//棋子直徑
  
  public Chess(int x,int y,Color color){
      this.x=x;
      this.y=y;
      this.color=color;
  } 
  
  public int getX(){
      return x;
  }
  public int getY(){
      return y;
  }
  public Color getColor(){//獲得的棋子顏色
      return color;
  }
}
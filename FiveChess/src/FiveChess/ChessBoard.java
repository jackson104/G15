package FiveChess;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RadialGradientPaint;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Ellipse2D;

import javax.swing.*;

//棋盤

public class ChessBoard extends JPanel implements MouseListener {
   public static int MARGIN=30;//外圍距離
   public static int GRID_SPAN=40;//格子長寬
   public static int ROWS=15;
   public static int COLS=15;
   
   Chess[] chessList=new Chess[(ROWS+1)*(COLS+1)];
   boolean isBlack=true;//黑期開局
   boolean gameOver=false;//判斷遊戲是否結束
   int chessCount;//棋盤上的棋子數量
   int xIndex,yIndex;//滑鼠可點擊之座標
   
   Image img;
   Color colortemp;
   public ChessBoard(){
	  
	   img=Toolkit.getDefaultToolkit().getImage("board.jpg");
	   addMouseListener(this);
	   addMouseMotionListener(new MouseMotionListener(){
		   public void mouseDragged(MouseEvent e){
			   
		   }
		   
		   public void mouseMoved(MouseEvent e){
		     int x1=(e.getX()-MARGIN+GRID_SPAN/2)/GRID_SPAN;
		     int y1=(e.getY()-MARGIN+GRID_SPAN/2)/GRID_SPAN;
		     //遊戲結束時不能下
		     //棋盤外無法下棋
		     //x，y位置已經有棋子存在，不能下
		     if(x1<0||x1>ROWS||y1<0||y1>COLS||gameOver||findChess(x1,y1))
		    	 setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		     else setCursor(new Cursor(Cursor.HAND_CURSOR));
		     
		   }
	   });
   } 
   
  

   public void paintComponent(Graphics g){
	 
	   super.paintComponent(g);//繪製棋盤
	 
	   int imgWidth= img.getWidth(this);
	   int imgHeight=img.getHeight(this);//棋盤長寬
	   int FWidth=getWidth();
	   int FHeight=getHeight();//視窗長寬
	   int x=(FWidth-imgWidth)/2;
	   int y=(FHeight-imgHeight)/2;
	   g.drawImage(img, x, y, null);
	
	   
	   for(int i=0;i<=ROWS;i++){
		   g.drawLine(MARGIN, MARGIN+i*GRID_SPAN, MARGIN+COLS*GRID_SPAN, MARGIN+i*GRID_SPAN);
	   }
	   for(int i=0;i<=COLS;i++){
		   g.drawLine(MARGIN+i*GRID_SPAN, MARGIN, MARGIN+i*GRID_SPAN, MARGIN+ROWS*GRID_SPAN);
		   
	   }
	   
	   
	   for(int i=0;i<chessCount;i++){
		   int xPos=chessList[i].getX()*GRID_SPAN+MARGIN;
		   int yPos=chessList[i].getY()*GRID_SPAN+MARGIN;
		   g.setColor(chessList[i].getColor());
		   colortemp=chessList[i].getColor();
		   if(colortemp==Color.black){
			   RadialGradientPaint paint = new RadialGradientPaint(xPos-Chess.DIAMETER/2+25, yPos-Chess.DIAMETER/2+10, 20, new float[]{0f, 1f}
               , new Color[]{Color.WHITE, Color.BLACK});
               ((Graphics2D) g).setPaint(paint);
               ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
               ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_DEFAULT);

		   }
		   else if(colortemp==Color.white){
			   RadialGradientPaint paint = new RadialGradientPaint(xPos-Chess.DIAMETER/2+25, yPos-Chess.DIAMETER/2+10, 70, new float[]{0f, 1f}
               , new Color[]{Color.WHITE, Color.BLACK});
               ((Graphics2D) g).setPaint(paint);
               ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
               ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_DEFAULT);

		   }
		 
		   Ellipse2D e = new Ellipse2D.Float(xPos-Chess.DIAMETER/2, yPos-Chess.DIAMETER/2, 34, 35);
		   ((Graphics2D) g).fill(e);
	       //標記上一步所下的棋
		   
		   if(i==chessCount-1){//上一步棋外框為紅色
			   g.setColor(Color.red);
			   g.drawRect(xPos-Chess.DIAMETER/2, yPos-Chess.DIAMETER/2,
				           34, 35);
		   }
	   }
   }
   
   public void mousePressed(MouseEvent e){
	   
	   
	   if(gameOver) return;
	   
	   String colorName=isBlack?"黑棋":"白棋";
	   
	   //滑鼠點擊位置為棋盤的交叉格線
	   xIndex=(e.getX()-MARGIN+GRID_SPAN/2)/GRID_SPAN;
	   yIndex=(e.getY()-MARGIN+GRID_SPAN/2)/GRID_SPAN;
	   
	   //滑鼠無法點擊棋盤外
	   if(xIndex<0||xIndex>ROWS||yIndex<0||yIndex>COLS)
		   return;
	   
	   //當位置上有棋子時，不能再次下棋
	   if(findChess(xIndex,yIndex))return;
	   
	   
	   Chess ch=new Chess(xIndex,yIndex,isBlack?Color.black:Color.white);
	   chessList[chessCount++]=ch;
	    repaint();
	  
	   
	   //對話框
	   
	   if(isWin()){
		   String msg=String.format("%s勝利！", colorName);
		   JOptionPane.showMessageDialog(this, msg);
		   gameOver=true;
	   }
	   isBlack=!isBlack;
	 }
   //滑鼠互動
   public void mouseClicked(MouseEvent e){
   }
   
   public void mouseEntered(MouseEvent e){
   }
   public void mouseExited(MouseEvent e){
   }
   public void mouseReleased(MouseEvent e){
   }

   private boolean findChess(int x,int y){
	   for(Chess c:chessList){
		   if(c!=null&&c.getX()==x&&c.getY()==y)
			   return true;
	   }
	   return false;
   }
   
   
   private boolean isWin(){
	   int lineCount=1;//棋子的連線
	   //橫向
	   //往左
	   for(int x=xIndex-1;x>=0;x--){
		   Color c=isBlack?Color.black:Color.white;
		   if(getChess(x,yIndex,c)!=null){
			   lineCount++;
		   }else
			   break;
	   }
      //往右
       for(int x=xIndex+1;x<=COLS;x++){
	      Color c=isBlack?Color.black:Color.white;
	      if(getChess(x,yIndex,c)!=null){
		     lineCount++;
	      }else
		     break;
       }
       if(lineCount>=5){
	         return true;
       }else 
	   lineCount=1;
       
       //直向
       //往上
       for(int y=yIndex-1;y>=0;y--){
    	   Color c=isBlack?Color.black:Color.white;
    	   if(getChess(xIndex,y,c)!=null){
    		   lineCount++;
    	   }else
    		   break;
       }
       //往下
       for(int y=yIndex+1;y<=ROWS;y++){
    	   Color c=isBlack?Color.black:Color.white;
    	   if(getChess(xIndex,y,c)!=null)
    	       lineCount++;
           else
    	      break;
       
       }
       if(lineCount>=5)
    	   return true;
       else
    	   lineCount=1;
       
       
       //斜向
       //右上
       for(int x=xIndex+1,y=yIndex-1;y>=0&&x<=COLS;x++,y--){
    	   Color c=isBlack?Color.black:Color.white;
    	   if(getChess(x,y,c)!=null){
    		   lineCount++;
    	   }
    	   else break;
       }
       //左下
       for(int x=xIndex-1,y=yIndex+1;x>=0&&y<=ROWS;x--,y++){
    	   Color c=isBlack?Color.black:Color.white;
    	   if(getChess(x,y,c)!=null){
    		   lineCount++;
    	   }
    	   else break;
       }
       if(lineCount>=5)
    	   return true;
       else lineCount=1;
       

       //左上
       for(int x=xIndex-1,y=yIndex-1;x>=0&&y>=0;x--,y--){
    	   Color c=isBlack?Color.black:Color.white;
    	   if(getChess(x,y,c)!=null)
    		   lineCount++;
    	   else break;
       }
       //右下
       for(int x=xIndex+1,y=yIndex+1;x<=COLS&&y<=ROWS;x++,y++){
    	   Color c=isBlack?Color.black:Color.white;
    	   if(getChess(x,y,c)!=null)
    		   lineCount++;
    	   else break;
       }
       if(lineCount>=5)
    	   return true;
       else lineCount=1;
       
       return false;
     }
   
   
   private Chess getChess(int xIndex,int yIndex,Color color){
	   for(Chess p:chessList){
		   if(p!=null&&p.getX()==xIndex&&p.getY()==yIndex
				   &&p.getColor()==color)
			   return p;
	   }
	   return null;
   }
   
   
   public void restartGame(){
	   //重新開始
	   for(int i=0;i<chessList.length;i++){
		   chessList[i]=null;
	   }
	   
	   isBlack=true;  //黑期開局
	   gameOver=false; //重設輸贏
	   chessCount =0; //棋子數歸零
	   repaint();
   }
   
   //悔棋
   public void goback(){
	   if(chessCount==0)
		   return ;
	   chessList[chessCount-1]=null;
	   chessCount--;
	   if(chessCount>0){
		   xIndex=chessList[chessCount-1].getX();
		   yIndex=chessList[chessCount-1].getY();
	   }
	   isBlack=!isBlack;
	   repaint();
   }
   


   public Dimension getPreferredSize(){
	   return new Dimension(MARGIN*2+GRID_SPAN*COLS,MARGIN*2
	                        +GRID_SPAN*ROWS);
   }         
}
package control;

import board.*;
import util.*;

public class WinChecking {
	public static boolean isWon(Position last,int stepWin) {
        int count = 1;      //本身一点为 1
        int posX = 0;
        int posY = 0;
        
        int WinSteps=stepWin;
        
        int x=last.pos[0];
        int y=last.pos[1];
        BoardBuffer boardBuffer=Controller.mapbuffer.get();
        Conditions color=boardBuffer.board.get(last).p;
        /**判断水平方向上的胜负
        /* 将水平方向以传入的点x上的y轴作为分隔线分为两部分
         * 先向左边遍历，判断到的相同的连续的点  count++
         */
        for(posX = x - 1; posX > 0 ; posX--) {
        	Position tmPosition=new Position(posX, y);
            if (boardBuffer.board.get(tmPosition)!=null 
            		&& boardBuffer.board.get(tmPosition).p == color) {
                count++;
                if (count >= WinSteps) {
                    return true;
                }
            }else {
                break;
            }
        }    //向右边遍历
        for(posX = x + 1; posX <= Limits.width; posX++) {
        	Position tmPosition=new Position(posX, y);
            if (boardBuffer.board.get(tmPosition)!=null 
            		&&boardBuffer.board.get(tmPosition).p == color) {
                count++;
                if (count >= WinSteps) {
                    return true;
                }
            }else {
                break;
            }
        }
        count=1;
        /**判断垂直方向上的胜负
        /* 将垂直方向以传入的点y上的x轴作为分隔线分为两部分
         * 先向上遍历，判断到的相同的连续的点  count++
         */
        for(posY = y - 1; posY > 0; posY--) {
        	Position tmPosition=new Position(x, posY);
            if (boardBuffer.board.get(tmPosition)!=null 
            		&&boardBuffer.board.get(tmPosition).p == color) {
                count++;
                if (count >= WinSteps) {
                    return true;
                }
            }else {
                break;
            }
        }//向下遍历
        for(posY = y + 1; posY <= Limits.width; posY++) {
        	Position tmPosition=new Position(x, posY);
            if (boardBuffer.board.get(tmPosition)!=null 
            		&&boardBuffer.board.get(tmPosition).p == color) {
                count++;
                if (count >= WinSteps) {
                    return true;
                }
            }else {
                break;
            }
        }
        count=1;
        /**判断左上右下方向上的胜负
         * 以坐标点为分割线，将棋盘分为左右两个等腰三角形
         * 先判断左边的
         */
        for(posX = x - 1, posY = y - 1; posX > 0 && posY > 0; posX--, posY--) {
        	Position tmPosition=new Position(posX, posY);
            if (boardBuffer.board.get(tmPosition)!=null 
            		&&boardBuffer.board.get(tmPosition).p == color) {
                count++;
                if (count >= WinSteps) {
                    count = 1;
                    return true;
                }
            }else {
                break;
            }
        }//判断右边的
        for(posX = x + 1, posY = y + 1; posX <= Limits.width && posY <= Limits.width; posX++, posY++) {
        	Position tmPosition=new Position(posX, posY);
            if (boardBuffer.board.get(tmPosition)!=null 
            		&&boardBuffer.board.get(tmPosition).p == color) {
                count++;
                if (count >= WinSteps) {
                    count = 1;
                    return true;
                }
            }else {
                break;
            }
        }
        count=1;
        /**判断右下左下方向上的胜负
         * 以坐标点为分割线，将棋盘分为左右两个等腰三角形
         * 先判断左边的
         */
        for(posX = x + 1, posY = y - 1; posX <= Limits.width && posY > 0; posX++, posY--) {
        	Position tmPosition=new Position(posX, posY);
            if (boardBuffer.board.get(tmPosition)!=null 
            		&&boardBuffer.board.get(tmPosition).p == color) {
                count++;
                if (count >= WinSteps) {
                    return true;
                }
            }else {
                break;
            }
        }//判断右边的
        for(posX = x - 1, posY = y + 1; posX > 0 && posY <= Limits.width; posX--, posY++) {
        	Position tmPosition=new Position(posX, posY);
            if (boardBuffer.board.get(tmPosition)!=null 
            		&&boardBuffer.board.get(tmPosition).p == color) {
                count++;
                if (count >= WinSteps) {
                    return true;
                }
            }else {
                break;
            }
        }
        return false;
    }
}

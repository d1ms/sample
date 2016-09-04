package sample;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Created by window on 18.06.2016.
 */
public class UserSnake extends Snake {
    private static int score;
    private static LinkedList<String> movesList;

    protected UserSnake (){
        movesList = new LinkedList<String>();
        movesList.add("right");
    }

    public static int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void incScore(int type) {
        this.score = this.score + 100 * ( this.length() - type ) ;
    }

    public static void addMove(String type){
        if( Game.isActive() ) {
            String lastMove = movesList.getLast();
            if( lastMove.compareTo(type) != 0 )
            switch (type)
            {
                case "top": if( lastMove.compareTo("down")  != 0 ) movesList.addLast(type); break;
                case "left": if( lastMove.compareTo("right") != 0 ) movesList.addLast(type); break;
                case "down": if( lastMove.compareTo("top") != 0 ) movesList.addLast(type); break;
                case "right": if( lastMove.compareTo("left") != 0 ) movesList.addLast(type); break;
                default: break;
            }

        }
    }

    public static void clearScore(){
        score = 0;
    }

    public static String getThisMove(){
        String t = movesList.get(0);
        return t;
    }

    public static void clearThisMove(){
        if(movesList.size() > 1)
            movesList.remove(0);
    }
}

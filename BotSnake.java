package sample;

import java.util.ArrayList;

/**
 * Created by window on 21.06.2016.
 */
public class BotSnake extends Snake {
    private  String lastMove ;
    protected BotSnake(){
        ArrayList<Point> n = new ArrayList<Point>();
        n.add( new Point(9,7));
        n.add( new Point(9,8));
        n.add(  new Point(9,9) );
        this.setPosition(n);
    }

    public void go(Point apple){
        Point head = this.getHead();

        int compareX = Integer.compare(apple.getX() , head.getX() );
        int compareY = Integer.compare(apple.getY() , head.getY() );

        switch (compareX){
            case -1: { if(lastMove != "right") { move("left" , Game.size); lastMove ="left"; } else { move("top" , Game.size);lastMove ="top";}   } break;
            case 1: { if( lastMove != "left")  { move("right" , Game.size); lastMove ="right";  } else {move("down" , Game.size); lastMove ="down";}} break;
            default: switch (compareY){
                case -1:  if(lastMove != "down") { move("top" , Game.size);lastMove ="top";  } else { move("left" , Game.size); lastMove ="left";}; break;
                case 1: if(lastMove != "top") { move("down" , Game.size); lastMove ="down";  } else {move("right" , Game.size); lastMove ="right";}; break;
                default: move("right" , Game.size); lastMove = "right"; break;
            } break;
        }
        }

        public boolean isUserCrashed( ArrayList<Point> position){
            for (int i=1; i < length(); i++){
                Point p1 = getPosition().get(i);
                for (Point p: position  ) {
                    if( (p.getX() == p1.getX() ) && (p.getY() == p1.getY() ) )
                    return true;
                }
            }
            return false;
        }

}

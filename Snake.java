package sample;

import java.util.ArrayList;

/**
 * Created by window on 16.06.2016.
 */
abstract class Snake { // Змея
    private ArrayList<Point> position;

    protected Snake(ArrayList<Point> position) {
        this.position = new ArrayList<Point>();
        this.position = position;
    }

    protected Snake() {
        position = new ArrayList<Point>();
        position.add( new Point(2,1) );
        position.add( new Point(1,1) );
        position.add( new Point(0,1) );


    }

    public int length(){
        return this.position.size();
    }

    public boolean isEatApple(Point apple){
        Point headSnake = position.get(0);
        return ( apple.getX() == headSnake.getX() ) && (apple.getY() == headSnake.getY() );
    }

    public Point getHead(){
        return position.get(0);
    }

    public Point getTail(){
        return position.get(position.size() - 1);
    }

    public void setPosition(ArrayList<Point> position ){
        this.position = position;
    }

    public boolean isHavePoint(Point point){
        for (Point p : position) {
            if ( ( p.getX() == point.getX() ) && (p.getY() == point.getY() ) )
                return true;
        }
        return false;
    }

    /** Врезалась ли змейка в себя */
    public boolean isCrashSelf(){
        Point headSnake = position.get(0);
        for (int i = 1;i < position.size(); i++) {
            if ( ( position.get(i).getX() == headSnake.getX() ) && (position.get(i).getY() == headSnake.getY() ) )
                return true;
        }
        return false;
    }

    public void addToTail(){

    }

    public void deleteTail(){
        this.position.remove(position.size() - 1);
    }
    public ArrayList<Point> getPosition(){
        return position;
    }

    public ArrayList<Point> move(String type , int fieldSize){

        Point headPoint = position.get(0);

        if( type.compareTo("top") == 0) {
            int newY = (headPoint.getY() == 0 )? fieldSize - 1: headPoint.getY() - 1;
            position.add(0, new Point(headPoint.getX() , newY ) )  ;
        } else

        if( type.compareTo("right") == 0 ) {
            int newX = (headPoint.getX() == ( fieldSize - 1 ) )? 0: headPoint.getX() + 1;
            position.add(0, new Point( newX , headPoint.getY() ) )  ;
        } else

        if( type.compareTo("down") == 0 ) {
            int newY = (headPoint.getY() == ( fieldSize - 1 ) )? 0 : headPoint.getY() + 1;
            position.add(0, new Point(headPoint.getX() , newY ) )  ;
        } else { // left
            int newX = (headPoint.getX() == 0 )? ( fieldSize - 1 ) : headPoint.getX() - 1;
            position.add(0, new Point( newX , headPoint.getY() ) )  ;
        }

        return position;

    }
}


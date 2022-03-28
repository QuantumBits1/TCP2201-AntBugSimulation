package com.company;

//Bug class that extends the Organism class
public class Bug extends Organism {
    private static final int breedThreshold = 8;
    private static final int starveThreshold = 3;
    private boolean hasEaten;

    //constructor method
    public Bug(){
        timeStep = 0;
        hasEaten = false;
    }

    //to read the time step for bug to breed
    public static int getBreedThreshold(){return breedThreshold;}

    //to read the time step for bug starve
    public static int getStarveThreshold(){return starveThreshold;}

    //to update if the bug has eaten
    public void setHasEaten(boolean t){hasEaten = t;}

    //to read if the bug has eaten
    public boolean getHasEaten(){return hasEaten;}

    @Override
    //overridden method to update the time step
    protected void setTimeStep(int t) {timeStep=t;}

    @Override
    //overridden method to read the time step
    protected int getTimeStep() {return timeStep;}

    @Override
    //overridden method to perform the move behaviour
    protected void move(int direction) {
        switch(direction){
            case 0:
                setX(getX()-1);
                break;
            case 1 :
                setY(getY()+1);
                break;
            case 2 :
                setX(getX()+1);
                break;
            case 3 :
                setY(getY()-1);
                break;
        }
    }

    @Override
    //overridden method to perform the breed behaviour
    protected void breed(int x, int y) {
        setX(x);
        setY(y);
    }

    @Override
    //overridden method to update the horizontal position
    protected void setX(int x) {positionX = x;}

    @Override
    //overridden method to update the vertical position
    protected void setY(int y) {positionY = y;}

    @Override
    //overridden method to read the horizontal position
    protected int getX() {return positionX;}

    @Override
    //overridden method to read the vertical position
    protected int getY() {return positionY;}

}

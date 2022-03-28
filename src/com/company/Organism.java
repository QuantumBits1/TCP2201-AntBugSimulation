package com.company;

//Organism class as the Model
public  class Organism {
    protected int positionX;
    protected int positionY;
    protected int timeStep;

    //constructor method
    public Organism(){}

    //to updates the time step for organism
    protected  void setTimeStep(int t){timeStep=t;}

    //to read the time step for organism
    protected  int getTimeStep(){return timeStep;}

    //method to perform the move behaviour
    protected  void move(int direction){
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

    //method to perform the breed behaviour
    protected  void breed(int x, int y){
        setX(x);
        setY(y);
    }

    //to update the horizontal position of organism
    protected  void setX(int x){positionX = x;}

    //to update the vertical position of organism
    protected  void setY(int y){positionY = y;}

    //to read the horizontal position of organism
    protected  int getX(){return positionX;}

    //to read the vertical position of organism
    protected  int getY(){return positionY;}
}

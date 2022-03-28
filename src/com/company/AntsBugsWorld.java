package com.company;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.LinkedList;
import java.util.Random;

//AntsBugsWorld acts as the Controller based on the MVC design pattern
//AntsBugsWorld enclosed for the creatures (ants & bugs) to perform any movement inside the grid world
public class AntsBugsWorld implements KeyListener {
    private Organism[][] organismWorld;
    private static GUI window;
    private static Timer timer;
    private LinkedList<Ant> antList = new LinkedList<>();
    private LinkedList<Bug> bugList = new LinkedList<>();
    private Random randomNum = new Random();
    private static final int dimX = 20;
    private static final int dimY = 20;
    private static int noOfAnt = 100;
    private static int noOfBug = 5;

    //constructor
    public AntsBugsWorld(){
        organismWorld = new Organism[dimY][dimX];
        window = new GUI(dimX,dimY);
        
        for (int i = 0; i < dimY; i++) {
            for (int j = 0; j < dimX; j++)
                organismWorld[i][j] = null;
        }

        for(int i = 0; i< noOfAnt; i++){
            initializeWorld(new Ant());
        }

        for(int i = 0; i< noOfBug; i++){
            initializeWorld(new Bug());
        }

        window.addKeyListener(this);
    }

    //fill up the grid cell with ants and bugs randomly
    public void initializeWorld(Organism Insect){
        int i = randomNum.nextInt(20);
        int j = randomNum.nextInt(20);

        while(!isEmpty(j+1, dimY-i)){
            i = randomNum.nextInt(20);
            j = randomNum.nextInt(20);
        }

        if(Insect instanceof Ant) {
            organismWorld[i][j] = new Ant();
            antList.add((Ant)organismWorld[i][j]);
        }
        else if(Insect instanceof Bug) {
            organismWorld[i][j] = new Bug();
             bugList.add((Bug)organismWorld[i][j]);
        }
        window.fill(Insect,i,j);
        organismWorld[i][j].setX(j+1);
        organismWorld[i][j].setY(dimY-i);
    }

    //to identify the occupied cell is the ant
    public boolean isAnt(int x, int y){
        return organismWorld[dimY - y][x - 1] instanceof Ant;
    }

    //to return the value of the timer
    public static Timer getTimer(){return timer;}

    //to ensure the organism is in the grid cell layout
    public boolean isInsideWorld(int x, int y){
        return (x >=1 && x <= dimX) && (y >= 1 && y <= dimY);
    }

    //to identify adjacent cell is empty
    public boolean isEmpty(int x, int y){
        return organismWorld[dimY - y][x - 1] == null;
    }

    //to insert organism on a cell
    public void insertOrganism(Organism insect, int x, int y){
        organismWorld[dimY-y][x-1] = insect;
    }

    //to read the position of the organism in the grid cell layout
    public Organism getOrganism(int x, int y){return organismWorld[dimY-y][x-1];}

    //to remove the organism from the grid cell layout
    public void deleteOrganism(int x, int y){
        organismWorld[dimY-y][x-1] = null;
    }

    //to update a grid cell
    public void updateCell(Organism insect){
        insertOrganism(insect, insect.getX(), insect.getY());
        window.fill(insect, dimY - insect.getY(), insect.getX() - 1);
    }

    //to clear a grid cell
    public void deleteCell(Organism insect){
        deleteOrganism(insect.getX(), insect.getY());
        window.fill(null, dimY - insect.getY(), insect.getX() - 1);
    }

    //Person in charge : Muhammad Afham, Mohamad Amirul Ariff

    //to perform the behaviour of the bug to move in grid world
    public void moveAllBug(){
        LinkedList<Ant> toRemoveAnts = new LinkedList<>();
        for(Bug bug : bugList) {
            //left
            if (isInsideWorld(bug.getX() - 1, bug.getY()) && isAnt(bug.getX() - 1, bug.getY())) {
                int direction = 0;
                Organism removedAnt = getOrganism(bug.getX() - 1, bug.getY());
                toRemoveAnts.add((Ant)removedAnt);
                deleteCell(bug);
                bug.move(direction);
                updateCell(bug);
                bug.setTimeStep(bug.getTimeStep() + 1);
                bug.setHasEaten(true);
            }

            //up
            else if (isInsideWorld(bug.getX(), bug.getY() + 1) && isAnt(bug.getX(), bug.getY() + 1)) {
                int direction = 1;
                Organism removedAnt = getOrganism(bug.getX(), bug.getY()+1);
                toRemoveAnts.add((Ant)removedAnt);
                deleteCell(bug);
                bug.move(direction);
                updateCell(bug);
                bug.setTimeStep(bug.getTimeStep() + 1);
                bug.setHasEaten(true);
            }

            //right
            else if (isInsideWorld(bug.getX() + 1, bug.getY()) && isAnt(bug.getX() + 1, bug.getY())) {
                int direction = 2;
                Organism removedAnt = getOrganism(bug.getX()+1, bug.getY());
                toRemoveAnts.add((Ant)removedAnt);
                deleteCell(bug);
                bug.move(direction);
                updateCell(bug);
                bug.setTimeStep(bug.getTimeStep() + 1);
                bug.setHasEaten(true);
            }

            //down
            else if (isInsideWorld(bug.getX(), bug.getY() - 1) && isAnt(bug.getX(), bug.getY() - 1)) {
                int direction = 3;
                Organism removedAnt = getOrganism(bug.getX(), bug.getY()-1);
                toRemoveAnts.add((Ant)removedAnt);
                deleteCell(bug);
                bug.move(direction);
                updateCell(bug);
                bug.setTimeStep(bug.getTimeStep() + 1);
                bug.setHasEaten(true);
            }

            if(!bug.getHasEaten()){
                int direction = randomNum.nextInt(4);
                int newX = 0;
                int newY = 0;
                if (direction == 1 || direction == 3) {
                    newX = bug.getX();
                    newY = getNewPosY(direction, bug);
                }
                else if (direction == 0 || direction == 2) {
                    newX = getNewPosX(direction, bug);
                    newY = bug.getY();
                }

                if (isInsideWorld(newX, newY) && isEmpty(newX, newY)) {
                    deleteCell(bug);
                    bug.move(direction);
                    updateCell(bug);
                    bug.setTimeStep(bug.getTimeStep() + 1);
                }
            }
            else{
                if(!toRemoveAnts.isEmpty())
                    antList.removeAll(toRemoveAnts);
            }
        }
    }

    //Person in charge : Muhammad Afham

    //to perform the behaviour of the ant to move in grid world
    public void moveAllAnt(){
        for (Ant ant : antList) {
            int direction = randomNum.nextInt(4);
            int newX = 0;
            int newY = 0;
            if (direction == 1 || direction == 3) {
                newX = ant.getX();
                newY = getNewPosY(direction, ant);
            } else {
                newX = getNewPosX(direction, ant);
                newY = ant.getY();
            }

            if (isInsideWorld(newX, newY) && isEmpty(newX, newY)) {
                deleteCell(ant);
                ant.move(direction);
                updateCell(ant);
                ant.setTimeStep(ant.getTimeStep() + 1);
            }
        }
    }

    //Person in charge : Muhammad Afham, Fatin Nabilah, Izzah Nurhana

    //to perform the behaviour of the bug to breed in grid world
    public void bugBreed(){
        LinkedList<Bug> extraBugList = new LinkedList<>();
        for(Bug bug : bugList) {
            if(bug.getTimeStep() == Bug.getBreedThreshold()) {
                Bug child;
                //left
                if (isInsideWorld(bug.getX() - 1, bug.getY()) && isEmpty(bug.getX() - 1, bug.getY())) {
                    child = new Bug();
                    child.breed(bug.getX() - 1, bug.getY());
                    updateCell(child);
                    extraBugList.add(child);
                }

                //up
                else if (isInsideWorld(bug.getX(), bug.getY() + 1) && isEmpty(bug.getX(), bug.getY() + 1)) {
                    child = new Bug();
                    child.breed(bug.getX(), bug.getY() + 1);
                    updateCell(child);
                    extraBugList.add(child);
                }

                //right
                else if (isInsideWorld(bug.getX() + 1, bug.getY()) && isEmpty(bug.getX() + 1, bug.getY())) {
                    child = new Bug();
                    child.breed(bug.getX() + 1, bug.getY());
                    updateCell(child);
                    extraBugList.add(child);
                }

                //down
                else if (isInsideWorld(bug.getX(), bug.getY() - 1) && isEmpty(bug.getX(), bug.getY() - 1)) {
                    child = new Bug();
                    child.breed(bug.getX(), bug.getY() - 1);
                    updateCell(child);
                    extraBugList.add(child);
                }
                bug.setTimeStep(0); //reset
            }
        }
        if(!extraBugList.isEmpty())
            bugList.addAll(extraBugList);
    }

    //Person in charge : Muhammad Afham, Fatin Nabilah, Izzah Nurhana

    //to perform the behaviour of the ant to breed in grid world
    public void antBreed(){
            LinkedList<Ant> extraAntList = new LinkedList<>();
            for(Ant ant : antList) {
                if(ant.getTimeStep() == Ant.getBreedThreshold()) {
                    //left
                    Ant child;
                    if (isInsideWorld(ant.getX() - 1, ant.getY()) && isEmpty(ant.getX() - 1, ant.getY())) {
                        child = new Ant();
                        child.breed(ant.getX() - 1, ant.getY());
                        updateCell(child);
                        extraAntList.add(child);
                    }
                    //up
                    else if (isInsideWorld(ant.getX(), ant.getY() + 1) && isEmpty(ant.getX(), ant.getY() + 1)) {
                        child = new Ant();
                        child.breed(ant.getX(), ant.getY() + 1);
                        updateCell(child);
                        extraAntList.add(child);
                    }

                    //right
                    else if (isInsideWorld(ant.getX() + 1, ant.getY()) && isEmpty(ant.getX() + 1, ant.getY())) {
                        child = new Ant();
                        child.breed(ant.getX() + 1, ant.getY());
                        updateCell(child);
                        extraAntList.add(child);
                }

                //down
                else if (isInsideWorld(ant.getX(), ant.getY() - 1) && isEmpty(ant.getX(), ant.getY() - 1)) {
                    child = new Ant();
                    child.breed(ant.getX(), ant.getY() - 1);
                    updateCell(child);
                    extraAntList.add(child);
                }
                ant.setTimeStep(0); //reset
            }

        }
        if(!extraAntList.isEmpty())
            antList.addAll(extraAntList);
    }

    //Person in charge : Izzah Nurhana

    //to perform the behaviour of the bug starve in grid world
    public void bugStarve(){
        LinkedList<Bug> toRemoveBug = new LinkedList<>();
        for(Bug bug : bugList){
            if(bug.getTimeStep() == Bug.getStarveThreshold() && !bug.getHasEaten()){
                deleteCell(bug);
                toRemoveBug.add(bug);
                System.out.println("bugStarve");
            }
            else
                bug.setHasEaten(false);
        }
        bugList.removeAll(toRemoveBug);
    }

    //to read the new position for the organism (horizontal)
    public int getNewPosX(int direction, Organism org){
        if(direction == 2) {
            return org.getX()+1;
        } else if(direction == 0)
            return org.getX()-1;
        else
            return -1;
    }

    //to read the new position for the organism (vertical)
    public int getNewPosY(int direction, Organism org){
        if(direction == 1) {
            return org.getY()+1;
        } else if(direction == 3)
            return org.getY()-1;
        else
            return -1;
    }

    //to implement all the behaviours of Ants and Bugs
    public void startSimulation(){
        //move all bugs
        moveAllBug();
        //move all ants
        moveAllAnt();
        //bugs breed
        bugBreed();
        //ants breed
        antBreed();
        //bugs starve
        bugStarve();

        setNoOfAnt(antList.size());
        setNoOfBug(bugList.size());
    }

    //to initiate the timer and start the simulation
    public void initiateEvent() {
        timer = new Timer(50, evt -> {
            startSimulation();
            timer.stop();
            window.updateGUI();
        });
    }

    //to read the number of ants in the grid world
    public static int getNoOfAnt(){return noOfAnt;}

    //to updates the number of ants in the grid world
    public static void setNoOfAnt(int numAnt){noOfAnt = numAnt;}

    //to set the number of bugs in the grid world
    public static void setNoOfBug(int numBug){noOfBug = numBug;}

    //to get the number of bugs in the grid world
    public static int getNoOfBug(){return noOfBug;}

    @Override
    //to allow the enter button to start the simulation
    public void keyTyped(KeyEvent e) {
        if(e.getKeyChar() == KeyEvent.VK_ENTER){
            AntsBugsWorld.getTimer().start();
            System.out.println("entered");
        }
    }

    @Override
    //function override the keylistener
    public void keyPressed(KeyEvent e) {

    }

    @Override
    //function override the keylistener
    public void keyReleased(KeyEvent e) {

    }

}
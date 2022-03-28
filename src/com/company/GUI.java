package com.company;

import javax.swing.*;
import java.awt.*;

//Person in charge : Muhammad Afham, Mohamad Amirul Ariff

//GUI class acts as the View from the MVC design pattern
//GUI class that visualizes the grid of 20 x 20 cells to locate the ants and bugs
public class GUI extends JFrame {
    private JLabel[][] Mapper;
    private static int dimX;
    private static int dimY;
    private Icon antIcon = new ImageIcon("ant.gif");
    private Icon bugIcon = new ImageIcon("bug3.gif");
    private Icon emptyIcon = new ImageIcon("blank2.png");
    private JLabel numAnt = new JLabel("No. of ant: "+AntsBugsWorld.getNoOfAnt());
    private JLabel numBug = new JLabel("No. of bug: "+AntsBugsWorld.getNoOfBug());

    public GUI(int dimX, int dimY){
        GUI.dimX = dimX;
        GUI.dimY = dimY;
        createMapper();
    }

    //Arrangement for the grid cell layout and the components in the simulation
    public void createMapper(){
        Mapper = new JLabel[gedY()][gedX()];
        JPanel gameBoard = new JPanel(new GridLayout(20,20));
        JPanel bottomBoard = new JPanel(new GridLayout(1,3));
        JPanel topBoard = new JPanel(new GridLayout(1,3));
        JButton start = new JButton("Start");
        JLabel prompt = new JLabel("Press enter to continue");

        setBackground(Color.white);
        setSize(400,400);
        setLayout(new BorderLayout());

        for(int i = 0; i< gedY(); i++){
            for(int j = 0; j< gedX(); j++) {
                Mapper[i][j] = new JLabel(emptyIcon);
                Mapper[i][j].setSize(20,20);
                Mapper[i][j].setBorder(BorderFactory.createLineBorder(Color.GREEN));
                gameBoard.add(Mapper[i][j]);
            }
        }

        start.addActionListener(e -> start.setEnabled(true));
        topBoard.add(start);
        bottomBoard.add(prompt);
        bottomBoard.add(numAnt);
        bottomBoard.add(numBug);

        getContentPane().add(gameBoard, BorderLayout.CENTER);
        getContentPane().add(bottomBoard, BorderLayout.SOUTH);
        pack();
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static int gedX() {return dimX;}

    public static int gedY() {return dimY;}

    //To fill the grid cell layout for the organism icon
    public void fill(Organism Insect, int row, int col) {
        if (Insect instanceof Ant) {
            Mapper[row][col].setIcon(antIcon);
        } else if (Insect instanceof Bug){
            Mapper[row][col].setIcon(bugIcon);
        }
        else
            Mapper[row][col].setIcon(emptyIcon);
    }

    //Update for the number of organism after each time step
    public void updateGUI(){
        numAnt.setText("No. of ant: "+AntsBugsWorld.getNoOfAnt());
        numBug.setText("No. of bug: "+AntsBugsWorld.getNoOfBug());
    }

}

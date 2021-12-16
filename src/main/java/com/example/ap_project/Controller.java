package com.example.ap_project;

import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

import java.util.HashMap;

public class Controller {

    private double x;
    private double y;

    @FXML
    private AnchorPane Pane;

    @FXML
    private Circle Player1;

    @FXML
    private Circle Player2;

    @FXML
    private ImageView board;

    @FXML
    private Label Number;

    private double currentX;
    private double currentY;
    private double boardWidth;
    private double boardHeight;
    private double boxHeight;
    private int currentPlayer1 = 0;
    private int currentPlayer2 = 0;

    private boolean lr = true; //true means right

    private HashMap<Integer, Integer> snakes = new HashMap<>();
    private HashMap<Integer, Integer> ladder = new HashMap<>();

    private double cell_1_X;
    private double cell_1_Y;

    private int[][] cellCoordinates = new int[10][10];

    public void roll(ActionEvent e) throws InterruptedException {
        populateSnakesAndLadders();
        System.out.println("x ki value initially is"+ x);
        System.out.println("x ki value initially is"+ y);

        findDimensions();

        int rn = (int)(Math.random() * 6.0D) + 1;
        Number.setText(String.valueOf(rn));
        if(currentPlayer1+rn>100 || (currentPlayer1==0 && rn!=6)){
            return;
        }
        if(currentPlayer1==0){
            move();
            return;
        }
//        System.out.println(rn);
        for ( int i = 0 ; i < rn ; i++){
            move();
        }
//        Player1.setCenterX(this.x += (double)(boxHeight * rn));

    }

    public void findDimensions(){
        boardWidth = board.getLayoutBounds().getWidth();
        boardHeight = board.getLayoutBounds().getHeight();
        boxHeight = boardHeight / 10.0;
        System.out.println(boardWidth);
        System.out.println(boardHeight);
    }

    public void move() throws InterruptedException {
        if(currentPlayer1==0){
            currentPlayer1++;
            return;
        }

        //if ladder is encountered
        if(ladder.containsKey(currentPlayer1)){

        }

        TranslateTransition trans = new TranslateTransition();
        trans.setNode(Player1);
        trans.setDuration(Duration.millis(1000));

        if ( currentPlayer1 % 10 == 0 ){
            trans.setToY(this.y-boxHeight);
            this.y = this.y-boxHeight;
            //Player1.setCenterY(this.y -= boxHeight);
            lr = !lr;
        }
        else{
            if (!lr){
                trans.setToX(this.x-boxHeight);
                this.x = this.x-boxHeight;
               // Player1.setCenterX(this.x -= boxHeight);
            }
            else{
                trans.setToX(this.x+boxHeight);
                this.x = this.x+boxHeight;
                //Player1.setCenterX(this.x += boxHeight);
            }
        }
        trans.play();
        currentPlayer1++;
    }

    public void populateSnakesAndLadders(){
        snakes.put(43,17);
        snakes.put(50,5);
        snakes.put(56,8);
        snakes.put(73,15);
        snakes.put(84,58);
        snakes.put(98,40);

        ladder.put(2,23);
        ladder.put(6,45);
        ladder.put(20,59);
        ladder.put(52,72);
        ladder.put(57,96);
        ladder.put(71,92);

    }
//    public void findPath(int start,int end){
//        int vertical;
//        int r1 = start / 10;
//        int r2 = end / 10;
//
//        vertical = r1>r2 ? r1-r2 : r2-r1;
//        System.out.println("ladder encountered, row distance is "+vertical);
//    }


}

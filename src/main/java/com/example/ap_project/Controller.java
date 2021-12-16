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
//        //if ladder is encountered
//        if(ladder.containsKey(currentPlayer1)){
//            System.out.println("fuckkkk! a ladder");
//            pair currposition = getCellcoordinates(currentPlayer1);
//            pair ladderends = getCellcoordinates(ladder.get(currentPlayer1));
//            translateBtwpoints(currposition,ladderends);
//        }
//        Player1.setCenterX(this.x += (double)(boxHeight * rn));

    }

    public void findDimensions(){
        boardWidth = board.getLayoutBounds().getWidth();
        boardHeight = board.getLayoutBounds().getHeight();
        boxHeight = boardHeight / 10.0;
//        System.out.println(boardWidth);
//        System.out.println(boardHeight);
    }

    public void move() throws InterruptedException {
        if(currentPlayer1==0){
            currentPlayer1++;
            return;
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

    public void test(ActionEvent e){
        TranslateTransition trans = new TranslateTransition();
        trans.setNode(Player2);
        trans.setByX(board.getLayoutX());
//        System.out.println(board.getLayoutX());
//        System.out.println(board.getLayoutY());
        trans.setByY(board.getLayoutY());
        trans.setDuration(Duration.millis(1000));
        trans.play();
    }

    public pair getCellcoordinates(int cellNo){ //will give coordinates of a cell, w.r.t pane
        int row,col;
        double boardLayoutX = board.getLayoutX();
        double boardLayoutY = board.getLayoutY();
        double bottomleftX = boardLayoutX + boxHeight/2;
        double bottomleftY = boardLayoutY + boardHeight - boxHeight/2;

        if(cellNo % 10 !=0){
            //not last cell
            if((cellNo / 10)%2==0){
                //left to right
                row = cellNo/10;
                col = cellNo%10;
            }
            else{
                //right to left
                row = cellNo/10;
                col = 10 - cellNo%10;
            }
        }

        else{
            //is last cell
            if((cellNo / 10)%2 != 0){
                //left to right
                row = cellNo/10 - 1;
                col = 10;
            }
            else{
                //right to left
                row = cellNo/10 -1;
                col = 0;
            }
        }
        double x1 = bottomleftX + (col-1)*boxHeight;
        double y1 = bottomleftY - row * boxHeight;
        pair toRet = new pair(x1,y1);

        return  toRet;

    }

//    public void translateBtwpoints(pair a,pair b){
//        Player1.setLayoutX(b.x_cor);
//        Player1.setLayoutY(b.y_cor);
//        System.out.println("climbed");
//    }


}

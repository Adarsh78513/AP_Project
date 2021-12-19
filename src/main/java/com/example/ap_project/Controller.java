package com.example.ap_project;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;

import java.util.HashMap;

public class Controller {

    private double x;//moves along with player
    private double y;

    @FXML
    private AnchorPane Pane;

    @FXML
    private Circle Circle1;

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



    private double cell_1_X;
    private double cell_1_Y;

    checkNull ch;
    Player player1;
    Player Player2_;
    LudoBoard SnakeLadder;

    private int[][] cellCoordinates = new int[10][10];

    public void initialize(){
        System.out.println("Initialising the Controller");
        ch = new checkNull(board);
        player1 = new Player(Circle1);
        SnakeLadder = new LudoBoard(board);
    }

    //TODO: call roll for the correct player


    public void roll(ActionEvent e) throws InterruptedException {
        SnakeLadder.printBoardHeight();

        if(currentPlayer1==0) {
            findDimensions();
        }

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
        //if ladder is encountered
        if(SnakeLadder.ladder.containsKey(currentPlayer1)){
            System.out.println("yeeeeee! a ladder");
            pair currposition = SnakeLadder.getCellCoordinates(currentPlayer1);
            pair ladderends = SnakeLadder.getCellCoordinates(SnakeLadder.ladder.get(currentPlayer1));
            translateBtwpoints(currposition,ladderends);
            currentPlayer1=SnakeLadder.ladder.get(currentPlayer1);
            updatePlayerDirection();
        }

        if(SnakeLadder.snakes.containsKey(currentPlayer1)){
            System.out.println("fuckkkk! a snake");
            pair currposition = SnakeLadder.getCellCoordinates(currentPlayer1);
            pair snakeends = SnakeLadder.getCellCoordinates(SnakeLadder.snakes.get(currentPlayer1));
            translateBtwpoints(currposition,snakeends);
            currentPlayer1=SnakeLadder.snakes.get(currentPlayer1);
            updatePlayerDirection();
            System.out.println("Player 1 at "+ currentPlayer1 + "moving " + lr);
        }
        //Player1.setCenterX(this.x += (double)(boxHeight * rn));

    }
    public void updatePlayerDirection(){
        if(currentPlayer1%10 !=0){
            if((currentPlayer1/10) % 2 ==0 ){
                lr = true;
            }
            else{
                lr = false;
            }
        }
        else{
            if((currentPlayer1/10) % 2 ==0 ){
                lr = false;
            }
            else{
                lr = true;
            }
        }
    }

    private void translateBtwpoints(pair currposition, pair ladderends) {
        Circle1.setLayoutX(ladderends.x_cor);
        Circle1.setLayoutY(ladderends.y_cor);
        this.x = ladderends.x_cor;
        this.y = ladderends.y_cor;
        System.out.println("NEw coords = "+this.x+"  "+this.y);
    }

    public void findDimensions(){
        boardWidth = board.getLayoutBounds().getWidth();
        boardHeight = board.getLayoutBounds().getHeight();
        boxHeight = boardHeight / 10.0;


        pair xy = SnakeLadder.getCellCoordinates(1);
        System.out.println("CELL CORDINATES =  "+xy.x_cor +" "+ xy.y_cor);
        x = xy.x_cor;
        y = xy.y_cor;
//        System.out.println(boardWidth);
//        System.out.println(boardHeight);
    }

    public void move() throws InterruptedException {
        if(currentPlayer1==0){
            currentPlayer1++;
            return;
        }

//        TranslateTransition trans = new TranslateTransition();
//        trans.setNode(Player1);
//        trans.setDuration(Duration.millis(1000));

        if ( currentPlayer1 % 10 == 0 ){
//            trans.setToY(this.y-boxHeight);
//            this.y = this.y-boxHeight;
            Circle1.setLayoutY(this.y -= boxHeight);
            lr = !lr;
        }
        else{
            if (!lr){
//                trans.setToX(this.x-boxHeight);
//                this.x = this.x-boxHeight;
                Circle1.setLayoutX(this.x -= boxHeight);
            }
            else{
//                trans.setToX(this.x+boxHeight);
//                this.x = this.x+boxHeight;
                Circle1.setLayoutX(this.x += boxHeight);
            }
        }
        //trans.play();
        currentPlayer1++;
    }






}

class checkNull{
    ImageView board;

    checkNull(ImageView board){
        this.board = board;
    }

    public void printBoardHeight(){
        System.out.println("The board height is "+board.getLayoutBounds().getHeight());
    }
}

class Player{
    int currentPosition;
    double x;
    double y;
    Circle circle;

    Player(Circle circle){
        this.currentPosition = 0;
        this.x = 0;
        this.y = 0;
        this.circle = circle;
    }

    public void rollDice(){
        int dice = (int)(Math.random() * 6) + 1;
        System.out.println("Dice value is "+dice);
        this.currentPosition += dice;
        System.out.println("Player position is "+this.currentPosition);
    }



}

class LudoBoard{
    double boardHeight;
    double boardWidth;
    double boxHeight;
    double boxWidth;
    ImageView board;
    public HashMap<Integer, Integer> snakes = new HashMap<>();
    public HashMap<Integer, Integer> ladder = new HashMap<>();

    LudoBoard(ImageView board){
        this.board = board;
        boardHeight = this.board.getLayoutBounds().getHeight();
        boardWidth = this.board.getLayoutBounds().getWidth();
        boxHeight = boardHeight/10;
        boxWidth = boardWidth/10;
        populateSnakesAndLadders();
    }

    public void printBoardHeight(){
        System.out.println("The board height is "+boardHeight);
    }

    public void populateSnakesAndLadders(){
        snakes.put(23,5);
        snakes.put(32,9);
        snakes.put(46,25);
        snakes.put(51,11);
        snakes.put(59,40);
        snakes.put(66,56);
        snakes.put(81,62);
        snakes.put(92,48);
        snakes.put(95,54);
        snakes.put(98,65);

        ladder.put(2,21);
        ladder.put(6,27);
        ladder.put(8,33);
        ladder.put(16,34);
        ladder.put(24,64);
        ladder.put(38,58);
        ladder.put(63,82);
        ladder.put(70,91);
        ladder.put(73,94);
        ladder.put(85,97);

    }

    public pair getCellCoordinates(int cellNo){ //will give coordinates of a cell, w.r.t pane
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
                col = 11 - (cellNo%10);
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
                col = 1;
            }
        }
        double x1 = bottomleftX + (col-1)*boxHeight;
        double y1 = bottomleftY - row * boxHeight;
        pair toRet = new pair(x1,y1);

        return  toRet;

    }

}
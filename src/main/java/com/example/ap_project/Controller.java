package com.example.ap_project;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import java.io.File;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.HashMap;

public class Controller {

    @FXML
    private AnchorPane Pane;

    @FXML
    private Circle Circle1;

    @FXML
    private Circle Circle2;

    @FXML
    private ImageView board;

    @FXML
    private ImageView diceRollImage;

    @FXML
    private Button Dice;

    @FXML
    private Label Number;

    @FXML
    private ImageView p1_image;

    @FXML
    private ImageView p2_image;

    static Player player1;
    static Player player2;
    static LudoBoard SnakeLadder;
    static Dice dice;

    static Player currentPlayer;

    static boolean P1turn;

    public void initialize(){
        System.out.println("Initialising the Controller");
        player1 = new Player(Circle1);
        player2 = new Player(Circle2);
        currentPlayer = player1;
        P1turn = true;
        SnakeLadder = new LudoBoard(board);
        dice = new Dice(Number);
        File file = new File("src/main/resources/dice1.png");
        diceRollImage.setImage(new Image(file.toURI().toString()));

        p2_image.setOpacity(0.5);
    }

    //TODO: call roll for the correct player


    public void roll(ActionEvent e) throws InterruptedException {
        int rn = (int)(Math.random() * 6.0D) + 1;



        Dice.setDisable(true);
        Thread thread = new Thread() {
            public void run() {
                try {
                    for (int i = 0; i < 10; i++) {
                        File file = new File("src/main/resources/dice" + ((int) (Math.random() * 6.0D) + 1) + ".png");
//                        diceRollImage.setImage(new javafx.scene.image.Image(file.toURI().toString()));
                        diceRollImage.setImage(new Image(file.toURI().toString()));
                        Thread.sleep(50);
                    }
                    File file = new File("src/main/resources/dice" + rn + ".png");
                    diceRollImage.setImage(new Image(file.toURI().toString()));

                    Dice.setDisable(false);
                } catch (Exception e) {
                    System.out.println("Exception in roll");
                }
            }
        };
        thread.start();

        currentPlayer.roll(diceRollImage, rn);//OVERLOADING
        P1turn = ! P1turn;
        if(P1turn){
            currentPlayer = player1;
            p2_image.setOpacity(0.5);
            p1_image.setOpacity(1);
        }
        else{
            currentPlayer = player2;
            p1_image.setOpacity(0.5);
            p2_image.setOpacity(1);
        }
    }
}

class Player{
    int currentPosition;
    double x;
    double y;
    Circle circle;
    private boolean lr; //true means right
    static int Dice_value;

    Player(Circle circle){
        this.currentPosition = 0;
        this.x = 0;
        this.y = 0;
        this.circle = circle;
        this.lr = true;
    }

    public void rollDice(){
        int dice = (int)(Math.random() * 6) + 1;
        System.out.println("Dice value is "+dice);
        this.currentPosition += dice;
        System.out.println("Player position is "+this.currentPosition);
    }

    public void move() throws InterruptedException {
        if(currentPosition==0){
            currentPosition++;
            return;
        }

//        TranslateTransition trans = new TranslateTransition();
//        trans.setNode(Player1);
//        trans.setDuration(Duration.millis(1000));

        if ( currentPosition % 10 == 0 ){
//            trans.setToY(this.y-boxHeight);
//            this.y = this.y-boxHeight;
            circle.setLayoutY(this.y -= LudoBoard.boxHeight);
            lr = !lr;
        }
        else{
            if (!lr){
//                trans.setToX(this.x-boxHeight);
//                this.x = this.x-boxHeight;
                circle.setLayoutX(this.x -= LudoBoard.boxHeight);
            }
            else{
//                trans.setToX(this.x+boxHeight);
//                this.x = this.x+boxHeight;
                circle.setLayoutX(this.x += LudoBoard.boxHeight);
            }
        }
        //trans.play();
        currentPosition++;
    }

    public void updatePlayerDirection(){
        if(currentPosition%10 !=0){
            if((currentPosition/10) % 2 ==0 ){
                lr = true;
            }
            else{
                lr = false;
            }
        }
        else{
            if((currentPosition/10) % 2 ==0 ){
                lr = false;
            }
            else{
                lr = true;
            }
        }
    }

    public void roll(ImageView diceRollImage, int rn) throws InterruptedException {
//        SnakeLadder.printBoardHeight();

        if(currentPosition==0) {
            Controller.SnakeLadder.findDimensions();
        }


        Controller.dice.showDice(rn);

        Dice_value = rn;

        if(currentPosition+rn>100 || (currentPosition==0 && rn!=6 && rn!=1)){
            return;
        }
        if(currentPosition==0){
            if(Dice_value==1 || Dice_value==6){
                pair xy = Controller.SnakeLadder.getCellCoordinates(1);
                Controller.currentPlayer.circle.setLayoutX(xy.x_cor);
                Controller.currentPlayer.circle.setLayoutY(xy.y_cor);
            }
            move();
            return;
        }
//        System.out.println(rn);
        for ( int i = 0 ; i < rn ; i++){
            move();
        }
        //if ladder is encountered
        if(Controller.SnakeLadder.ladder.containsKey(currentPosition)){
            System.out.println("yeeeeee! a ladder");
            pair currposition = Controller.SnakeLadder.getCellCoordinates(currentPosition);
            pair ladderends = Controller.SnakeLadder.getCellCoordinates(Controller.SnakeLadder.ladder.get(currentPosition));
            translateBtwpoints(currposition,ladderends);
            currentPosition=Controller.SnakeLadder.ladder.get(currentPosition);
            updatePlayerDirection();
        }

        if(Controller.SnakeLadder.snakes.containsKey(currentPosition)){
            System.out.println("fuckkkk! a snake");
            pair currposition = Controller.SnakeLadder.getCellCoordinates(currentPosition);
            pair snakeends = Controller.SnakeLadder.getCellCoordinates(Controller.SnakeLadder.snakes.get(currentPosition));
            translateBtwpoints(currposition,snakeends);
            currentPosition=Controller.SnakeLadder.snakes.get(currentPosition);
            updatePlayerDirection();
            System.out.println("Player 1 at "+ currentPosition + "moving " + lr);
        }
        //Player1.setCenterX(this.x += (double)(boxHeight * rn));

    }


    private void translateBtwpoints(pair currposition, pair ladderends) {
        circle.setLayoutX(ladderends.x_cor);
        circle.setLayoutY(ladderends.y_cor);
        this.x = ladderends.x_cor;
        this.y = ladderends.y_cor;
        System.out.println("NEw coords = "+this.x+"  "+this.y);
    }



}

class LudoBoard{
    double boardHeight;
    double boardWidth;
    static double boxHeight;
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

    public void findDimensions(){
        boardWidth = board.getLayoutBounds().getWidth();
        boardHeight = board.getLayoutBounds().getHeight();
        boxHeight = boardHeight / 10.0;


        pair xy = getCellCoordinates(1);
        System.out.println("CELL CORDINATES =  "+xy.x_cor +" "+ xy.y_cor);


            Controller.currentPlayer.x = xy.x_cor;
            Controller.currentPlayer.y = xy.y_cor;

//        System.out.println(boardWidth);
//        System.out.println(boardHeight);

    }

}


class Dice{
    Label Number;

    Dice(Label Number){
        this.Number = Number;
    }
    public void showDice(int diceNo){
        Number.setText(Integer.toString(diceNo));
    }
}
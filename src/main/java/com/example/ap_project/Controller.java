package com.example.ap_project;

import javafx.animation.TranslateTransition;
import javafx.application.Platform;
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
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.util.HashMap;

public class Controller {
    @FXML
    private Button BUTTON;

    @FXML
    private AnchorPane Pane;

    @FXML
    private ImageView Circle1;

    @FXML
    private ImageView Circle2;

    @FXML
    private ImageView board;

    @FXML
    private ImageView diceRollImage;


    @FXML
    private Label Number;

    @FXML
    private ImageView p1_image;

    @FXML
    private ImageView p2_image;

    @FXML
    private ImageView YellowArrow;

    static Player player1;
    static Player player2;
    static LudoBoard SnakeLadder;
    static Dice dice;

    static Player currentPlayer;

    static boolean P1turn;
    MediaPlayer backgroundMusic;

    public void initialize(){
        TranslateTransition transition = new TranslateTransition();
        transition.setNode(YellowArrow);
        transition.setDuration(Duration.millis(500));
        transition.setCycleCount(TranslateTransition.INDEFINITE);
        transition.setByY(-25);
        transition.setAutoReverse(true);
        transition.play();

        music();
        System.out.println("Initialising the Controller");
        player1 = new Player(Circle1,1);
        player2 = new Player(Circle2,2);
        currentPlayer = player1;

        P1turn = true;
        SnakeLadder = new LudoBoard(board);
        dice = new Dice(Number);
        File file = new File("src/main/resources/dice1.png");
        diceRollImage.setImage(new Image(file.toURI().toString()));

        p2_image.setOpacity(0.5);
    }

    public void music() {
//        Media media = new Media(new File("src/main/resources/LudoKingMusic.mp3").toURI().toString());
//        backgroundMusic = new MediaPlayer(media);
//        backgroundMusic.setVolume(0.1);
//        backgroundMusic.setAutoPlay(true);
        Media media = new Media(new File("src/main/resources/LudoKingMusic.mp3").toURI().toString());
        backgroundMusic = new MediaPlayer(media);
        backgroundMusic.setOnEndOfMedia(new Runnable() {
            public void run() {
                backgroundMusic.seek(Duration.ZERO);
            }
            });
        backgroundMusic.setVolume(0.07);
        backgroundMusic.play();
    }

    //TODO: call roll for the correct player


    public void roll(ActionEvent e) throws InterruptedException {

        if(Controller.player1.shifted==-1){
            TranslateTransition trans1 = new TranslateTransition();
            trans1.setNode(Controller.player1.circle);
            trans1.setDuration(Duration.millis(1000));
            trans1.setToX(Controller.player1.x + 20);
            Controller.player1.x += 20;
            trans1.play();
            Controller.player1.shifted=0;

        }
        if(Controller.player2.shifted==1){
            TranslateTransition trans1 = new TranslateTransition();
            trans1.setNode(Controller.player2.circle);
            trans1.setDuration(Duration.millis(1000));
            trans1.setToX(Controller.player2.x - 20);
            Controller.player2.x -= 20;
            trans1.play();
            Controller.player2.shifted=0;

        }

        int rn = (int)(Math.random() * 6.0D) + 1;

        myth2 th2 = new myth2(p1_image,p2_image,BUTTON,rn,diceRollImage);
        th2.start();

//        BUTTON.setDisable(true);
//        Thread thread = new Thread() {
//            public void run() {
//                try {
//                    for (int i = 0; i < 10; i++) {
//                        File file = new File("src/main/resources/dice" + ((int) (Math.random() * 6.0D) + 1) + ".png");
////                        diceRollImage.setImage(new javafx.scene.image.Image(file.toURI().toString()));
//                        diceRollImage.setImage(new Image(file.toURI().toString()));
//                        Thread.sleep(50);
//                    }
//                    File file = new File("src/main/resources/dice" + rn + ".png");
//                    diceRollImage.setImage(new Image(file.toURI().toString()));
//
//                    BUTTON.setDisable(false);
//                } catch (Exception e) {
//                    System.out.println("Exception in roll");
//                }
//            }
//        };
//        thread.start();

    }
}

class Player{
    int currentPosition;
    int shifted;
    double x;
    double y;
    ImageView circle;
    int playerID;
    private boolean lr; //true means right
    static int Dice_value;

    Player(ImageView circle, int ID){
        this.currentPosition = 0;
        this.x = 0;
        this.y = 0;
        this.circle = circle;
        this.lr = true;
        this.playerID = ID;
        shifted = 0;
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


        TranslateTransition trans = new TranslateTransition();
        trans.setNode(Controller.currentPlayer.circle);
        trans.setDuration(Duration.millis(1000));

        if ( currentPosition % 10 == 0 ){
            trans.setToY(this.y-Controller.SnakeLadder.boxWidth);
            this.y = this.y-Controller.SnakeLadder.boxWidth;
            //circle.setLayoutY(this.y -= LudoBoard.boxHeight);
            lr = !lr;
        }
        else{
            if (!lr){
                trans.setToX(this.x-Controller.SnakeLadder.boxWidth);
                this.x = this.x-Controller.SnakeLadder.boxWidth;
                //circle.setLayoutX(this.x -= LudoBoard.boxHeight);
            }
            else{
                trans.setToX(this.x+Controller.SnakeLadder.boxWidth);
                this.x = this.x+Controller.SnakeLadder.boxWidth;
                //circle.setLayoutX(this.x += LudoBoard.boxHeight);
            }
        }
        trans.play();
        currentPosition++;
    }

    public void updatePlayerDirection(){
        if(this.currentPosition%10 !=0){
            if((this.currentPosition/10) % 2 ==0 ){
                this.lr = true;
            }
            else{
                this.lr = false;
            }
        }
        else{
            if((this.currentPosition/10) % 2 ==0 ){
                this.lr = false;
            }
            else{
                this.lr = true;
            }
        }
    }

    public void roll(ImageView diceRollImage, int rn,Button BUTTON) throws InterruptedException {
//        SnakeLadder.printBoardHeight();

        if(currentPosition==0) {
            Controller.SnakeLadder.findDimensions();
        }


        Controller.dice.showDice(rn);

        Dice_value = rn;

        if(currentPosition+rn>100 || (currentPosition==0 && rn!=1 )){
            return;
        }
        if(currentPosition==0){
            if(Dice_value==1){
                TranslateTransition trans = new TranslateTransition();
                trans.setNode(Controller.currentPlayer.circle);
                trans.setDuration(Duration.millis(1000));


                if(Controller.currentPlayer.playerID == 1){
                    trans.setToX(0.0);
                    trans.setToY(-80.7);
                    Controller.currentPlayer.x = 0.0;
                    Controller.currentPlayer.y = -80.7;
                }
                else if(Controller.currentPlayer.playerID == 2){
                    trans.setToX(-40.35);
                    trans.setToY(-80.7);
                    Controller.currentPlayer.x = -40.35;
                    Controller.currentPlayer.y = -80.7;
                }

                trans.play();
//                Controller.currentPlayer.circle.setLayoutX(xy.x_cor);
//                Controller.currentPlayer.circle.setLayoutY(xy.y_cor);
            }

//            myth th = new myth();
//            th.start();
            move();
            if(Controller.player1.currentPosition == Controller.player2.currentPosition){
                System.out.println(" SAME CELL PE HAIN");
                TranslateTransition trans1 = new TranslateTransition();
                trans1.setNode(Controller.player1.circle);
                trans1.setDuration(Duration.millis(1000));
                trans1.setToX(Controller.player1.x - 20);
                Controller.player1.x -= 20;
                trans1.play();

                Controller.player1.shifted = -1;
                Controller.player2.shifted = 1;

                TranslateTransition trans2 = new TranslateTransition();
                trans2.setNode(Controller.player2.circle);
                trans2.setDuration(Duration.millis(1000));
                trans2.setToX(Controller.player2.x + 20);
                Controller.player2.x += 20;
                trans2.play();
            }
            return;
        }
//        System.out.println(rn);


        BUTTON.setDisable(true);
        for ( int i = 0 ; i < rn ; i++){
//            myth th = new myth();
//            th.start();
            move();
            try {
                Thread.sleep(800);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        if(Controller.player1.currentPosition == Controller.player2.currentPosition){
            System.out.println(" SAME CELL PE HAIN");
            TranslateTransition trans1 = new TranslateTransition();
            trans1.setNode(Controller.player1.circle);
            trans1.setDuration(Duration.millis(1000));
            trans1.setToX(Controller.player1.x - 20);
            Controller.player1.x -= 20;
            trans1.play();

            Controller.player1.shifted = -1;
            Controller.player2.shifted = 1;

            TranslateTransition trans2 = new TranslateTransition();
            trans2.setNode(Controller.player2.circle);

            trans2.setDuration(Duration.millis(1000));
            trans2.setToX(Controller.player2.x + 20);
            Controller.player2.x += 20;
            trans2.play();
        }
        BUTTON.setDisable(false);

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

        TranslateTransition trans = new TranslateTransition();
        trans.setNode(circle);
        trans.setDuration(Duration.millis(1000));
        trans.setToX(ladderends.x_cor);
        trans.setToY(ladderends.y_cor);
        trans.play();
//        circle.setLayoutX(Controller.SnakeLadder.bottomleftX + ladderends.x_cor);
//        circle.setLayoutY(Controller.SnakeLadder.bottomleftY - ladderends.y_cor);
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
    double bottomleftX;
    double bottomleftY;

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

        bottomleftX = boardLayoutX + boxHeight/2;//first cell ke coordinates w.r.t pane
        bottomleftY = boardLayoutY + boardHeight - boxHeight/2;

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
        double x1=0.0,y1=0.0;
        if(Controller.currentPlayer.playerID == 1) {
            x1 = bottomleftX + (col - 1) * boxHeight - bottomleftX;
            y1 = bottomleftY - row * boxHeight - bottomleftY - 80.7;
        }
        else if(Controller.currentPlayer.playerID == 2) {
            x1 = bottomleftX + (col - 1) * boxHeight - bottomleftX - 40.35;
            y1 = bottomleftY - row * boxHeight - bottomleftY - 80.7;
        }

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
        //Number.setText(String.valueOf(diceNo));
    }

    public static void diceRollingAnimation(ImageView diceRollImage){
        for (int i = 0; i < 10; i++) {
            File file = new File("src/main/resources/dice" + ((int) (Math.random() * 6.0D) + 1) + ".png");
            //            diceRollImage.setImage(new javafx.scene.image.Image(file.toURI().toString()));
            diceRollImage.setImage(new Image(file.toURI().toString()));
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}


class myth extends Thread{

    ImageView p1_image;
    ImageView p2_image;
    Button BUTTON;
    int rn;
    ImageView diceRollImage;
    myth(ImageView p1_image,ImageView p2_image,Button BUTTON,int rn,ImageView diceRollImage){
        this.p1_image = p1_image;
        this.p2_image = p2_image;
        this.BUTTON = BUTTON;
        this.rn = rn;
        this.diceRollImage = diceRollImage;
    }

    @Override
    public void run(){
        Platform.runLater(new Runnable3());
        try {
            Controller.currentPlayer.roll(diceRollImage,rn,BUTTON);
        } catch (InterruptedException e) {
            e.printStackTrace();
            System.out.println("\n\n\n\n\n\nYAHA HAI ERROR\n\n\n\n\n\n");
        }

        Platform.runLater(new Runnable2(p1_image,p2_image));
    }
}

class Runnable2 implements Runnable{
    ImageView p1_image;
    ImageView p2_image;

    Runnable2(ImageView p1_image,ImageView p2_image){
        this.p1_image = p1_image;
        this.p2_image = p2_image;
    }
    @Override
    public void run() {
        Controller.P1turn = ! Controller.P1turn;
        if(Controller.P1turn){
            Controller.currentPlayer = Controller.player1;
            p2_image.setOpacity(0.5);
            p1_image.setOpacity(1);
        }
        else{
            Controller.currentPlayer = Controller.player2;
            p1_image.setOpacity(0.5);
            p2_image.setOpacity(1);
        }
    }
}

class Runnable3 implements Runnable{
    @Override
    public void run() {
        System.out.println(Player.Dice_value);
        Controller.dice.showDice(Player.Dice_value);
    }
}

class myth2 extends Thread{
    ImageView p1_image;
    ImageView p2_image;
    Button BUTTON;
    int rn;
    ImageView diceRollImage;

    myth2(ImageView p1_image,ImageView p2_image,Button BUTTON,int rn,ImageView diceRollImage){
        this.p1_image = p1_image;
        this.p2_image = p2_image;
        this.BUTTON = BUTTON;
        this.rn = rn;
        this.diceRollImage = diceRollImage;
    }

    @Override
    public void run() {
        BUTTON.setDisable(true);
        Dice.diceRollingAnimation(diceRollImage);

        File file = new File("src/main/resources/dice" + rn + ".png");
        diceRollImage.setImage(new Image(file.toURI().toString()));

        BUTTON.setDisable(false);

        myth th = new myth(p1_image,p2_image,BUTTON,rn,diceRollImage);
        th.start();
    }
}
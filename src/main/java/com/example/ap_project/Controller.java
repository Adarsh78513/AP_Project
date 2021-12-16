package com.example.ap_project;

import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

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
    private double currentPlayer1 = 0;
    private double currentPlayer2 = 0;

    private boolean lr = true; //true means right





    public void roll(ActionEvent e) throws InterruptedException {
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
        TranslateTransition trans = new TranslateTransition();
        trans.setNode(Player1);
        trans.setDuration(Duration.millis(500));

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


}

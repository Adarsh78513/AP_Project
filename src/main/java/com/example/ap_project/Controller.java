package com.example.ap_project;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;

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
    private double currentPlayer1 = 1;
    private double currentPlayer2 = 1;
    //true means right
    private boolean lr = true;





    public void roll(ActionEvent e) throws InterruptedException {
        findDimensions();

        int rn = (int)(Math.random() * 6.0D) + 1;
        Number.setText(String.valueOf(rn));
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
        if ( currentPlayer1 % 10 == 0 ){
            Player1.setCenterY(this.y -= boxHeight);
            lr = !lr;
        }
        else{
            if (!lr){
                Player1.setCenterX(this.x -= boxHeight);
            }
            else{
                Player1.setCenterX(this.x += boxHeight);
            }
        }
        currentPlayer1++;
    }


}

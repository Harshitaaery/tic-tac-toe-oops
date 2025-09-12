package com.tictactoe;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class TicTacToeFX extends Application {
    private Board board = new Board();
    private Player player1 = new Player("Player 1", 'X');
    private Player player2 = new Player("Player 2", 'O');
    private Player currentPlayer = player1;
    private Button[][] buttons = new Button[3][3];

    @Override
    public void start(Stage stage) {
        GridPane grid = new GridPane();

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                Button btn = new Button(" ");
                btn.setMinSize(80, 80);
                final int row = i, col = j;
                btn.setOnAction(e -> handleMove(row, col, btn));
                buttons[i][j] = btn;
                grid.add(btn, j, i);
            }
        }

        stage.setScene(new Scene(grid, 250, 250));
        stage.setTitle("Tic Tac Toe");
        stage.show();
    }

    private void handleMove(int row, int col, Button btn) {
        if (board.makeMove(row, col, currentPlayer.getSymbol())) {
            btn.setText(String.valueOf(currentPlayer.getSymbol()));
            if (board.isWinner(currentPlayer.getSymbol())) {
                showAlert(currentPlayer.getName() + " wins!");
                resetGame();
            } else if (board.isFull()) {
                showAlert("It's a draw!");
                resetGame();
            } else {
                switchTurn();
            }
        }
    }

    private void switchTurn() {
        currentPlayer = (currentPlayer == player1) ? player2 : player1;
    }

    private void resetGame() {
        board = new Board();
        currentPlayer = player1;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j].setText(" ");
            }
        }
    }

    private void showAlert(String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Game Over");
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

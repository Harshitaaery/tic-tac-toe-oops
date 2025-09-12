package com.tictactoe;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.animation.PauseTransition;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.Optional;

public class TicTacToeFX extends Application {

    private TicTacToe game = new TicTacToe();
    private Button[][] buttons;
    private boolean playerTurn = true;
    private String playerName = "Player";
    private Label turnLabel = new Label();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        // Ask for player name
        TextInputDialog dialog = new TextInputDialog("Player");
        dialog.setTitle("Enter Name");
        dialog.setHeaderText("Welcome to Tic Tac Toe!");
        dialog.setContentText("Please enter your name:");
        Optional<String> result = dialog.showAndWait();
        result.ifPresent(name -> playerName = name);

        // Grid setup
        int SIZE = game.getSize();
        buttons = new Button[SIZE][SIZE];
        GridPane grid = new GridPane();
        grid.setHgap(5);
        grid.setVgap(5);

        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                Button btn = new Button(" ");
                btn.setPrefSize(100, 100);
                btn.setStyle(
                        "-fx-background-color: #B57EDC; " + // Lavender Flower
                        "-fx-font-size: 36px; " +
                        "-fx-font-weight: bold; " +
                        "-fx-border-color: #FFFFFF; " + // white border
                        "-fx-border-width: 3px;"
                );
                int row = i;
                int col = j;
                btn.setOnAction(e -> handlePlayerMove(row, col));
                buttons[i][j] = btn;
                grid.add(btn, j, i);
            }
        }

        // Turn label
        turnLabel.setText(playerName + "'s Turn (X)");
        turnLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #FFFFFF;");

        // Reset button
        Button resetBtn = new Button("Reset Board");
        resetBtn.setStyle(
                "-fx-background-color: #E0218A; " + // Barbie Pink
                "-fx-font-size: 16px; " +
                "-fx-font-weight: bold; " +
                "-fx-text-fill: #FFFFFF;"
        );
        resetBtn.setOnAction(e -> resetBoard());

        HBox bottomBox = new HBox(10, resetBtn);
        bottomBox.setStyle("-fx-alignment: center;");

        VBox root = new VBox(15, turnLabel, grid, bottomBox);
        root.setStyle(
                "-fx-padding: 20; " +
                "-fx-alignment: center; " +
                "-fx-background-color: #E0218A;" // Barbie Pink
        );

        Scene scene = new Scene(root);
        primaryStage.setTitle("Tic Tac Toe");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void handlePlayerMove(int row, int col) {
        if (!playerTurn || !game.isCellEmpty(row, col)) return;

        game.makePlayerMove(row, col);
        buttons[row][col].setText(String.valueOf(game.getPlayerSymbol()));

        if (game.checkWinner(game.getPlayerSymbol())) {
            showAlert(playerName + " wins!");
            return;
        }

        if (game.isBoardFull()) {
            showAlert("It's a Draw!");
            return;
        }

        playerTurn = false;
        turnLabel.setText("Computer's Turn (O)");

        // PauseTransition to ensure UI updates before computer moves
        PauseTransition pause = new PauseTransition(Duration.millis(300));
        pause.setOnFinished(e -> computerMove());
        pause.play();
    }

    private void computerMove() {
        int[] move = game.makeComputerMove();
        buttons[move[0]][move[1]].setText(String.valueOf(game.getComputerSymbol()));

        PauseTransition pause = new PauseTransition(Duration.millis(100));
        pause.setOnFinished(e -> {
            if (game.checkWinner(game.getComputerSymbol())) {
                showAlert("Computer wins!");
                return;
            }

            if (game.isBoardFull()) {
                showAlert("It's a Draw!");
                return;
            }

            playerTurn = true;
            turnLabel.setText(playerName + "'s Turn (X)");
        });
        pause.play();
    }

    private void showAlert(String message) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Game Over");
            alert.setHeaderText(null);
            alert.setContentText(message);
            alert.showAndWait();
            resetBoard();
        });
    }

    private void resetBoard() {
        game.resetBoard();
        int SIZE = game.getSize();
        for (int i = 0; i < SIZE; i++)
            for (int j = 0; j < SIZE; j++)
                buttons[i][j].setText(" ");
        playerTurn = true;
        turnLabel.setText(playerName + "'s Turn (X)");
    }
}

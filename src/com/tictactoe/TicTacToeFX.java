package com.tictactoe;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.Optional;

public class TicTacToeFX extends Application {
    private Button[][] buttons = new Button[3][3];
    private boolean playerXTurn = true;
    private String playerXName;
    private String playerOName;
    private Text turnLabel;

    @Override
    public void start(Stage primaryStage) {
        // Ask for player names
        playerXName = askName("Enter name for Player X:");
        playerOName = askName("Enter name for Player O:");

        turnLabel = new Text(playerXName + "'s Turn (X)");
        turnLabel.setStyle("-fx-font-size: 18px; -fx-fill: black;");

        // Grid for game board
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        // Barbie pink board
        grid.setStyle("-fx-background-color: #FF69B4; -fx-padding: 20; -fx-background-radius: 15;");

        // Create buttons (tiles)
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                Button btn = new Button("");
                btn.setPrefSize(100, 100);
                // Lavender tiles, bold text
                btn.setStyle("-fx-font-size: 28px; -fx-background-color: #B57EDC; -fx-text-fill: black; -fx-font-weight: bold; -fx-background-radius: 10;");
                final int row = i, col = j;
                btn.setOnAction(e -> handleMove(btn, row, col));
                buttons[i][j] = btn;
                grid.add(btn, j, i);
            }
        }

        // Root VBox with light pink background
        VBox root = new VBox(15, turnLabel, grid);
        root.setAlignment(Pos.CENTER);
        root.setStyle("-fx-background-color: #FFB6C1; -fx-padding: 20;");

        Scene scene = new Scene(root, 400, 500);
        primaryStage.setTitle("Tic Tac Toe - Pink & Purple Edition");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private String askName(String prompt) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Player Name");
        dialog.setHeaderText(prompt);
        dialog.setContentText("Name:");
        Optional<String> result = dialog.showAndWait();
        return result.orElse("Player");
    }

    private void handleMove(Button btn, int row, int col) {
        if (!btn.getText().isEmpty()) return; // ignore if already clicked

        String symbol = playerXTurn ? "X" : "O";
        btn.setText(symbol);

        if (checkWinner(symbol)) {
            String winnerName = playerXTurn ? playerXName : playerOName;
            showAlert("Game Over", winnerName + " (" + symbol + ") wins!");
            resetBoard();
            return;
        }

        if (isBoardFull()) {
            showAlert("Game Over", "It's a Draw!");
            resetBoard();
            return;
        }

        // Switch turn
        playerXTurn = !playerXTurn;
        turnLabel.setText((playerXTurn ? playerXName : playerOName) + "'s Turn (" + (playerXTurn ? "X" : "O") + ")");
    }

    private boolean checkWinner(String symbol) {
        // Rows & Columns
        for (int i = 0; i < 3; i++) {
            if (buttons[i][0].getText().equals(symbol) &&
                buttons[i][1].getText().equals(symbol) &&
                buttons[i][2].getText().equals(symbol)) return true;

            if (buttons[0][i].getText().equals(symbol) &&
                buttons[1][i].getText().equals(symbol) &&
                buttons[2][i].getText().equals(symbol)) return true;
        }
        // Diagonals
        if (buttons[0][0].getText().equals(symbol) &&
            buttons[1][1].getText().equals(symbol) &&
            buttons[2][2].getText().equals(symbol)) return true;

        if (buttons[0][2].getText().equals(symbol) &&
            buttons[1][1].getText().equals(symbol) &&
            buttons[2][0].getText().equals(symbol)) return true;

        return false;
    }

    private boolean isBoardFull() {
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                if (buttons[i][j].getText().isEmpty()) return false;
        return true;
    }

    private void resetBoard() {
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                buttons[i][j].setText("");
        playerXTurn = true;
        turnLabel.setText(playerXName + "'s Turn (X)");
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

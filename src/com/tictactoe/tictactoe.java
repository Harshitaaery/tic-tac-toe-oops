package com.tictactoe;

import java.util.Random;

public class TicTacToe {

    private final int SIZE = 3;
    private char[][] board = new char[SIZE][SIZE];
    private final char PLAYER_SYMBOL = 'X';
    private final char COMPUTER_SYMBOL = 'O';
    private Random random = new Random();

    public TicTacToe() {
        resetBoard();
    }

    public int getSize() {
        return SIZE;
    }

    public char getPlayerSymbol() {
        return PLAYER_SYMBOL;
    }

    public char getComputerSymbol() {
        return COMPUTER_SYMBOL;
    }

    public char[][] getBoard() {
        return board;
    }

    public boolean isCellEmpty(int row, int col) {
        return board[row][col] == ' ';
    }

    public boolean makePlayerMove(int row, int col) {
        if (board[row][col] == ' ') {
            board[row][col] = PLAYER_SYMBOL;
            return true;
        }
        return false;
    }

    public int[] makeComputerMove() {
        int[] move = getComputerMove();
        board[move[0]][move[1]] = COMPUTER_SYMBOL;
        return move;
    }

    // Dumb AI: block immediate player win, else random
    private int[] getComputerMove() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (board[i][j] == ' ') {
                    board[i][j] = PLAYER_SYMBOL;
                    if (checkWinner(PLAYER_SYMBOL)) {
                        board[i][j] = ' ';
                        return new int[]{i, j};
                    }
                    board[i][j] = ' ';
                }
            }
        }
        int row, col;
        do {
            row = random.nextInt(SIZE);
            col = random.nextInt(SIZE);
        } while (board[row][col] != ' ');
        return new int[]{row, col};
    }

    public boolean checkWinner(char symbol) {
        for (int i = 0; i < SIZE; i++) {
            if ((board[i][0] == symbol && board[i][1] == symbol && board[i][2] == symbol) ||
                (board[0][i] == symbol && board[1][i] == symbol && board[2][i] == symbol))
                return true;
        }
        if ((board[0][0] == symbol && board[1][1] == symbol && board[2][2] == symbol) ||
            (board[0][2] == symbol && board[1][1] == symbol && board[2][0] == symbol))
            return true;
        return false;
    }

    public boolean isBoardFull() {
        for (int i = 0; i < SIZE; i++)
            for (int j = 0; j < SIZE; j++)
                if (board[i][j] == ' ') return false;
        return true;
    }

    public void resetBoard() {
        for (int i = 0; i < SIZE; i++)
            for (int j = 0; j < SIZE; j++)
                board[i][j] = ' ';
    }
}


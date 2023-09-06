package com.example.tictactoe;

import android.widget.Button;

public class WinLogic {
    public static boolean IsWinner(String[][] board, String symbol)
    {
        for (int i = 0; i < 3; i++)
        {
            if (board[i][0] == symbol && board[i][1]== symbol && board[i][2] == symbol)
            {
                return true;
            }
        }

        for (int j = 0; j < 3; j++)
        {
            if (board[0][j] == symbol && board[1][j] == symbol && board[2][j] == symbol)
            {
                return true;
            }
        }

        if (board[0][0] == symbol && board[1][1] == symbol && board[2][2] == symbol)
        {
            return true;
        }
        if (board[0][2] == symbol && board[1][1] == symbol && board[2][0] == symbol)
        {
            return true;
        }
        return false;
    }
    public static boolean isDraw (int moveCount)
    {
        if (moveCount == 9)
        {
            return true;
        }
        return false;
    }
}

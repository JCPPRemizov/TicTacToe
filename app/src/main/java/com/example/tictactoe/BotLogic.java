package com.example.tictactoe;

import android.widget.Button;

import java.util.Random;

public class BotLogic {
    String emptyCell = "\0";
    public void BotMove(Button[][] buttons, String [][] board, String botSymbol, String playerSymbol)
    {
        //Проверка, что бот в следующем ходе может выиграть
        for (int i = 0; i < 3; i++)
        {
            for (int j = 0; j < 3; j++)
            {
                if (board[i][j] == emptyCell)
                {
                    board[i][j] = botSymbol;
                    if (WinLogic.IsWinner(board, botSymbol))
                    {
                        buttons[i][j].setText(botSymbol);
                        return;
                    }
                    else
                    {
                        board[i][j] = emptyCell;
                    }
                }
            }
        }
        //Проверка,что игрок в следующем ходе может выиграть.
        for (int i = 0; i < 3; i++)
        {
            for (int j = 0; j < 3; j++)
            {
                if (board[i][j] == emptyCell)
                {
                    board[i][j] = playerSymbol;
                    if (WinLogic.IsWinner(board, playerSymbol))
                    {
                        buttons[i][j].setText(botSymbol);
                        return;
                    }
                    else
                    {
                        board[i][j] = emptyCell;
                    }
                }
            }
        }
        //Рандомный ход
        Random rnd = new Random();
        int row, col;
        do
        {
            row = rnd.nextInt(3);
            col = rnd.nextInt(3);
        } while (board[row][col] != emptyCell);

        buttons[row][col].setText(botSymbol);
    }


}

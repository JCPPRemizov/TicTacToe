package com.example.tictactoe;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static String [][] board = new String[3][3];
    private static Button [][] buttons = new Button[3][3];
    private BotLogic bot = new BotLogic();
    private int moveCount = 0;
    private boolean isPlayerTurn = true;

    private Switch themeSwitcher;
    private TextView winsView, drawView, defeatView;
    private String playerSymbol = "X";
    private String  botSymbol = "O";

    private int scoreWin = 0, scoreDraw = 0, scoreDefeat = 0;

    private SharedPreferences themeSettings, statsSettings;
    private SharedPreferences.Editor settingsEditor, statsEditor;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        themeSettings = getSharedPreferences("SETTINGS", MODE_PRIVATE);
        statsSettings = getSharedPreferences("STATSETTINGS", MODE_PRIVATE);

        if (!themeSettings.contains("MODE_NIGHT_ON")){
            settingsEditor = themeSettings.edit();
            settingsEditor.putBoolean("MODE_NIGHT_ON", false);
            settingsEditor.apply();
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        } else {
            setCurrentTheme();
        }

        if (!statsSettings.contains("WINS") && !statsSettings.contains("DRAWS") && !statsSettings.contains("DEFEATS")){
            statsEditor = statsSettings.edit();
            statsEditor.putInt("WINS", 0);
            statsEditor.putInt("DRAWS", 0);
            statsEditor.putInt("DEFEATS", 0);
            statsEditor.apply();
        } else {

            loadScore();
        }

        setContentView(R.layout.activity_main);

        themeSwitcher = findViewById(R.id.themeSwitch);
        winsView = findViewById(R.id.winsView);
        drawView = findViewById(R.id.drawView);
        defeatView = findViewById(R.id.defeatView);

        themeSwitcher.setOnClickListener(view -> {
            settingsEditor = themeSettings.edit();
            if (!themeSettings.getBoolean("MODE_NIGHT_ON", false)){
                settingsEditor.putBoolean("MODE_NIGHT_ON", true);

            } else {
                settingsEditor.putBoolean("MODE_NIGHT_ON", false);

            }
            settingsEditor.apply();
            setCurrentTheme();
        });


        buttons[0][0] = findViewById(R.id.button1);
        buttons[0][1] = findViewById(R.id.button2);
        buttons[0][2] = findViewById(R.id.button3);
        buttons[1][0] = findViewById(R.id.button4);
        buttons[1][1] = findViewById(R.id.button5);
        buttons[1][2] = findViewById(R.id.button6);
        buttons[2][0] = findViewById(R.id.button7);
        buttons[2][1] = findViewById(R.id.button8);
        buttons[2][2] = findViewById(R.id.button9);

        for (int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++){
                buttons[i][j].setText("\0");
                buttons[i][j].setOnClickListener(this);
            }
        }

        updateScore();
        updateSwitch();
    }

    private void setCurrentTheme(){
        settingsEditor = themeSettings.edit();
        if (themeSettings.getBoolean("MODE_NIGHT_ON", false)){
            getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
        settingsEditor.apply();
    }


    private void updateSwitch(){
        if (themeSettings.getBoolean("MODE_NIGHT_ON", false)){
            themeSwitcher.setChecked(true);
        } else {
            themeSwitcher.setChecked(false);
        }
    }

    private void loadScore(){
        if (statsSettings.contains("WINS")){
            scoreWin = statsSettings.getInt("WINS", 0);
        }
        if (statsSettings.contains("DRAWS")){
            scoreDraw = statsSettings.getInt("DRAWS", 0);
        }
        if (statsSettings.contains("DEFEATS")){
            scoreDefeat = statsSettings.getInt("DEFEATS", 0);
        }
    }

    private void updateScore(){
        statsEditor = statsSettings.edit();
        statsEditor.putInt("WINS", scoreWin);
        statsEditor.putInt("DRAWS", scoreDraw);
        statsEditor.putInt("DEFEATS", scoreDefeat);
        statsEditor.apply();
        winsView.setText(Integer.toString(scoreWin));
        drawView.setText(Integer.toString(scoreDraw));
        defeatView.setText(Integer.toString(scoreDefeat));

    }

    private void updateBoard(){
        for (int i = 0; i < 3; i++){
            for (int j = 0; j < 3; j++){
                board[i][j] = buttons[i][j].getText().toString();
            }
        }
    }

    private void newGame(){
        isPlayerTurn = true;
        moveCount = 0;
        for (int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++){
                buttons[i][j].setText("\0");
            }
        }
        updateBoard();
    }

    @Override
    public void onClick(View view) {
        Button button = (Button) view;
        if (isPlayerTurn){
            if (button.getText() == playerSymbol || button.getText() == botSymbol){
                return;
            }
            moveCount++;
            isPlayerTurn = false;
            button.setText(playerSymbol);
            updateBoard();
           if (WinLogic.IsWinner(board, playerSymbol)){
               Toast.makeText(MainActivity.this, "Вы выиграли!", Toast.LENGTH_SHORT).show();
               scoreWin++;
               updateScore();
               newGame();
           }
           else if (WinLogic.isDraw(moveCount)){
               Toast.makeText(MainActivity.this, "Ничья!", Toast.LENGTH_SHORT).show();
               scoreDraw++;
               drawView.setText(Integer.toString(scoreDraw));
               updateScore();
               newGame();
           }
           else if (!isPlayerTurn)
           {
               bot.BotMove(buttons, board, botSymbol, playerSymbol);
               updateBoard();
               isPlayerTurn = true;
               moveCount++;
               if (WinLogic.IsWinner(board, botSymbol))
               {
                   Toast.makeText(MainActivity.this, "Вы проиграли!", Toast.LENGTH_SHORT).show();
                   scoreDefeat++;
                   defeatView.setText(Integer.toString(scoreDefeat));
                   updateScore();
                   newGame();
               }
           }

        }
    }


}
package com.example.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    TextView mplayer1TextView;
    TextView mplayer2TextView;
    Button mreset;
    int Player1Points;
    int Player2Points;
    private int roundCount;
    boolean Player1Turn;
    private Button[][] buttons = new Button[3][3];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mplayer1TextView = findViewById(R.id.player1textView);
        mplayer2TextView = findViewById(R.id.player2textView);
        mreset = findViewById(R.id.resetbtn);
        Player1Points = 0;
        Player2Points = 0;
        Player1Turn = true;

        for (int i=0;i<3;i++)
        {
            for (int j=0;j<3;j++)
            {
                String buttonID = "button_"+i+j;
                int resID = getResources().getIdentifier(buttonID, "id", getPackageName());
                buttons[i][j] = findViewById(resID);
                buttons[i][j].setOnClickListener(this);
            }
        }

        mplayer1TextView.setText("Player 1:" + Player1Points);
        mplayer2TextView.setText("Player 2:" + Player2Points);

        mreset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetGame();
            }
        });


    }

    @Override
    public void onClick(View v) {
        if (!((Button) v).getText().toString().equals(""))
        {
            return;
        }
        else
            if (Player1Turn)
            {
                ((Button)v).setText("X");
            }
            else
            {
                ((Button)v).setText("0");
            }

        roundCount++;

            if (CheckForWin())
            {
                if (Player1Turn)
                    player1Win();
                else
                    player2Win();
            }else if (roundCount == 9) {
                draw();
            }else{
                Player1Turn =  !Player1Turn;
            }
    }

    private boolean CheckForWin()
    {
        String [][] field = new String[3][3];
        int i,j;
        for (i=0;i<3;i++){
            for(j=0;j<3;j++){
                field[i][j] = buttons[i][j].getText().toString();
            }
        }

        for(i=0;i<3;i++){
            if (field[i][0].equals(field[i][1]) && field[i][0].equals(field[i][2])
            && !field[i][0].equals("")){
                return true;
            }
        }

        for ( i = 0; i < 3; i++) {
            if (field[0][i].equals(field[1][i])
                    && field[0][i].equals(field[2][i])
                    && !field[0][i].equals("")) {
                return true;
            }
        }

        if (field[0][0].equals(field[1][1])
                && field[0][0].equals(field[2][2])
                && !field[0][0].equals("")) {
            return true;
        }

        if (field[0][2].equals(field[1][1])
                && field[0][2].equals(field[2][0])
                && !field[0][2].equals("")) {
            return true;
        }

        return false;
    }

    private void player1Win()
    {
        Player1Points++;
        Toast.makeText(this, "Player 1 wins!", Toast.LENGTH_SHORT).show();
        updatePointsText();
        resetBoard();
    }

    private void player2Win()
    {
        Player2Points++;
        Toast.makeText(this, "Player 2 wins!", Toast.LENGTH_SHORT).show();
        updatePointsText();
        resetBoard();
    }
    private void draw() {
        Toast.makeText(this, "Draw!", Toast.LENGTH_SHORT).show();
        resetBoard();
    }

    private void updatePointsText()
    {
        mplayer1TextView.setText("Player 1:"+Player1Points);
        mplayer2TextView.setText("Player 2:"+Player2Points);
    }

    private void resetBoard()
    {
        for (int i=0;i<3;i++)
        {
            for(int j=0;j<3;j++){
                buttons[i][j].setText("");
            }
        }

        roundCount = 0;
        Player1Turn = true;
    }

    private void resetGame() {
        Player1Points = 0;
        Player2Points = 0;
        updatePointsText();
        resetBoard();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt("roundCount", roundCount);
        outState.putInt("player1Points", Player1Points);
        outState.putInt("player2Points", Player2Points);
        outState.putBoolean("player1Turn", Player1Turn);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        roundCount = savedInstanceState.getInt("roundCount");
        Player1Points = savedInstanceState.getInt("player1Points");
        Player2Points = savedInstanceState.getInt("player2Points");
        Player1Turn = savedInstanceState.getBoolean("player1Turn");
    }
}

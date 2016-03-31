package org.annachen.tictactoe;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    int player = 1;
    boolean game_is_active = true;

    // 0 means unplayed
    int[] status = {0,0,0,
                    0,0,0,
                    0,0,0};

    int[][] winningPositions = {{0,1,2},{3,4,5},{6,7,8},
                               {0,3,6},{1,4,7},{2,5,8},
                               {0,4,8},{2,4,6}};

    public void pick(View view) {
        ImageView imageView = (ImageView)view;
        int tag = Integer.parseInt(imageView.getTag().toString());

        if(status[tag] == 0 && game_is_active) {

            // change board status
            status[tag] = player;
            imageView.setTranslationY(-500f);

            if (player == 1) {
                imageView.setImageResource(R.drawable.starfish);
            } else {
                imageView.setImageResource(R.drawable.fish);
            }

            for(int[] winningPosition:winningPositions) {
                // check if player has won
                if(status[winningPosition[0]] == player &&
                        status[winningPosition[1]] == player &&
                        status[winningPosition[2]] == player) {
                    //Toast.makeText(getApplicationContext(),player+" has won!",Toast.LENGTH_LONG).show();
                    game_is_active = false;

                    TextView textView = (TextView)findViewById(R.id.winner);
                    if(player == 1) {
                        textView.setText("Starfish has won!");
                    }else {
                        textView.setText("Fish has won!");
                    }

                    LinearLayout linearLayout = (LinearLayout)findViewById(R.id.linearlayout);
                    linearLayout.setVisibility(View.VISIBLE);
                }else {
                    // check if it is a draw
                    boolean game_is_over = true;
                    for(int s:status) {
                        if(s == 0)
                            game_is_over = false;
                    }

                    if(game_is_over) {
                        game_is_active = false;

                        TextView textView = (TextView)findViewById(R.id.winner);
                        textView.setText("It's a draw!");
                        LinearLayout linearLayout = (LinearLayout)findViewById(R.id.linearlayout);
                        linearLayout.setVisibility(View.VISIBLE);

                        break;
                    }
                }
            }
            imageView.animate().translationY(0f).rotationBy(360f).setDuration(700);

            player = (player == 1)?2:1;
        }else {
            imageView.animate().rotationBy(360f).setDuration(500);
        }

    }

    public void play_again(View view) {
        game_is_active = true;
        int player = 1;

        for(int i=0; i<status.length; i++) {
            status[i] = 0;
        }

        GridLayout gridLayout = (GridLayout)findViewById(R.id.gridlayout);
        for(int i=0; i<gridLayout.getChildCount(); i++) {
            // reset ImageView
            ((ImageView)gridLayout.getChildAt(i)).setImageResource(0);
        }

        LinearLayout linearLayout = (LinearLayout)findViewById(R.id.linearlayout);
        linearLayout.setVisibility(View.INVISIBLE);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}

package menu;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import lampkraft.attackp.R;

/**
 * Created by Lampkraft on 2015-10-15.
 */

/**
 * Activity containing the highscore list
 */
public class HighScoreActivity extends Activity implements View.OnClickListener {

    Button btnMainMenu, btnPlayAgain;
    TextView txtCurrentScore, txtLastScore,
            txtCurrentScoreNumber, txtLastScoreNumber;
    SharedPreferences prefs;

    /**
     * Sets up the activity graphics and click listeners
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_highscore);

        Typeface tf = Typeface.createFromAsset(getAssets(), "Titania.ttf");
        prefs = this.getSharedPreferences("myPrefs", Context.MODE_PRIVATE);

        txtCurrentScore = (TextView)findViewById(R.id.txt_currentscore);
        txtLastScore = (TextView)findViewById(R.id.txt_lastscore);
        txtCurrentScoreNumber = (TextView)findViewById(R.id.txt_currentscore_number);
        txtLastScoreNumber = (TextView)findViewById(R.id.txt_lastscore_number);
        btnMainMenu = (Button)findViewById(R.id.btn_mainmenu);
        btnPlayAgain = (Button)findViewById(R.id.btn_again);

        txtCurrentScore.setTypeface(tf);
        txtLastScore.setTypeface(tf);
        txtCurrentScoreNumber.setTypeface(tf);
        txtLastScoreNumber.setTypeface(tf);
        btnMainMenu.setTypeface(tf);
        btnPlayAgain.setTypeface(tf);

        btnMainMenu.setOnClickListener(this);
        btnPlayAgain.setOnClickListener(this);

        if(prefs.getBoolean("newHighscore", false)){
            txtCurrentScore.setText("New Highscore!");
            txtLastScore.setText("Last Highscore");
            txtLastScoreNumber.setText("" + prefs.getInt("lastscore", 0));
            txtCurrentScoreNumber.setText("" + prefs.getInt("highscore", 0));
        } else{
            txtCurrentScore.setText("You've scored");
            txtLastScore.setText("Highest score");
            txtLastScoreNumber.setText("" + prefs.getInt("highscore", 0));
            txtCurrentScoreNumber.setText("" + prefs.getInt("lastscore", 0));
        }



    }

    /**
     * Checks what button is being pressed in the menu
     * @param v the view object to check as button
     */
    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.btn_mainmenu :
                Intent mainmenu = new Intent("com.attackp.MAINMENU");
                startActivity(mainmenu);
                break;
            case R.id.btn_again :
                Intent playagain = new Intent("com.attackp.STARTGAME");
                startActivity(playagain);
                break;
        }
    }

    /**
     * Called when the activity is paused
     * Removes the activity
     */
    @Override
    public void onPause() {
        super.onPause();
        // Remove the activity when its off the screen
        finish();
    }

}

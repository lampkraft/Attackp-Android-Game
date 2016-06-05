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
import android.widget.Toast;

import lampkraft.attackp.R;


/**
 * Created by Lampkraft on 2015-10-15.
 */

/**
 * Activity for the main menu
 */
public class MainMenuActivity extends Activity implements View.OnClickListener {

    TextView txtScore;
    Button btnEnter;
    Button btnSound;
    Toast toast;
    SharedPreferences prefs;

    /**
     * Called when the activity is created.
     * Sets up the main menu and its resources.
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        Typeface tf = Typeface.createFromAsset(getAssets(), "Titania.ttf");

        txtScore = (TextView)findViewById(R.id.txt_score);
        btnEnter = (Button)findViewById(R.id.btn_enter);
        btnSound = (Button)findViewById(R.id.btn_sound);

        btnEnter.setOnClickListener(this);
        btnSound.setOnClickListener(this);

        btnEnter.setTypeface(tf);
        btnSound.setTypeface(tf);

        toast = Toast.makeText(getApplicationContext(), "CLICK", Toast.LENGTH_SHORT);

        prefs = this.getSharedPreferences("myPrefs", Context.MODE_PRIVATE);

        txtScore.setTypeface(tf);
        txtScore.setText("" + prefs.getInt("highscore", 0));

    }

    /**
     * Called when user taps the screen
     * @param v the view object to check as button
     */
    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.btn_enter :
                Intent startGame = new Intent("com.attackp.STARTGAME");
                startActivity(startGame);
                break;
            case R.id.btn_sound :
                break;
        }

    }

    /**
     * Called when the activity is resumed
     * Resumes activity but resets highscore
     */
    @Override
    protected void onResume() {
        super.onResume();
        txtScore.setText("" + prefs.getInt("highscore", 0));
    }
}

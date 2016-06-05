package menu;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import lampkraft.attackp.R;

/**
 * Created by Lampkraft on 2015-10-15.
 */

/**
 * The main activity is created when the app starts
 * Creates an intent to the main menu activity
 */
public class MainActivity extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent startGame = new Intent("com.attackp.MAINMENU");
        startActivity(startGame);
        finish();
    }

}

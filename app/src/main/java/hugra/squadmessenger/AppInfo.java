package hugra.squadmessenger;

import android.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;


public class AppInfo extends AppCompatActivity {
    private final int leftJust = Gravity.START;
    private final int centerJust = Gravity.CENTER;
    private final int rightJust = Gravity.END;
    TextView welcomeText;
    private int gravState;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_info);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(false); // remove the left caret
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(false); // disable the button

            actionBar.setDisplayShowHomeEnabled(false); // remove the icon
        }
    }


}

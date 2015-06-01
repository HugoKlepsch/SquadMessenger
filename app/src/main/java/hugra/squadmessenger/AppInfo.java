package hugra.squadmessenger;

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


    public void changeGravity(View v){
        switch (gravState){
            case leftJust:
                gravState = centerJust;
                welcomeText.setGravity(gravState);
                break;
            case centerJust:
                gravState = rightJust;
                welcomeText.setGravity(gravState);
                break;
            case rightJust:
                gravState = leftJust;
                welcomeText.setGravity(gravState);
                break;
        }
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_info);
        welcomeText = (TextView) findViewById(R.id.appInfoMainTopLabel);
        gravState = welcomeText.getGravity();
    }


}

package hugra.squadmessenger;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;


public class AppInfo extends ActionBarActivity {
    private final int leftJust = Gravity.LEFT;
    private final int centerJust = Gravity.CENTER;
    private final int rightJust = Gravity.RIGHT;
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_app_info, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

package hugra.squadmessenger;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;


public class DisplayMessageActivity extends AppCompatActivity {
    private TextView swapButt;
    private boolean isEnabledText = true;

    public void swapEnabled(View v){
        swapButt.setEnabled(!isEnabledText);
        isEnabledText = !isEnabledText;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_message);

        swapButt = (TextView) findViewById(R.id.displayMessageLabel);
        swapButt.setText(getIntent().getStringExtra(MainActivity.EXTRA_MESSAGE));
//        swapButt.setEnabled(true);
    }
}

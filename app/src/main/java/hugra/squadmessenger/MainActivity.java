package hugra.squadmessenger;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class MainActivity extends ActionBarActivity {
    public final static String EXTRA_MESSAGE = "com.hugra.squadmessenger.MESSAGE";
    private static TextView chatOutput;
    private static EditText userIn;
    private static Button sendButt;

    public void sendMessage(View v){
//        Intent intent = new Intent(this, DisplayMessageActivity.class);
//        EditText editText = (EditText) findViewById(R.id.mainActivity_editText);
//        String message = editText.getText().toString();
//        intent.putExtra(EXTRA_MESSAGE, message);
//        startActivity(intent);
        chatOutput.append("\n" + userIn.getText());
        userIn.setText("");
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        chatOutput = (TextView) findViewById(R.id.ChatOutput);
        userIn = (EditText) findViewById(R.id.mainActivity_userIn);
//        userIn.setOnKeyListener(new View.OnKeyListener() {
//            @Override
//            public boolean onKey(View v, int keyCode, KeyEvent event) {
//                if (event.getAction() == KeyEvent.ACTION_DOWN){ //on press down <Button>
//                    switch (keyCode){
//                        case KeyEvent.KEYCODE_ENTER:
//                            Log.d("userin", "keyevent.keycode_enter");
//                            sendMessage();
//                            return true;
//                    }
//                }
//                return false;
//            }
//        });
        sendButt = (Button) findViewById(R.id.mainActivity_sendButt);
        sendButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage(v);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.appInfo) {
            Intent intent = new Intent(this, AppInfo.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }


}

package hugra.squadmessenger;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;

import java.io.IOException;

import hugra.squadmessenger.client.ClientMain;
import hugra.squadmessenger.sharedPackages.LoginDeets;
import hugra.squadmessenger.sharedPackages.Message;


public class MainActivity extends AppCompatActivity {
    public final static String EXTRA_MESSAGE = "com.hugra.squadmessenger.MESSAGE";
    private static TextView chatOutput;
    private static EditText userIn;
    private static Button sendButt;
    private static ClientMain clientThread;
    private static ScrollView chatOutScroller;

    private static String userName;
    private static String iPAddress;
    private static int port;


    public static void sendMessage(Message message){
//        Intent intent = new Intent(this, DisplayMessageActivity.class);
//        EditText editText = (EditText) findViewById(R.id.mainActivity_editText);
//        String message = editText.getText().toString();
//        intent.putExtra(EXTRA_MESSAGE, message);
//        startActivity(intent);
        chatOutput.append(message.getCredentials().getUserName() + ": " + message.getMessage() +
                "\n");
        userIn.setText("");
        chatOutScroller.fullScroll(ScrollView.FOCUS_DOWN);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.userName = getIntent().getStringExtra("userName");
        this.iPAddress = getIntent().getStringExtra("iPAddress");
        this.port = Integer.parseInt(getIntent().getStringExtra("port"));

        chatOutput = (TextView) findViewById(R.id.mainActivity_ChatOutput);
        userIn = (EditText) findViewById(R.id.mainActivity_userIn);

        sendButt = (Button) findViewById(R.id.mainActivity_sendButt);
//        sendButt.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                sendMessage(v);
//            }
//        });
        try {
            clientThread = new ClientMain(userName, iPAddress, port);
        } catch (IOException e) {
            e.printStackTrace();
        }
        chatOutScroller = (ScrollView) findViewById(R.id.mainActivity_ScrollingTextview);
        chatOutScroller.setSmoothScrollingEnabled(true);



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

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
import sharedPackages.Message;


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


    public static void sendMessage(View v){
        clientThread.enQueueMessage(userIn.getText().toString());
        userIn.setText("");
    }

    public static void recieveMessage(Message message){
        chatOutput.append(message.getCredentials().getUserName() + ": " + message.getMessage() +
                "\n");

        chatOutScroller.fullScroll(ScrollView.FOCUS_DOWN);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Bundle bundle = getIntent().getExtras();
        this.userName = bundle.getString("userName");
        this.iPAddress = bundle.getString("iPAddress");
        this.port = Integer.parseInt(bundle.getString("port"));
//        Log.d("MainActivity creds", "userName = " + userName);
//        Log.d("MainActivity creds", "ip = " + iPAddress);
//        Log.d("MainActivity creds", "port = " + bundle.getString("port"));

        chatOutput = (TextView) findViewById(R.id.mainActivity_ChatOutput);
        userIn = (EditText) findViewById(R.id.mainActivity_userIn);

        sendButt = (Button) findViewById(R.id.mainActivity_sendButt);
        sendButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage(v);
            }
        });
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

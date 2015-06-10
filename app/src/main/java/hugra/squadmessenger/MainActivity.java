package hugra.squadmessenger;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;

import java.io.IOException;
import java.util.Vector;

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

    public static boolean canSpawnCheckMessageThread = true;

    public static Vector<String> users;


    public static void sendMessage(View v){
        clientThread.enQueueMessage(userIn.getText().toString());
        userIn.setText("");
    }

    public static void recieveMessage(Message message){
        canSpawnCheckMessageThread = true;
        chatOutput.append(message.getCredentials().getUserName() + ": " + message.getMessage() +
                "\n");

        chatOutScroller.fullScroll(ScrollView.FOCUS_DOWN);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(false); // remove the left caret
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(false); // disable the button

            actionBar.setDisplayShowHomeEnabled(false); // remove the icon
        }
        this.userName = LoginToServer.userName;
        this.iPAddress = LoginToServer.iPAddress;
        this.port = Integer.parseInt(LoginToServer.port);


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
            clientThread = new ClientMain(userName, iPAddress, port, this);
        } catch (IOException e) {
            e.printStackTrace();
        }
        chatOutScroller = (ScrollView) findViewById(R.id.mainActivity_ScrollingTextview);
        chatOutScroller.setSmoothScrollingEnabled(true);



    }

    @Override
    protected void onResume(){
        super.onResume();
        Log.d("Activity", "onResume");
    }

    @Override
    protected void onStart(){
        super.onStart();
        Log.d("Activity", "onStart");
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

        switch (id){
            case R.id.appInfo:
                Intent intent = new Intent(this, AppInfo.class);
                startActivity(intent);
                break;
            case R.id.ShowUsers:
                startActivity(new Intent(this, ShowUsers.class));
                break;
            case R.id.logOut:
                ClientMain.setAlive(false);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                this.finish();
                System.exit(1);
                break;
        }


        return super.onOptionsItemSelected(item);
    }
}

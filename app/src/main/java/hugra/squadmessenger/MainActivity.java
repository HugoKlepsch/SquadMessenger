package hugra.squadmessenger;

import android.content.Intent;
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
    private static TextView chatOutput; //the variables that hold the UI objects
    private static EditText userIn;
    private static Button sendButt;
    private static ClientMain clientThread;
    private static ScrollView chatOutScroller;

    private static String userName;
    private static String iPAddress; //the information to log into the server
    private static int port;


    public static Vector<String> users;


    public static void sendMessage(View v){ //this method is called when you press send
        clientThread.enQueueMessage(userIn.getText().toString()); //enqueues the message into the
        // messge out queue, it is dequeued and sent several times per second.
        userIn.setText(""); //sets the contents of the entry box to empty string
    }

    public static void recieveMessage(Message message){ //this method is called when we recieve a
    // message and want to add it to the chat output
        chatOutput.append(message.getCredentials().getUserName() + ": " + message.getMessage() +
                "\n");

        chatOutScroller.fullScroll(ScrollView.FOCUS_DOWN); //autoscrolls to the bottom
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) { //this is called when the window opens
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); //opens the layout from the xml file


        android.support.v7.app.ActionBar actionBar = getSupportActionBar(); //the following lines
        // gets the title bar that includes the back/up button, and removes it because it causes
        // the mainActivity to reconnect to the server in stead of keeping it in the background.
        actionBar.setDisplayHomeAsUpEnabled(false); // remove the left caret
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(false); // disable the button

            actionBar.setDisplayShowHomeEnabled(false); // remove the icon
        }

        this.userName = LoginToServer.userName; //get the public variables that hold the login
        // details from the login activity
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
            clientThread = new ClientMain(userName, iPAddress, port, this); //makes a new object
            // of the messages backend. THis object runs in the background and handles
            // interaction with the server. It's modularity helped making porting the app much
            // easier
        } catch (IOException e) {
            e.printStackTrace();
        }
        chatOutScroller = (ScrollView) findViewById(R.id.mainActivity_ScrollingTextview);
        chatOutScroller.setSmoothScrollingEnabled(true); //after getting the UI component, we
        // make sure that it scrolls smoothly



    }

    @Override
    protected void onResume(){ //this runs after the oncreate() method, in part of the android
    // .activity lifecycle, very complicated google it
        super.onResume();
    }

    @Override
    protected void onStart(){ //this runs after the onResume() method, in part of the android
        // .activity lifecycle, very complicated google it
        super.onStart();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) { //if the user wants to open the options menu
    // dropdown
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) { //whem the user selects an item in the
    // options drop down
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        switch (id){
            case R.id.appInfo: //if it is the app info, generate a new intent and launch it
                Intent intent = new Intent(this, AppInfo.class);
                startActivity(intent);
                break;
            case R.id.ShowUsers: //if it is the show Users, generate a new intent and launch it
                startActivity(new Intent(this, ShowUsers.class));
                break;
            case R.id.logOut: //if it is the Logout button, set the variable that hopefully
            // allows the backend to shut down correctly, and waits an amount of time for it to
            // do so
                ClientMain.setAlive(false);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                this.finish(); //attempts to close the window
                System.exit(1); //if it is still open, forcefully crash the chat window
                break;
        }


        return super.onOptionsItemSelected(item);
    }
}

/*
		 Title: LoginToServer.java
		 Programmer: hugo
		 Date of creation: June 1, 2015
		 Description: The class/thread that describes the login activity. In this class there is
		 logic for the login window
 */


package hugra.squadmessenger;

import android.content.Intent;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class LoginToServer extends AppCompatActivity {
    private static EditText userNameField; //initializing variable prototypes for later use
    private static EditText passField; //We currently don't use password protection, but I have
    // the variables here to use if we ever expand.
    private static EditText ipEditText; //EditTexts are UI components.
    private static EditText portEditText;
    public static String userName; //These hold each of the values from the UI components later
    public static String password;
    public static String iPAddress;
    public static String port;
    static ImageView img; //more UI components
    static TextView pingText;
    static Button loginServerButt;
    static Button pingServerButt;
    boolean hasEnteredUname = false; //booleans for the enable button logic
    boolean hasEnteredPW = false;
    boolean hasEnteredIP = false;
    boolean hasEnteredPort = false;
    private static final Pattern IP_ADDRESS_PATTERN //This is a REGEX that searches for a valid
    // IPv4 address. it goes <number 0-255> <"."> <number 0-255> <"."> <number 0-255> <"."> <number 0-255>
            = Pattern.compile(
            "((25[0-5]|2[0-4][0-9]|[0-1][0-9]{2}|[1-9][0-9]|[1-9])\\.(25[0-5]|2[0-4]"
                    + "[0-9]|[0-1][0-9]{2}|[1-9][0-9]|[1-9]|0)\\.(25[0-5]|2[0-4][0-9]|[0-1]"
                    + "[0-9]{2}|[1-9][0-9]|[1-9]|0)\\.(25[0-5]|2[0-4][0-9]|[0-1][0-9]{2}"
                    + "|[1-9][0-9]|[0-9]))"); //This was found online on StackOverflow, but
                    // ported for use on android.
    Matcher ipMatcher;


    @Override
    protected void onCreate(Bundle savedInstanceState) { //This method is called when the login
    // window is opened

        super.onCreate(savedInstanceState); //if the window components already exist in memory,
        // use those
        setContentView(R.layout.activity_login_to_server); //load the layout from the appropriate
        // xml file


        android.support.v7.app.ActionBar actionBar = getSupportActionBar(); //the following lines
        // gets the title bar that includes the back/up button, and removes it because it causes
        // the mainActivity to reconnect to the server in stead of keeping it in the background.
        actionBar.setDisplayHomeAsUpEnabled(false); // remove the left caret
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(false); // disable the button

            actionBar.setDisplayShowHomeEnabled(false); // remove the icon
        }
        userNameField = (EditText) findViewById(R.id.loginUname); //we get the object of the UI
        // component in order to access it's functions such as getText()
        userNameField.addTextChangedListener(new TextWatcher() { //this runs code when the text
        // inside the entry box is changed, useful for the logic of enabling/disabling the login
        // buttons.
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { //this
            // method seems more useful
                if (!(s.toString().equals(""))) { //if it is not empty string
                    hasEnteredUname = true; //set the logic variable
                    updateButtons(); //run the method that evaluates the logic and runs
                    // appropriate commands
                } else {
                    hasEnteredUname = false; //set the logic variable
                    updateButtons(); //run the method that evaluates the logic and runs
                    // appropriate commands
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        ipEditText = (EditText) findViewById(R.id.loginIP); //get the UI object
        final ColorStateList normalColour = ipEditText.getTextColors(); //gets the default colour
        // of the edittext because we need to set it back to this colour, but we don't know what
        // it is

        ipEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                ipMatcher = IP_ADDRESS_PATTERN.matcher(s.toString()); //takes the given ip from the
                // edittext and compares it with the IP_ADDRESS_PATTERN regex.
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (ipMatcher.matches()) { //if it is a valid ip
                    ipEditText.setTextColor(normalColour.getDefaultColor()); //we set the colour
                    // of the text to the value of "normal" that we found earlier
                    hasEnteredIP = true; //set logic variabels
                    updateButtons(); //run logic evaluator
                } else { //if not valid IP
                    ipEditText.setTextColor(getResources().getColor(R.color.badIPColor)); //set
                    // the colour of the IP text to red, which looks invalid
                    hasEnteredIP = false; //set logic variables
                    updateButtons(); //run logic evalutator
                }
            }
        });
        portEditText = (EditText) findViewById(R.id.loginPort); //get UI object
        portEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!(s.toString().equals(""))) { //if it is not empty string
                    hasEnteredPort = true; //set logic variabels
                    updateButtons(); //run logic evaluator
                } else {
                    hasEnteredPort = false; //set logic variabels
                    updateButtons(); //run logic evaluator
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        img = (ImageView) findViewById(R.id.loginPingImg); //this is the object that we put the
        // images into (the ones that display the status of the network
        pingText = (TextView) findViewById(R.id.loginPingText); //the UI object that displays the
        // actual ping value
        loginServerButt  = (Button) findViewById(R.id.loginLoginButt); // the button that calls
        // the login functions
        loginServerButt.setEnabled(false); //we don't want it enabled yet because it is safe to
        // assum that the login info is not correct
        pingServerButt = (Button) findViewById(R.id.loginPingButt); //calls the methods that ping
        // the server to test networking
        pingServerButt.setEnabled(false);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) { //if you hit the three dots in the top right
    // (options menu)
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login_to_server, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) { //called when the user selects and item
    // from the options menu
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.appInfo) { //if the user selected appinfo, open that window/activity
            startActivity(new Intent(this, AppInfo.class));
        }

        return super.onOptionsItemSelected(item);
    }

    public void updateButtons() { //this handles the logic of disabling/reenabling the login buttons
        if (hasEnteredIP){
            pingServerButt.setEnabled(true); //if there is a valid IP, enable the ping button
        } else{
            pingServerButt.setEnabled(false); //else, diable it
        }
        if (hasEnteredUname && hasEnteredPort && hasEnteredIP){
            loginServerButt.setEnabled(true); //if there is enough info to login, enable th login
            // button
        } else {
            loginServerButt.setEnabled(false);
        }
    }

    public void pingServer(View view){ //this method is called when I click the Ping button
        img.setImageResource(R.drawable.ping_loading); //sets the image on the screen to show it
        // is loading
        new TestConnectivity().execute(String.valueOf(ipEditText.getText())); //makes a new
        // asynchronous task (Thread), with the IP as one of the parmeters, then runs it.
    }

    public static void updateConnectivityStatus(float ping){ //this is called once the ping
    // results are back, and updates the image on screen depending on the results
        if (ping < 20 && ping > 0){
            img.setImageResource(R.drawable.ping_great);

        } else if (ping > 20 && ping < 150){
            img.setImageResource(R.drawable.ping_good);
        } else if (ping > 150) {
            img.setImageResource(R.drawable.ping_bad);
        } else {
            img.setImageResource(R.drawable.ping_failed);
        }
        pingText.setText("Ping: " + ping); //update the numerical ping on screen
    }


    public void login(View view){ //this method is called by clicking the login button

        pingServer(view); //might as well ping the server at the same time


        Intent intent = new Intent(this, MainActivity.class); //an intent is the object that
        // expresses the "intent" to open a type of window. In this case we intend to open the
        // mainActivity activity, to show the chat bar.



        this.userName = userNameField.getText().toString(); //we get all the values from the
        // entry boxes
//        this.password = passField.getText().toString();
        this.iPAddress = ipEditText.getText().toString();
        if(portEditText.getText().toString().equals("")){ //sets the default port, if there isn't
        // one entered
            this.port = "6969";
        } else {
            this.port = portEditText.getText().toString();
        }

        startActivity(intent); //then we run the intent, effectively opening the new activity/window
    }
}







package hugra.squadmessenger;

import android.content.Intent;

import android.support.v7.app.ActionBarActivity;
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
    EditText loginField;
    EditText passField;
    EditText ipEditText;
    EditText portEditText;
    static ImageView img;
    static TextView pingText;
    static Button loginServerButt;
    static Button pingServerButt;
    boolean hasEnteredUname = false;
    boolean hasEnteredPW = false;
    boolean hasEnteredIP = false;
    boolean hasEnteredPort = false;
    private static final Pattern IP_ADDRESS
            = Pattern.compile(
            "((25[0-5]|2[0-4][0-9]|[0-1][0-9]{2}|[1-9][0-9]|[1-9])\\.(25[0-5]|2[0-4]"
                    + "[0-9]|[0-1][0-9]{2}|[1-9][0-9]|[1-9]|0)\\.(25[0-5]|2[0-4][0-9]|[0-1]"
                    + "[0-9]{2}|[1-9][0-9]|[1-9]|0)\\.(25[0-5]|2[0-4][0-9]|[0-1][0-9]{2}"
                    + "|[1-9][0-9]|[0-9]))");
    Matcher ipMatcher;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_to_server);
        loginField = (EditText) findViewById(R.id.loginUname);
        loginField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!(s.toString().equals(""))) { //if it is not empty string
                    hasEnteredUname = true;
                    updateButtons();
                } else {
                    hasEnteredUname = false;
                    updateButtons();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
//        passField = (EditText) findViewById(R.id.loginPW);
//        passField.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                if(!(s.toString().equals(""))){ //if it is not empty string
//                    hasEnteredPW = true;
//                    updateButtons();
//                } else {
//                    hasEnteredPW = false;
//                    updateButtons();
//                }
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//
//            }
//        });
        ipEditText = (EditText) findViewById(R.id.loginIP);
        ipEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                ipMatcher = IP_ADDRESS.matcher(s.toString()); //takes the given ip from the
                // edittext and compares it with the IP_ADDRESS regex.
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (ipMatcher.matches()) { //if it is a valid ip
//                    ipEditText.setAlpha((float) 1.0);
                    ipEditText.setTextColor(getResources().getColor(R.color.goodIPColor));
                    hasEnteredIP = true;
                    updateButtons();
                } else { //if not
//                    ipEditText.setAlpha((float) 0.5);
                    ipEditText.setTextColor(getResources().getColor(R.color.badIPColor));
                    hasEnteredIP = false;
                    updateButtons();
                }
            }
        });
        portEditText = (EditText) findViewById(R.id.loginPort);
        portEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!(s.toString().equals(""))) { //if it is not empty string
                    hasEnteredPort = true;
                    updateButtons();
                } else {
                    hasEnteredPort = false;
                    updateButtons();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        img = (ImageView) findViewById(R.id.loginPingImg);
        pingText = (TextView) findViewById(R.id.loginPingText);
        loginServerButt  = (Button) findViewById(R.id.loginLoginButt);
        loginServerButt.setEnabled(false);
        pingServerButt = (Button) findViewById(R.id.loginPingButt);
        pingServerButt.setEnabled(false);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login_to_server, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.loginToMain) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        } else if (id == R.id.appInfo) {
            startActivity(new Intent(this, AppInfo.class));
        }

        return super.onOptionsItemSelected(item);
    }

    public void updateButtons() {
        if (hasEnteredIP){
            pingServerButt.setEnabled(true);
        } else{
            pingServerButt.setEnabled(false);
        }
        if (hasEnteredUname && hasEnteredPort){
            loginServerButt.setEnabled(true);
        } else {
            loginServerButt.setEnabled(false);
        }
    }

    public void pingServer(View view){
        img.setImageResource(R.drawable.ping_loading);
        Log.d("debug", "in pingServer");
        TestConnectivity runer = new TestConnectivity();
        runer.execute(String.valueOf(ipEditText.getText()));
    }

    public static void updateConnectivityStatus(float ping){
        if (ping < 20 && ping > 0){
            img.setImageResource(R.drawable.ping_great);

        } else if (ping > 20 && ping < 150){
            img.setImageResource(R.drawable.ping_good);
        } else if (ping > 150) {
            img.setImageResource(R.drawable.ping_bad);
        } else {
            img.setImageResource(R.drawable.ping_failed);
        }
        pingText.setText("Ping: " + ping);
    }


    public void login(View view){

        pingServer(view);
        LoginDeets creds = new LoginDeets(loginField.getText().toString(), passField.getText()
                .toString());
        Log.d("debug", "in login");
        Log.d("creds", creds.toString());
    }
}







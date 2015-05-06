package hugra.squadmessenger;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import java.net.InetAddress;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class LoginToServer extends ActionBarActivity {
    EditText ipET;
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
        ipET = (EditText) findViewById(R.id.loginIP);
        ipET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                Log.d("debug", s.toString());
                ipMatcher = IP_ADDRESS.matcher(s.toString()); //takes the given ip from the
                // edittext and compares it with the IP_ADDRESS regex.
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (ipMatcher.matches()) { //if it is a valid ip
                    ipET.setAlpha((float) 1.0);
                    ipET.setTextColor(getResources().getColor(R.color.goodIPColor));
                } else { //if not
                    ipET.setAlpha((float) 0.5);
                    ipET.setTextColor(getResources().getColor(R.color.badIPColor));
                }
            }
        });
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

    public void pingServer(View view){
        Log.d("debug", "in pingServer");
        testConnectivity runer = new testConnectivity();
        runer.start();
    }

    public void login(View view){
        Log.d("debug", "in login");
    }
}

class testConnectivity extends Thread {
    public void run(){
//        InetAddress tester = new InetAddress().getAddress(new byte[]{127, 0, 0, 1});
//        tester.isReachable(234);

        for (int i = 0; i < 5; i++) {
            try {
                Log.d("debug", "Inside the run method. ");
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

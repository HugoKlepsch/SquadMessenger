package hugra.squadmessenger;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class LoginToServer extends ActionBarActivity {
    EditText ipET;
    static ImageView img;
    static TextView pingText;
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
        img = (ImageView) findViewById(R.id.loginPingImg);
        pingText = (TextView) findViewById(R.id.loginPingText);
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
        img.setImageResource(R.drawable.ping_loading);
        Log.d("debug", "in pingServer");
        testConnectivity runer = new testConnectivity();
        runer.execute(String.valueOf(ipET.getText()));



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
        Log.d("debug", "in login");
    }
}

class testConnectivity extends AsyncTask<String, Void, String> {
    float pingMilis;
    ImageView img;

    @Override
    protected String doInBackground(String... params) {
        Log.d("bigbug", "In doInBackground");
        // Runs in background thread
        pingMilis = ping(params[0]);//your web service request;


        return String.valueOf(pingMilis);
    }
    @Override
    protected void onPostExecute(String params) {
        Log.d("bigbug", "In onPostExecute");
        float pingMilis = Float.parseFloat(params);
        LoginToServer.updateConnectivityStatus(pingMilis);
        // runs in UI thread - You may do what you want with
        // response
        // Eg Cancel progress dialog - Use result


    }

    private float ping(String url){

        int count = 0;
        String str = "";
//        Log.d("debug", url);
        try {
            Process process;
            if(Build.VERSION.SDK_INT <= 16) {
                // shiny APIS
                process = Runtime.getRuntime().exec("/system/bin/ping -w 1 -c 1 " + url);
            }
            else{
                String options = "-w 1 -c 1 -Q ";
                Log.d("debug", url);
                process = new ProcessBuilder().command("/system/bin/ping", options, url)
                        .redirectErrorStream(true).start();
            }
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

            StringBuilder output = new StringBuilder();
            String temp;

            while ( (temp = reader.readLine()) != null) {
                output.append(temp);
                count++;
//                Log.d("debug", temp);
            }

            reader.close();


            if(count > 0) {
                str = output.toString();
            }

            process.destroy();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Log.d("Length of str: ", String.valueOf(str.length()));
        if (str.length() > 200) {
            String delimiter1 = "=";

            String timeStr = str.split(delimiter1)[3];
            timeStr = timeStr.split(" ms")[0];
            float pingMilis = Float.parseFloat(timeStr);
            Log.i("PING ", String.valueOf(pingMilis));

            return pingMilis;
        } else {
            return (float) -1;
        }
//        return (float) 10;
    }
}

//class testConnectivity extends Thread {
//    EditText ipEt;
//    private float pingMilis;
//    private boolean isReady;
//
//    public testConnectivity(EditText ipET){
//        this.ipEt = ipET;
//    }
//
//    public void run(){
//        isReady = false;
//        pingMilis = ping(ipEt.getText().toString());
//        isReady = true;
//    }
//
//    public boolean isReady(){
//        return isReady;
//    }
//
//    public float getMilis(){
//        return pingMilis;
//    }
//
//    private float ping(String url){
//
//        int count = 0;
//        String str = "";
////        Log.d("debug", url);
//        try {
//            Process process;
//            if(Build.VERSION.SDK_INT <= 16) {
//                // shiny APIS
//                process = Runtime.getRuntime().exec("/system/bin/ping -w 1 -c 1 " + url);
//            }
//            else{
//                String options = "-c 1 -Q ";
//                Log.d("debug", url);
//                process = new ProcessBuilder().command("/system/bin/ping", options, url)
//                        .redirectErrorStream(true).start();
//            }
//            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
//
//            StringBuilder output = new StringBuilder();
//            String temp;
//
//            while ( (temp = reader.readLine()) != null) {
//                output.append(temp);
//                count++;
////                Log.d("debug", temp);
//            }
//
//            reader.close();
//
//
//            if(count > 0) {
//                str = output.toString();
//            }
//
//            process.destroy();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//
//        String delimiter1 = "=";
//
//        String timeStr = str.split(delimiter1)[3];
//        timeStr = timeStr.split(" ms")[0];
//        float pingMilis = Float.parseFloat(timeStr);
//        Log.i("PING ", String.valueOf(pingMilis));
//
//        return pingMilis;
//    }
//}

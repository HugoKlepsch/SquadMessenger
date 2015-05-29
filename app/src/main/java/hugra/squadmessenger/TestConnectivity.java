package hugra.squadmessenger;

import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class TestConnectivity extends AsyncTask<String, Void, String> {
    float pingMilis;

    @Override
    protected String doInBackground(String... params) {
        // Runs in background thread
        pingMilis = ping(params[0]);//your web service request;

        return String.valueOf(pingMilis); //this returns the value to onPostExecute, as a parameter
    }
    @Override
    protected void onPostExecute(String params) {
        float pingMilis = Float.parseFloat(params);
        LoginToServer.updateConnectivityStatus(pingMilis); //the ui must be updated in the class
        // that is originated.
        // runs in UI thread - You may do what you want with
        // response
        // Eg Cancel progress dialog - Use result


    }

    private float ping(String url){

        int count = 0;
        String str = "";
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
    }
}
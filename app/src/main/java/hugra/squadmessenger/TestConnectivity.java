/*
		 Title: TestConnectivity.java
		 Programmer: hugo
		 Date of creation: May 26, 2015
		 Description: This file does not describe any activity or window, however it runs the
		 logic of pinging the server, and returning the results
 */



package hugra.squadmessenger;

import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class TestConnectivity extends AsyncTask<String, Void, String> { //since this extands the
// async task, when we make an object of it, then run it, it runs in a separate thread. It also
// requires that we override some methods tomorrow.
    float pingMilis; //the ping value

    @Override
    protected String doInBackground(String... params) { //this runs when we call .run on the
    // object, in a separate thread, for UI safety
        // Runs in background thread
        pingMilis = ping(params[0]); //calls the my ping method with the IP address passed from
        // the constructor.

        return String.valueOf(pingMilis); //this returns the value to onPostExecute, as a parameter
    }
    @Override
    protected void onPostExecute(String params) { //gets called when doInBackgroudn exits, and
    // runs on the UI thread, not UI thread safe.
        float pingMilis = Float.parseFloat(params);
        LoginToServer.updateConnectivityStatus(pingMilis); //the ui must be updated in the class
        // that is originated.
        // runs in UI thread - You may do what you want with
        // response


    }

    private float ping(String url){ //takes in a string IP of url and pings it, returning a float
    // of the time

        int count = 0;
        String str = "";
        try {
            Process process;
            if(Build.VERSION.SDK_INT <= 16) { //if the phone is old
                // shiny APIS
                process = Runtime.getRuntime().exec("/system/bin/ping -w 1 -c 1 -Q" + url);
                //excecutes the "ping" command from command line, wiht command options to only do
                // it once
            }
            else{
                String options = "-w 1 -c 1 -Q ";
                process = new ProcessBuilder().command("/system/bin/ping", options, url) //a
                // different, newer way of calling system commands from the command line
                        .redirectErrorStream(true).start();
            }
            BufferedReader reader = new BufferedReader(new InputStreamReader(process
                    .getInputStream())); //gets the output stream of the ping command line
                    // interface

            StringBuilder output = new StringBuilder(); //accumulates the output from the output
            // stream from the headless command line interface
            String temp;

            while ( (temp = reader.readLine()) != null) { //reads from the stream
                output.append(temp);
                count++;
            }

            reader.close(); //closes the stream to empty memory


            if(count > 0) {
                str = output.toString();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (str.length() > 200) { //if the ping fails, it returns something less than 200 chars,
        // so if it's greater, than it probably ping properly
            String delimiter1 = "=";

            String timeStr = str.split(delimiter1)[3]; //string manipulation of the output to
            // find the ping
            timeStr = timeStr.split(" ms")[0];
            float pingMilis = Float.parseFloat(timeStr);

            return pingMilis;
        } else { //if it did not connect, return an error code
            return (float) -1;
        }
    }
}
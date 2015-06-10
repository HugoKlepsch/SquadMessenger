/*
		 Title: AppInfo.java
		 Programmer: hugo
		 Date of creation: June 1, 2015
		 Description: The class/thread that describes the App info "activity" (window)
 */



package hugra.squadmessenger;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;


public class AppInfo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) { //The only thing this activity has to do
    // is create itself, load the layout from xml & load the message
        super.onCreate(savedInstanceState); //If there is a saved instance of the window, use
        // that (this is there by deafult)
        setContentView(R.layout.activity_app_info); //load the layout from the xml file


        android.support.v7.app.ActionBar actionBar = getSupportActionBar(); //the following lines
        // gets the title bar that includes the back/up button, and removes it because it causes
        // the mainActivity to reconnect to the server in stead of keeping it in the background.
        actionBar.setDisplayHomeAsUpEnabled(false); // remove the left caret
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(false); // disable the button

            actionBar.setDisplayShowHomeEnabled(false); // remove the icon
        }
    }


}

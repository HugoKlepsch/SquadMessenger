/*
		 Title: ShowUsers.java
		 Programmer: hugo
		 Date of creation: June 1, 2015
		 Description: This is a simple activity that only shows a small list of connected users,
		 it does not update ever, but this could be a future improvement
 */

package hugra.squadmessenger;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import java.util.Vector;


public class ShowUsers extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) { //runs on activity open
        Vector<String> userList = MainActivity.users; //holds the list of users
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_show_users); //load the layout from the xml file
        android.support.v7.app.ActionBar actionBar = getSupportActionBar(); //the following lines
        // gets the title bar that includes the back/up button, and removes it because it causes
        // the mainActivity to reconnect to the server in stead of keeping it in the background.
        actionBar.setDisplayHomeAsUpEnabled(false); // remove the left caret
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(false); // disable the button

            actionBar.setDisplayShowHomeEnabled(false); // remove the icon
        }

        TextView users = (TextView) findViewById(R.id.ShowUsers_Users);
        users.setText("Users: \n"); //add each user to the screen
        for (int i = 0; i < userList.size(); i ++){
            users.setText(users.getText() + userList.get(i) + "\n");

        }
    }


}

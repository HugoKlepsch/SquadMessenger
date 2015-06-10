package hugra.squadmessenger;

import android.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.Vector;


public class ShowUsers extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Vector<String> userList = MainActivity.users;
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_show_users);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(false); // remove the left caret
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(false); // disable the button

            actionBar.setDisplayShowHomeEnabled(false); // remove the icon
        }
        TextView users = (TextView) findViewById(R.id.ShowUsers_Users);
        users.setText("Users: \n");
        for (int i = 0; i < userList.size(); i ++){
            users.setText(users.getText() + userList.get(i) + "\n");
        }

    }


}

package hugra.squadmessenger;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.Vector;


public class ShowUsers extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Vector<String> userList = MainActivity.users;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_users);
        TextView users = (TextView) findViewById(R.id.ShowUsers_Users);
        users.setText("Users: \n");
        for (int i = 0; i < userList.size(); i ++){
            users.setText(users.getText() + userList.get(i) + "\n");
        }

    }


}

package com.example.tinagross.vigourfinal;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {

    public final static String EXTRA_MESSAGE = "com.example.tinagross.activityswitch.MESSAGE";

    public void sendMessage (View view){
        Intent intent = new Intent(this,BTSettingsActivity.class);
        EditText editText1 = (EditText) findViewById(R.id.edit_message);
        String username = editText1.getText().toString();
        EditText editText2 = (EditText) findViewById(R.id.password_submit);
        String password = editText2.getText().toString();
        /*Check here if password and username are correct*/
        intent.putExtra(EXTRA_MESSAGE, "hello " + username + "!");
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FindDevices frag = new FindDevices();
        frag.show(getFragmentManager(), "Dialog");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    static public class FindDevices extends DialogFragment {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the Builder class for convenient dialog construction
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage(R.string.paired_message);
            builder
                    .setNegativeButton("Cancel",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    //dialog.dismiss();
                                    dialog.cancel();
                                }
                            });

            AlertDialog alertD = builder.create();
            alertD.show();

            // Create the AlertDialog object and return it
            return builder.create();
        }
    }
}

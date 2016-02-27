package com.example.tinagross.vigourfinal;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class BTSearchActivity extends AppCompatActivity {

    private ArrayList<BluetoothDevice> activeDevices = null;
    private ArrayAdapter<String> detectedAdapter;
    private ListView lv;
    private BluetoothAdapter BA;
    private ArrayList<String> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_btsearch);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        BA = BluetoothAdapter.getDefaultAdapter();
        list = new ArrayList<String>();
        detectedAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,list);
        Intent intent = getIntent();
        activeDevices = new ArrayList<BluetoothDevice>();
        lv = (ListView)findViewById(R.id.listView);

        if(BA.isDiscovering()){
            //BA.cancelDiscovery();
            Toast.makeText(getApplicationContext(), "is discovering...", Toast.LENGTH_SHORT).show();
        }
        IntentFilter filter = new IntentFilter();

        filter.addAction(BluetoothDevice.ACTION_FOUND);
        filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED);
        filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);

        BTSearchActivity.this.registerReceiver(mReceiver, filter);
        FindDevices newFragment = new FindDevices();
        newFragment.show(getFragmentManager(), "Dialogue");
        BA.startDiscovery();
        Toast.makeText(getApplicationContext(), "Starting Discovery...", Toast.LENGTH_SHORT).show();
        if(getFragmentManager().findFragmentById(newFragment.getId())==newFragment){
            Toast.makeText(getApplicationContext(), "Trying to dismiss Dialog", Toast.LENGTH_SHORT).show();
            newFragment.dismiss();
            getFragmentManager().beginTransaction().remove(newFragment).commit();
        }
    }

    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            Toast.makeText(getApplicationContext(), "RECEIVER HAS BEEN CALLED!", Toast.LENGTH_SHORT).show();
            String action = intent.getAction();

            if (BluetoothAdapter.ACTION_DISCOVERY_STARTED.equals(action)) {


                Toast.makeText(getApplicationContext(), "Finding Devices...", Toast.LENGTH_SHORT).show();
            } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
                /***************PROBLEMS************************/
                Toast.makeText(getApplicationContext(), "Discovery has ended", Toast.LENGTH_SHORT).show();
                for(BluetoothDevice bt : activeDevices)
                    list.add(bt.getName() + "\t" + bt.getAddress());

                //updateList(adapter, list);
            } else if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);

                Toast.makeText(getApplicationContext(), "Showing Active Devices", Toast.LENGTH_SHORT).show();
                Toast.makeText(getApplicationContext(), "Device Found:" + device.getName(), Toast.LENGTH_SHORT).show();
                /***************PROBLEMS************************/
                detectedAdapter.add(device.getName()+"\n"+device.getAddress());
                activeDevices.add(device);
                detectedAdapter.notifyDataSetChanged();
                lv.setAdapter(detectedAdapter);
            }
        }

        /*public ArrayAdapter updateList(ArrayAdapter oldAdapter, ArrayList<String> newList){
            oldAdapter.clear();
            oldAdapter.addAll(newList);
            return oldAdapter;
        }*/
    };

    static public class FindDevices extends DialogFragment {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the Builder class for convenient dialog construction
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage(R.string.dialogue_message);
            builder
                    .setCancelable(false)
                    .setNegativeButton("Cancel",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.dismiss();
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

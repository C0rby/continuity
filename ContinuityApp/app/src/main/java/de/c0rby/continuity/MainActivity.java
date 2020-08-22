package de.c0rby.continuity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import de.c0rby.continuity.model.Receiver;

public class MainActivity extends AppCompatActivity {
    private NsdHelper nsdHelper;

    private RecyclerView recyclerView;
    List<Receiver> receivers;
    private ReceiverAdapter receiverAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recyclerView);
        this.getSupportActionBar().hide();
        createReceivers();
    }

    @Override
    protected void onStart() {
        nsdHelper = new NsdHelper(this);
        nsdHelper.discoverServices();
        super.onStart();
    }

    @Override
    protected void onPause() {
        if (nsdHelper != null) {
            this.nsdHelper.stopDiscovery();
        }
        super.onPause();
    }
    @Override
    protected void onResume() {
        super.onResume();
        if (nsdHelper != null) {
            nsdHelper.discoverServices();
        }
    }
    // For KitKat and earlier releases, it is necessary to remove the
    // service registration when the application is stopped.  There's
    // no guarantee that the onDestroy() method will be called (we're
    // killable after onStop() returns) and the NSD service won't remove
    // the registration for us if we're killed.
    // In L and later, NsdService will automatically unregister us when
    // our connection goes away when we're killed, so this step is
    // optional (but recommended).
    @Override
    protected void onStop() {
        nsdHelper = null;
        super.onStop();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    private void createReceivers() {
        receivers = new ArrayList<>(4);
        receivers.add(new Receiver("Evas Wohnzimmer", "http://192.168.0.33:8080"));
        receivers.add(new Receiver("Yocto", "http://192.168.178.184:8080"));
        receivers.add(new Receiver("Laptop", "http://192.168.178.157:8080"));
        receivers.add(new Receiver("Mein Wohnzimmer", "http://192.168.178.12:8080"));
        receivers.add(new Receiver("Mein Schlafzimmer", "http://192.168.178.23:8080"));
        receiverAdapter = new ReceiverAdapter(getBaseContext(), receivers);
        recyclerView.setAdapter(receiverAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getBaseContext(),LinearLayoutManager.VERTICAL, false));
        recyclerView.setNestedScrollingEnabled(false);
    }
}
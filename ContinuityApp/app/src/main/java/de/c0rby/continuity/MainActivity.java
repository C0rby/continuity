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

    private void createReceivers() {
        receivers = new ArrayList<>(4);
        receivers.add(new Receiver("Evas Wohnzimmer", "http://192.168.0.33:8080"));
        receivers.add(new Receiver("Yocto", "http://192.168.178.84:8080"));
        receivers.add(new Receiver("Laptop", "http://192.168.178.157:8080"));
        receivers.add(new Receiver("Mein Wohnzimmer", "http://192.168.178.12:8080"));
        receivers.add(new Receiver("Mein Schlafzimmer", "http://192.168.178.23:8080"));
        receiverAdapter = new ReceiverAdapter(getBaseContext(), receivers);
        recyclerView.setAdapter(receiverAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getBaseContext(),LinearLayoutManager.VERTICAL, false));
        recyclerView.setNestedScrollingEnabled(false);
    }
}
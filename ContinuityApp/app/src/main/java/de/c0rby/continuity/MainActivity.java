package de.c0rby.continuity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
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
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    private NsdHelper nsdHelper;

    private RecyclerView recyclerView;
    List<Receiver> receivers;
    private ReceiverAdapter receiverAdapter;
    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            RecyclerView.ViewHolder vh = (RecyclerView.ViewHolder) view.getTag();
            int pos = vh.getAdapterPosition();

            final Receiver r = receivers.get(pos);
            new AsyncTask<String, Void, Long>() {

                @Override
                protected Long doInBackground(String... strings) {
                    OkHttpClient client = new OkHttpClient();

                    final Intent intent = getIntent();
                    if (intent == null) {
                        return 0L;
                    }
                    final String url = intent.getStringExtra(Intent.EXTRA_TEXT);

                    StringBuilder json = new StringBuilder();
                    json.append("{\"type\":\"URL\",\"value\":\"").append(url).append("\"}");
                    RequestBody body = RequestBody.create(json.toString(), MediaType.get("application/json; charset=utf-8"));
                    Request req = new Request.Builder()
                            .url(r.getAddress() + "/open")
                            .post(body)
                            .build();
                    try (Response response = client.newCall(req).execute()) {
                        Log.i("ReceiverAdapter", response.body().string());
                    } catch (IOException e) {
                        Log.e("ReceiverAdapter", e.getMessage());
                    }
                    return 0L;
                }
            }.execute();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recyclerView);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);
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
        receivers.add(new Receiver("Evas Wohnzimmer", "http://192.168.0.33:8080"));



        receiverAdapter = new ReceiverAdapter(receivers);
        receiverAdapter.setOnClickListener(onClickListener);
        recyclerView.setAdapter(receiverAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getBaseContext(),LinearLayoutManager.VERTICAL, false));
        recyclerView.setNestedScrollingEnabled(false);
    }
}
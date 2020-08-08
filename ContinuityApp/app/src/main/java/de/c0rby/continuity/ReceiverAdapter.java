package de.c0rby.continuity;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;


import java.io.IOException;
import java.util.List;

import de.c0rby.continuity.model.Receiver;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ReceiverAdapter extends RecyclerView.Adapter<ReceiverAdapter.ViewHolder> {
    private LayoutInflater inflater;
    private List<Receiver> receivers;
    private Context ctx;

    public ReceiverAdapter(Context ctx, List<Receiver> receivers) {
        inflater = LayoutInflater.from(ctx);
        this.receivers = receivers;
        this.ctx = ctx;
    }

    @Override
    public ReceiverAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.receiver_layout, parent, false);
        return new ReceiverAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ReceiverAdapter.ViewHolder vh, final int position) {
        vh.name.setText(receivers.get(position).getName());
        vh.address.setText(receivers.get(position).getAddress());
        vh.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Receiver r = receivers.get(position);

                new AsyncTask<String, Void, Long>() {

                    @Override
                    protected Long doInBackground(String... strings) {
                        OkHttpClient client = new OkHttpClient();

                        StringBuilder json = new StringBuilder();
                        json.append("{\"type\":\"URL\",\"value\":\"").append("https://heise.de").append("\"}");
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
                Toast.makeText(ctx, receivers.get(position).getName(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return receivers.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        TextView address;

        public ViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            address = itemView.findViewById(R.id.address);
        }
    }
}

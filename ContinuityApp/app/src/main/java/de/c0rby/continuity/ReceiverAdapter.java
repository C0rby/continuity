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
    private List<Receiver> receivers;
    private View.OnClickListener listener;

    public ReceiverAdapter(List<Receiver> receivers ) {
        this.receivers = receivers;
    }

    public void setOnClickListener(View.OnClickListener listener) {
        this.listener = listener;
    }

    public void addReceiver(Receiver r) {
        this.receivers.add(r);
    }

    @Override
    public ReceiverAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.receiver_layout, parent, false);
        return new ReceiverAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ReceiverAdapter.ViewHolder vh, final int position) {
        vh.name.setText(receivers.get(position).getName());
        vh.address.setText(receivers.get(position).getAddress());
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
            itemView.setTag(this);
            itemView.setOnClickListener(listener);

            name = itemView.findViewById(R.id.name);
            address = itemView.findViewById(R.id.address);
        }
    }
}

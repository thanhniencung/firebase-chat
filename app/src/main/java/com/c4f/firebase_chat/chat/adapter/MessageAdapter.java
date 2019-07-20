package com.c4f.firebase_chat.chat.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.c4f.firebase_chat.R;
import com.c4f.firebase_chat.model.Message;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MyViewHolder> {
    private List<Message> messages = new ArrayList<>();

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView tvName;
        private TextView tvTime;
        private TextView tvContent;

        public MyViewHolder(View v) {
            super(v);
            tvName = v.findViewById(R.id.name);
            tvTime = v.findViewById(R.id.time);
            tvContent = v.findViewById(R.id.content);
        }
    }

    public MessageAdapter() {}

    public void setData(List<Message> messages) {
        this.messages.addAll(messages);
        notifyDataSetChanged();
    }

    public void addData(Message message) {
        this.messages.add(message);
        notifyItemInserted(this.messages.size() - 1);
    }

    @Override
    public MessageAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                         int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_message, parent, false);
        return new MessageAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MessageAdapter.MyViewHolder holder, int position) {
        Log.e("ADAPTER", String.valueOf(position));
        holder.tvName.setText(messages.get(position).getOwner().getDisplayName());
        holder.tvContent.setText(messages.get(position).getContent());


        SimpleDateFormat sdf = new SimpleDateFormat("dd/M/yyyy");
        String date = sdf.format(new Date());
        holder.tvTime.setText(date);
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }
}
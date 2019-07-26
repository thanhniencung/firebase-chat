package com.c4f.firebase_chat.friend.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.c4f.firebase_chat.R;
import com.c4f.firebase_chat.model.User;
import com.c4f.firebase_chat.model.event.ClickFriendEvent;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

//https://developer.android.com/guide/topics/ui/layout/recyclerview
public class FriendAdapter extends RecyclerView.Adapter<FriendAdapter.MyViewHolder> {
    private List<User> friends = new ArrayList<>();
    ColorGenerator generator = ColorGenerator.MATERIAL;
    private User me;

    public void setMe(User me) {
        this.me = me;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView tvName;
        private ImageView ivAvatar;

        public MyViewHolder(View v) {
            super(v);
            tvName = v.findViewById(R.id.friendName);
            ivAvatar = v.findViewById(R.id.imageView);

            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    User friend = friends.get(getAdapterPosition());
                    String roomId = buildRoomId(me.getId(), friend.getId());
                    EventBus.getDefault().post(new ClickFriendEvent(roomId, friend.getDisplayName(), me));
                }
            });
        }
    }

    private String buildRoomId(int myId, int friendId) {
        if (myId < friendId) {
            return myId + "-" + friendId;
        }
        return friendId + "-" + myId; // 1-2
    }

    public FriendAdapter() {}

    public void setData(List<User> friends) {
        this.friends.addAll(friends);
        notifyDataSetChanged();
    }

    public void addData(User user) {
        this.friends.add(user);
        notifyDataSetChanged();
    }

    @Override
    public FriendAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                     int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_friend, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.tvName.setText(friends.get(position).getDisplayName());

        String letter = String.valueOf(friends.get(position).getDisplayName().charAt(0));

        ColorGenerator generator = ColorGenerator.MATERIAL;
        TextDrawable drawable = TextDrawable.builder()
                .buildRound(letter, generator.getRandomColor());

        holder.ivAvatar.setImageDrawable(drawable);
    }

    @Override
    public int getItemCount() {
        return friends.size();
    }
}
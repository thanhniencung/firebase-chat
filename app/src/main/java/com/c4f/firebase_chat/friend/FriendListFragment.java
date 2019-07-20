package com.c4f.firebase_chat.friend;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.c4f.firebase_chat.MainActivity;
import com.c4f.firebase_chat.R;
import com.c4f.firebase_chat.chat.ChatFragment;
import com.c4f.firebase_chat.friend.adapter.FriendAdapter;
import com.c4f.firebase_chat.model.User;
import com.c4f.firebase_chat.model.event.ClickFriendEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

public class FriendListFragment extends Fragment {

    private FriendListViewModel viewModel;
    private FriendAdapter adapter;

    public static FriendListFragment newInstance() {
        return new FriendListFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.friend_list_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = FriendListViewModel.of(this);

        viewModel.getMyInfo();
        viewModel.getFriendList();

        onFriendListResponse();
        onUserResponse();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getActivity().getApplicationContext());
        layoutManager.setOrientation(RecyclerView.VERTICAL);

        recyclerView.setLayoutManager(layoutManager);

        adapter = new FriendAdapter();
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    void onUserResponse() {
        viewModel.getUserResponse().observe(this, new Observer<User>() {
            @Override
            public void onChanged(User value) {
                adapter.setMe(value);
            }
        });
    }

    void onFriendListResponse() {
        viewModel.getFriendListResponse().observe(this, new Observer<List<User>>() {
            @Override
            public void onChanged(List<User> user) {
                if (user == null) {
                    return;
                }
                adapter.setData(user);
            }
        });
    }

    @Subscribe
    public void onFriendClicked(ClickFriendEvent event) {
        ((MainActivity) getActivity()).changeTitle(event.getFriend());
        ((MainActivity) getActivity())
                .changeFragment(ChatFragment.newInstance(event.getRoomId()));
    }
}

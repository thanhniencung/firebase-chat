package com.c4f.firebase_chat.chat;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.c4f.firebase_chat.R;
import com.c4f.firebase_chat.chat.adapter.MessageAdapter;
import com.c4f.firebase_chat.model.Message;
import com.c4f.firebase_chat.model.User;

import java.util.List;

public class ChatFragment extends Fragment {

    private ChatViewModel viewModel;
    private MessageAdapter adapter;

    private LinearLayoutManager layoutManager;
    private RecyclerView recyclerView;
    private EditText editText;

    private String roomId;
    private User me;

    public static ChatFragment newInstance(String roomId, User me) {
        ChatFragment chatFragment = new ChatFragment();
        Bundle bundle = new Bundle();
        bundle.putString("ROOM_ID", roomId);
        bundle.putSerializable("ME", me);
        chatFragment.setArguments(bundle);
        return chatFragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.chat_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = ChatViewModel.of(this);
        adapter = new MessageAdapter();

        roomId = getArguments().getString("ROOM_ID");
        me = (User) getArguments().getSerializable("ME");

        editText = view.findViewById(R.id.chatBox);
        recyclerView = view.findViewById(R.id.recyclerView);

        layoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        layoutManager.setStackFromEnd(true);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        view.findViewById(R.id.send).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = editText.getText().toString();
                if (content.isEmpty()) {
                    return;
                }

                Message message = new Message();
                message.setContent(content);
                message.setOwner(me);

                viewModel.sendMessage(roomId, message);
            }
        });

        viewModel.getMessageList(getArguments().getString("ROOM_ID"));
        onListMessage();
        onMessage();
    }

    void onListMessage() {
        viewModel.getMsgListResponse().observe(this, new Observer<List<Message>>() {
            @Override
            public void onChanged(List<Message> messages) {
                if (messages.size() > 0) {
                    adapter.setData(messages);
                    recyclerView.scrollToPosition(adapter.getItemCount() - 1);
                }

                long start = messages.size() == 0 ? 0 : messages.get(messages.size() -1).getTime();
                viewModel.listenNewMessage(roomId, start);
            }
        });
    }

    void onMessage() {
        viewModel.getMsgResponse().observe(this, new Observer<Message>() {
            @Override
            public void onChanged(Message message) {
                editText.setText("");
                adapter.addData(message);
                recyclerView.scrollToPosition(adapter.getItemCount() - 1);
            }
        });
    }

}

package com.c4f.firebase_chat;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.c4f.firebase_chat.friend.FriendListFragment;

// https://firebase.google.com/docs/android/setup
public class MainActivity extends AppCompatActivity {
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("SNAPCHAT");
        toolbar.setTitleTextColor(0xff000000);
        setSupportActionBar(toolbar);

        changeFragment(FriendListFragment.newInstance());
    }

    public void changeFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        transaction.add(R.id.container, fragment);
        transaction.addToBackStack(fragment.getClass().getName());

        transaction.commit();
    }

    public void changeTitle(String title) {
        toolbar.setTitle(title);
    }
}

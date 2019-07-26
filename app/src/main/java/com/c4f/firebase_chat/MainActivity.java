package com.c4f.firebase_chat;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.c4f.firebase_chat.chat.ChatFragment;
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
        if (fragment instanceof ChatFragment) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });
        }

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        transaction.add(R.id.container, fragment);
        if (!(fragment instanceof FriendListFragment)) {
            transaction.addToBackStack(fragment.getClass().getName());
        }

        transaction.commit();
    }

    public void changeTitle(String title) {
        toolbar.setTitle(title);
    }

    @Override
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() == 0) {
            hideKeyboard(this);
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        }
        super.onBackPressed();
    }

    public void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}

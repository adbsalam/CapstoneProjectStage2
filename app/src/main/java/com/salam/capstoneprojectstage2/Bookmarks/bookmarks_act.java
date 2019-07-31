package com.salam.capstoneprojectstage2.Bookmarks;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.salam.capstoneprojectstage2.R;

public class bookmarks_act extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookmarks_act);



        addFragment(new booking_list_fragment(), false, "one");



    }


    public void addFragment(booking_list_fragment fragment, boolean addToBackStack, String tag) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction ft = manager.beginTransaction();
        if (addToBackStack) {
            ft.addToBackStack(tag);
        }
        ft.replace(R.id.booking_container, fragment, tag);
        ft.commitAllowingStateLoss();
    }




}

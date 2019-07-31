package com.salam.capstoneprojectstage2.Search_query_UI;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.salam.capstoneprojectstage2.R;

public class search_results extends AppCompatActivity {


    String  day_min, month_min, year_min;
    String  day_max, month_max, year_max;
    String Keyword_search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);
        getSupportActionBar().setTitle("Results");
        Intent query_parameters = getIntent();
        day_min = query_parameters.getStringExtra("minday");
        month_min = query_parameters.getStringExtra("minmonth");
        year_min = query_parameters.getStringExtra("minyear");

        day_max = query_parameters.getStringExtra("maxday");
        month_max = query_parameters.getStringExtra("maxmonth");
        year_max = query_parameters.getStringExtra("maxyear");
        Keyword_search = query_parameters.getStringExtra("key_word");


        addFragment(new query_results_fragment(), false, "one");


    }

    public void addFragment(query_results_fragment fragment, boolean addToBackStack, String tag) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction ft = manager.beginTransaction();
        if (addToBackStack) {
            ft.addToBackStack(tag);
        }
        ft.replace(R.id.container, fragment, tag);
        ft.commitAllowingStateLoss();
        Bundle bundle = new Bundle();
        bundle.putString("minday", day_min);
        bundle.putString("minmonth", month_min);
        bundle.putString("minyear", year_min);

        bundle.putString("maxday", day_max);
        bundle.putString("maxmonth", month_max);
        bundle.putString("maxyear", year_max);
        bundle.putString("key_word", Keyword_search);

        fragment.setArguments(bundle);
    }


}

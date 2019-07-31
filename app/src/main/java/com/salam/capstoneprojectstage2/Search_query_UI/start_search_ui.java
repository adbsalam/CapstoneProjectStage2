package com.salam.capstoneprojectstage2.Search_query_UI;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.salam.capstoneprojectstage2.R;

public class start_search_ui extends AppCompatActivity {
    DatePicker datePicker_min;
    DatePicker datePicker_max;
    Button search_button;
    String  day_min, month_min, year_min;
    String  day_max, month_max, year_max;
    EditText keyword;
    String Keyword_search;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_search_ui);
        getSupportActionBar().setTitle("Search");

        datePicker_max = findViewById(R.id.maxdatepick);
        datePicker_min = findViewById(R.id.mindatepick);
        keyword = findViewById(R.id.search_keyword);
            search_button = findViewById(R.id.search_now_ui);

       search_button.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               day_min = String.valueOf(datePicker_min.getDayOfMonth());
               month_min = String.valueOf(datePicker_min.getMonth());
               year_min = String.valueOf(datePicker_min.getYear());

               day_max = String.valueOf(datePicker_max.getDayOfMonth());
               month_max = String.valueOf(datePicker_max.getMonth());
               year_max = String.valueOf(datePicker_max.getYear());


               Keyword_search = keyword.getText().toString();


               Intent search_start = new Intent(start_search_ui.this, search_results.class);
               search_start.putExtra("minday", day_min);
               search_start.putExtra("minmonth", month_min);
               search_start.putExtra("minyear", year_min);

               search_start.putExtra("maxday", day_max);
               search_start.putExtra("maxmonth", month_max);
               search_start.putExtra("maxyear", year_max);
               search_start.putExtra("key_word", Keyword_search);


               startActivity(search_start);
           }
       });


    }
}

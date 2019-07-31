package com.salam.capstoneprojectstage2.Search_query_UI;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
    String date_min, date_max;






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


               if (Integer.valueOf(month_min) < 10){
                    date_min = year_min+"-"+"0"+month_min+"-"+day_min;
               }else
               {
                 date_min =  year_min+"-"+month_min+"-"+day_min;
               }

               if (Integer.valueOf(month_max) < 10){
                   date_max = year_max+"-"+"0"+month_max+"-"+day_max;
               }
               else {
                   date_max = year_max+"-"+month_max+"-"+day_max;

               }

               Keyword_search = keyword.getText().toString();







               Intent search_start = new Intent(start_search_ui.this, search_results.class);
                search_start.putExtra("date_max", date_max);
               search_start.putExtra("date_min", date_min);

               Log.d("fuck", date_max);
               Log.d("fuck", date_min);
               Log.d("fuck", month_max);
               startActivity(search_start);
           }
       });


    }
}

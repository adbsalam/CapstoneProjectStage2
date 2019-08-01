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
    String date_min, date_max;
    Integer new_month_min, new_month_max;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_search_ui);
        getSupportActionBar().setTitle(getString(R.string.SEARCH_S));

        datePicker_max = findViewById(R.id.maxdatepick);
        datePicker_min = findViewById(R.id.mindatepick);
        keyword = findViewById(R.id.search_keyword);
        search_button = findViewById(R.id.search_now_ui);

       search_button.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

               if (keyword.getText().toString().isEmpty()){
                   keyword.setError(getString(R.string.KEY_REQ));
                   keyword.requestFocus();
                   return;
               }



               //MINIMUM DATE ENTRY FOR SEARCH
               day_min = String.valueOf(datePicker_min.getDayOfMonth());
               month_min = String.valueOf(datePicker_min.getMonth());
               new_month_min = Integer.valueOf(month_min) +1;
               year_min = String.valueOf(datePicker_min.getYear());

               //MAXIMUM DATE ENTRY FOR SEARCH
               day_max = String.valueOf(datePicker_max.getDayOfMonth());
               month_max = String.valueOf(datePicker_max.getMonth());
               new_month_max = Integer.valueOf(month_max) + 1;
               year_max = String.valueOf(datePicker_max.getYear());
               //FORMAT TEXT FOR DATE MIN AS YYYY-MM-DD
               if (new_month_min < 10){
                    date_min = year_min+"-"+"0"+new_month_min.toString()+"-"+day_min;
               }else
               {
                 date_min =  year_min+"-"+new_month_min.toString()+"-"+day_min;
               }
               //FORMAT TEXT FOR DATE MAX AS YYYY-MM-DD
               if (new_month_max < 10){
                   date_max = year_max+"-"+"0"+new_month_max.toString()+"-"+day_max;
               }
               else {
                   date_max = year_max+"-"+new_month_max.toString()+"-"+day_max;
               }

               Keyword_search = keyword.getText().toString();
               //PASS TO SEARCH RESULTS TO SHOW THE RESULTS
               Intent search_start = new Intent(start_search_ui.this, search_results.class);
                search_start.putExtra(getString(R.string.DATE_MAX), date_max);
               search_start.putExtra(getString(R.string.DATE_MIN), date_min);
               search_start.putExtra(getString(R.string.KEYWORD), Keyword_search);
               startActivity(search_start);
           }
       });


    }
}

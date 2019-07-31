package com.salam.capstoneprojectstage2.Search_query_UI;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.salam.capstoneprojectstage2.Adapter.search_results_adapter;
import com.salam.capstoneprojectstage2.Models.search_results_model;
import com.salam.capstoneprojectstage2.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class query_results_fragment extends Fragment {
    private RecyclerView recyclerView;
    private List<search_results_model> case_list = new ArrayList<>();
    private search_results_adapter mAdapter;
    String  day_min, month_min, year_min;
    String  day_max, month_max, year_max;
    String Keyword_search;
    private ProgressBar progressBar;
    private int pStatus = 0;
    private Handler handler = new Handler();


    public query_results_fragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_query_results_fragment, container, false);

        Bundle bundle=getArguments();
        assert bundle != null;
        day_min = bundle.getString("minday");
        month_min = bundle.getString("minmonth");
        year_min = bundle.getString("minyear");
        day_max = bundle.getString("maxday");
        month_max = bundle.getString("maxmonth");
        year_max = bundle.getString("maxyear");
        Keyword_search = bundle.getString("key_word");
        progressBar = view.findViewById(R.id.progress);





        recyclerView = view.findViewById(R.id.search_results_recycler);
        mAdapter = new search_results_adapter(getActivity(), case_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());


        new LongOperation().execute("");
        return view;


    }


    private void ShowItemsNames() {
/*
        Uri.Builder builder = new Uri.Builder();
        builder.scheme(getString(R.string.auth))
                .authority(getString(R.string.authority))
                .appendPath(getString(R.string.topher))
                .appendPath(getString(R.string.year))
                .appendPath(getString(R.string.month))
                .appendPath(getString(R.string.link)).appendPath(getString(R.string.jsonname));

                */

        String URL = "https://api.case.law/v1/cases/?search="+Keyword_search;

        RequestQueue Video_Queue = Volley.newRequestQueue(getContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);


                            JSONArray JSONARRAY = jsonObject.getJSONArray("results");
                            for(int k =0; k<30; k++){
                                JSONObject stepsobject = JSONARRAY.getJSONObject(k);
                                search_results_model VL = new search_results_model(stepsobject.getString("name_abbreviation"),stepsobject.getString("docket_number"), stepsobject.getString("decision_date"), stepsobject.getString("id"));
                                VL.setTitle(stepsobject.getString("name_abbreviation"));
                                VL.setJurisdiction(stepsobject.getString("docket_number"));
                                VL.setDate(stepsobject.getString("decision_date"));
                                VL.setCaseid(stepsobject.getString("id"));
                                case_list.add(VL);
                                recyclerView.setAdapter(mAdapter);
                            }
                            mAdapter.notifyDataSetChanged();

                }
                catch (JSONException e){
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
        Video_Queue.add(stringRequest);
    }




    private class LongOperation extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {


            ShowItemsNames();


            return "done";
        }

        @Override
        protected void onPostExecute(String result) {
            progressBar.setVisibility(View.INVISIBLE);

        }

        @Override
        protected void onPreExecute() {



        }

        @Override
        protected void onProgressUpdate(Void... values) {
            progressBar.setVisibility(View.VISIBLE);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    while (pStatus <= 100) {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                progressBar.setProgress(pStatus);
                                //txtProgress.setText(pStatus + " %");
                            }
                        });
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        pStatus++;
                    }
                }
            }).start();

        }
    }










    public void onButtonPressed(Uri uri) {
    }
}

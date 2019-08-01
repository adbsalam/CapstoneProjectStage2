package com.salam.capstoneprojectstage2.Search_query_UI;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
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
    private String date_min, date_max;
    private String Keyword_search;
    private ProgressBar progressBar;
    TextView no_results_lbl;
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
        no_results_lbl = view.findViewById(R.id.no_results);
        no_results_lbl.setVisibility(View.INVISIBLE);
        Bundle bundle=getArguments();
        assert bundle != null;
        date_min = bundle.getString(getString(R.string.DATE_MIN_FF));
        date_max = bundle.getString(getString(R.string.DATE_MAX_FF));
        Keyword_search = bundle.getString(getString(R.string.KEYWORD_MX));
        progressBar = view.findViewById(R.id.progress);
        //PROGRESSBAR RUNNABLE
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (pStatus <= 100) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            progressBar.setProgress(pStatus);
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

        recyclerView = view.findViewById(R.id.search_results_recycler);
        mAdapter = new search_results_adapter(getActivity(), case_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        new LongOperation().execute("");
        return view;
    }

    private void ShowItemsNames() {



        Uri.Builder builder = new Uri.Builder();
        builder.scheme(getString(R.string.HTTP))
                .authority(getString(R.string.AUTHORITY))
                .appendPath("v1")
                .appendPath(getString(R.string.CASES_D))
                .appendPath("")
                .appendQueryParameter(getString(R.string.SEARCH_D), Keyword_search)
                .appendQueryParameter(getString(R.string.D_DATE_MIN), date_min)
                .appendQueryParameter(getString(R.string.D_DATE_MAX), date_max);

        String URL2 = builder.build().toString();
        RequestQueue Video_Queue = Volley.newRequestQueue(getContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL2, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    progressBar.setVisibility(View.INVISIBLE);

                    JSONObject jsonObject = new JSONObject(response);
                            JSONArray JSONARRAY = jsonObject.getJSONArray(getString(R.string.RESULTS_JS));
                            if (JSONARRAY.length()<= 0){

                                no_results_lbl.setVisibility(View.VISIBLE);

                            }
                            for(int k =0; k<20; k++){
                                JSONObject stepsobject = JSONARRAY.getJSONObject(k);
                                search_results_model VL = new search_results_model(stepsobject.getString(getString(R.string.NAME_AB)),stepsobject.getString(getString(R.string.DOCK_NUM)), stepsobject.getString(getString(R.string.DECISION_DATE)), stepsobject.getString("id"));
                                VL.setTitle(stepsobject.getString(getString(R.string.NAME_AB)));
                                VL.setJurisdiction(stepsobject.getString(getString(R.string.DOCK_NUM)));
                                VL.setDate(stepsobject.getString(getString(R.string.DECISION_DATE)));
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
                Toast.makeText(getContext(),getString(R.string.PLEASE_WAIT), Toast.LENGTH_LONG).show();
                new LongOperation().execute("");

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
        }

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected void onProgressUpdate(Void... values) {
        }
    }

    public void onButtonPressed(Uri uri) {
    }
}

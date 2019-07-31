package com.salam.capstoneprojectstage2.Search_query_UI;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.salam.capstoneprojectstage2.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class caseLaw_details_fragment extends Fragment {

        private TextView title, docket;
        private TextView citation_txt, juri_txt, court_txt, details_txt;
        private String caseid;
    private String citation;
    private String title_of;
        private ImageView fav;

        private DatabaseReference ratingdatabase;
        private FirebaseAuth auth;




    public caseLaw_details_fragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle=getArguments();
        assert bundle != null;
        caseid = bundle.getString("id");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_case_law_details_fragment, container, false);
        title = view.findViewById(R.id.title_txt);
        docket = view.findViewById(R.id.dock_number);
citation_txt = view.findViewById(R.id.citation_txt);
juri_txt = view.findViewById(R.id.juri_txt);
court_txt = view.findViewById(R.id.court_txt);
details_txt = view.findViewById(R.id.details_extra_txt);


        fav = view.findViewById(R.id.fav_button);
        auth = FirebaseAuth.getInstance();


        fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                title_of = title.getText().toString();
                FirebaseUser firebaseUser = auth.getCurrentUser();
                assert firebaseUser != null;
                final String userid = firebaseUser.getUid();
                ratingdatabase = FirebaseDatabase.getInstance().getReference("Fav_cases").child(userid).child(caseid);

                HashMap<String, String> hashrate = new HashMap<>();
                hashrate.put("caseid", caseid);
                hashrate.put("jurisdiction", juri_txt.getText().toString());
                hashrate.put("title", title_of);
                hashrate.put("court", court_txt.getText().toString());
                hashrate.put("cite", citation_txt.getText().toString());
                hashrate.put("details_extra", details_txt.getText().toString());
                ratingdatabase.setValue(hashrate);



            }
        });





         new LongOperation().execute("");
        return view;

    }

    public void onButtonPressed(Uri uri) {

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

        String URL = "https://api.case.law/v1/cases/"+caseid;





        RequestQueue Video_Queue = Volley.newRequestQueue(getContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);


                    JSONArray casebodyOBJ = jsonObject.getJSONArray("citations");
                    JSONObject volumeOBJ = jsonObject.getJSONObject("volume");
                    JSONObject courtOBJ = jsonObject.getJSONObject("court");
                    JSONObject juriOBJ = jsonObject.getJSONObject("jurisdiction");
                    title.setText(jsonObject.getString("name"));
                    //citation = casebodyOBJ.toString().replaceAll("[*]","");

                    citation = casebodyOBJ.getJSONObject(0).getString("type")+"  " +casebodyOBJ.getJSONObject(0).getString("cite");


                    citation_txt.setText(citation);
                    details_txt.setText(jsonObject.getString("name_abbreviation") + "\nDecision Date:" + jsonObject.getString("decision_date"));
                    court_txt.setText(courtOBJ.getString("name") +"(abr)" +courtOBJ.getString("name_abbreviation"));
                    juri_txt.setText(juriOBJ.getString("name") +" " + juriOBJ.getString("name_long") );


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
        }

        @Override
        protected void onPreExecute() {



        }

        @Override
        protected void onProgressUpdate(Void... values) {


        }
    }




}

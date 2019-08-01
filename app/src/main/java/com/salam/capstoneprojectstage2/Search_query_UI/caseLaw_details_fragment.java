package com.salam.capstoneprojectstage2.Search_query_UI;

import android.annotation.SuppressLint;
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
                ratingdatabase = FirebaseDatabase.getInstance().getReference(getString(R.string.FAV_CASE_DB_C)).child(userid).child(caseid);

                HashMap<String, String> hashrate = new HashMap<>();
                hashrate.put(getString(R.string.CASE_ID_CASE), caseid);
                hashrate.put(getString(R.string.JURI_C), juri_txt.getText().toString());
                hashrate.put(getString(R.string.TITLE_DB), title_of);
                hashrate.put(getString(R.string.COURT_D), court_txt.getText().toString());
                hashrate.put(getString(R.string.CITE_DB), citation_txt.getText().toString());
                hashrate.put(getString(R.string.EXTRA_DETAILS), details_txt.getText().toString());
                ratingdatabase.setValue(hashrate);

                Toast.makeText(getContext(),getString(R.string.ADDED_MSG), Toast.LENGTH_LONG).show();

            }
        });

         new LongOperation().execute("");
         return view;

    }

    public void onButtonPressed(Uri uri) {

    }

    private void ShowItemsNames() {

        Uri.Builder builder = new Uri.Builder();
        builder.scheme(getString(R.string.HTTP))
                .authority(getString(R.string.AUTHORITY))
                .appendPath("v1")
                .appendPath(getString(R.string.CASES_D))
               .appendPath(caseid);
        String URL2 = builder.build().toString();

        RequestQueue Video_Queue = Volley.newRequestQueue(getContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL2, new com.android.volley.Response.Listener<String>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray casebodyOBJ = jsonObject.getJSONArray(getString(R.string.CITATION_D));
                    JSONObject volumeOBJ = jsonObject.getJSONObject(getString(R.string.VOLUME_D));
                    JSONObject courtOBJ = jsonObject.getJSONObject(getString(R.string.COURT_JS));
                    JSONObject juriOBJ = jsonObject.getJSONObject(getString(R.string.JURI_JS));
                    title.setText(jsonObject.getString(getString(R.string.N_ABV_S)));
                    //citation = casebodyOBJ.toString().replaceAll("[*]","");
                    citation = casebodyOBJ.getJSONObject(0).getString(getString(R.string.TYPE_JS))+"  " +casebodyOBJ.getJSONObject(0).getString(getString(R.string.CITE_JS_P));
                    citation_txt.setText(citation);
                    details_txt.setText(jsonObject.getString(getString(R.string.N_ABV_S)) + "\nDecision Date:" + jsonObject.getString(getString(R.string.D_DATE_J_R)));
                    court_txt.setText(courtOBJ.getString(getString(R.string.NAME_TT)) +"(abr)" +courtOBJ.getString(getString(R.string.N_ABV_S)));
                    juri_txt.setText(juriOBJ.getString(getString(R.string.NAME_TT)) +" " + juriOBJ.getString(getString(R.string.NAME_LONG_D)) );
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

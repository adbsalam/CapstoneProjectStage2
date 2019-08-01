package com.salam.capstoneprojectstage2.Bookmarks;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.salam.capstoneprojectstage2.Adapter.fav_adapter;
import com.salam.capstoneprojectstage2.Models.search_results_model;
import com.salam.capstoneprojectstage2.R;

import java.util.ArrayList;
import java.util.List;

public class booking_list_fragment extends Fragment {

    private RecyclerView recyclerView;
    private List<search_results_model> case_list = new ArrayList<>();
    private fav_adapter mAdapter;
    FirebaseAuth auth;
    TextView booking_error;
    DatabaseReference reference;
    public booking_list_fragment() {
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
        View view =  inflater.inflate(R.layout.fragment_booking_list_fragment, container, false);
        auth = FirebaseAuth.getInstance();
        recyclerView = view.findViewById(R.id.fav_recycler);
        mAdapter = new fav_adapter(getActivity(), case_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        booking_error = view.findViewById(R.id.book_err_txt);
        new NewLong().execute("");
        return view;

    }
    private void readCases(){

        case_list.clear();
        reference = FirebaseDatabase.getInstance().getReference(getString(R.string.FAV_CASE)).child(auth.getCurrentUser().getUid());

        reference.orderByChild(getString(R.string.CASE_ID)).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot: dataSnapshot.getChildren()){
                    search_results_model casemode = snapshot.getValue(search_results_model.class);
                    case_list.add(casemode);
                    booking_error.setVisibility(View.INVISIBLE);
                }
                recyclerView.setAdapter(mAdapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private class NewLong extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            readCases();
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

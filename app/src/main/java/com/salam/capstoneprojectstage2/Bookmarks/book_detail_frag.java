package com.salam.capstoneprojectstage2.Bookmarks;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.salam.capstoneprojectstage2.Adapter.search_results_adapter;
import com.salam.capstoneprojectstage2.Models.fav_model_details;
import com.salam.capstoneprojectstage2.R;

import java.util.ArrayList;
import java.util.List;

public class book_detail_frag extends Fragment {
    private RecyclerView recyclerView;
    private List<fav_model_details> case_list = new ArrayList<>();
    private search_results_adapter mAdapter;

    DatabaseReference reference;
    FirebaseAuth auth;
    String caseid;

    private TextView title, docket;
    private TextView citation_txt, juri_txt, court_txt, details_txt;

    public book_detail_frag() {
        // Required empty public constructor
    }




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

            Bundle bundle=getArguments();
            assert bundle != null;
            caseid = bundle.getString("id");

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_book_detail_frag, container, false);
        title = view.findViewById(R.id.title_txt);
        docket = view.findViewById(R.id.dock_number);
        citation_txt = view.findViewById(R.id.citation_txt);
        juri_txt = view.findViewById(R.id.juri_txt);
        court_txt = view.findViewById(R.id.court_txt);
        details_txt = view.findViewById(R.id.details_extra_txt);

        auth = FirebaseAuth.getInstance();
        readCases();

        return view;

    }


    private void readCases(){

        reference = FirebaseDatabase.getInstance().getReference("Fav_cases").child(auth.getCurrentUser().getUid());

        reference.orderByChild("caseid").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot: dataSnapshot.getChildren()){

                    fav_model_details casemode = snapshot.getValue(fav_model_details.class);

                    if(casemode.getCaseid().equals(caseid)){

                        title.setText(casemode.getTitle());
                        //citation = casebodyOBJ.toString().replaceAll("[*]","");


                        citation_txt.setText(casemode.getCite());
                        details_txt.setText(casemode.getDetails_extra());
                        court_txt.setText(casemode.getCourt());
                        juri_txt.setText(casemode.getJurisdiction() );




                    }




                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }



}

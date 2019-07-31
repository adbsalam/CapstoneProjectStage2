package com.salam.capstoneprojectstage2.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.salam.capstoneprojectstage2.Models.user_details;
import com.salam.capstoneprojectstage2.R;

import java.util.List;




    public class nearbyAdapter extends RecyclerView.Adapter<nearbyAdapter.RecyclerViewHolder> {

        private List<user_details> user_list;
        private final Activity activity;

        public nearbyAdapter(Activity activity, List<user_details> user_list) {
            this.user_list = user_list;
            this.activity = activity;
        }

        @Override
        public nearbyAdapter.RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new nearbyAdapter.RecyclerViewHolder(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.nearby_layout_item, parent, false));
        }

        @Override
        public void onBindViewHolder(final nearbyAdapter.RecyclerViewHolder holder, int position) {
            final user_details user_listnew = user_list.get(position);
            holder.name.setText(user_listnew.getFirst_name());

            holder.itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                }
            });
        }

        @Override
        public int getItemCount() {
            return user_list.size();
        }

        public void addItems(List<user_details> user_list) {
            this.user_list = user_list;
            notifyDataSetChanged();
        }

        static class RecyclerViewHolder extends RecyclerView.ViewHolder {
            final TextView name;
            final ImageView near_dp;


            RecyclerViewHolder(View view) {
                super(view);
                name = view.findViewById(R.id.near_NAME);
                near_dp = view.findViewById(R.id.near_dp);



            }
        }
    }





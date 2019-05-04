package com.example.naveed.backup;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class CustomAdapter extends FirestoreRecyclerAdapter<ModelData, CustomAdapter.DataHolder> {


    public CustomAdapter(@NonNull FirestoreRecyclerOptions<ModelData> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull DataHolder holder, int position, @NonNull ModelData model) {

        holder.tvName.setText(model.getContactName());
        holder.tvNumber.setText(model.getNumber());
    }

    @NonNull
    @Override
    public DataHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_list, parent, false);

        return new DataHolder(v);
    }

    class DataHolder extends RecyclerView.ViewHolder{

        TextView tvName, tvNumber;

        public DataHolder(View itemView) {
            super(itemView);

//            tvName= itemView.findViewById(R.id.call_name);
//            tvNumber= itemView.findViewById(R.id.call_number);
        }
    }




}

package com.example.kollect;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.database.DataSnapshot;
import com.paypal.pyplcheckout.interfaces.SelectedListener;

import java.util.ArrayList;

public class favArtistAdapter extends RecyclerView.Adapter<favArtistAdapter.MyViewHolder>{
    private final RecyclerViewInterface2 recyclerViewInterface2;
    private ArrayList<String> arrayList;
    public favArtistAdapter(ArrayList<String> arrayList, RecyclerViewInterface2 recyclerViewInterface2){
        this.arrayList = arrayList;
        this.recyclerViewInterface2 = recyclerViewInterface2;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);

        View v = layoutInflater.inflate(R.layout.favorite_artist_row_item,parent,false);
        return new MyViewHolder(v,recyclerViewInterface2);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.artist_id.setText(arrayList.get(position).toString());
    }
    @Override
    public int getItemCount() {
        return arrayList.size();
    }
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public CardView cardView;
        public TextView artist_id;

        public MyViewHolder(@NonNull View itemView, RecyclerViewInterface2 recyclerViewInterface2) {
            super(itemView);
            artist_id = itemView.findViewById(R.id.fav_artist_id);
            cardView = itemView.findViewById(R.id.cardView_artist);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (recyclerViewInterface2 != null) {
                        int postion = getAdapterPosition();
                        if (postion != RecyclerView.NO_POSITION) {
                            recyclerViewInterface2.onItemClick2(postion);
                        }
                    }
                }
            })
            ;
        }
    }
}
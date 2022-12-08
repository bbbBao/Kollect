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

public class favGroupAdapter extends RecyclerView.Adapter<favGroupAdapter.MyViewHolder>{
    private final RecyclerViewInterface recyclerViewInterface;
    private ArrayList<String> arrayList;
    public favGroupAdapter(ArrayList<String> arrayList, RecyclerViewInterface recyclerViewInterface){
        this.arrayList = arrayList;
        this.recyclerViewInterface = recyclerViewInterface;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        
        View v = layoutInflater.inflate(R.layout.favorite_group_row_item,parent,false);
        return new MyViewHolder(v,recyclerViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.group_id.setText(arrayList.get(position).toString());
    }
    @Override
    public int getItemCount() {
        return arrayList.size();
    }
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public CardView cardView;
        public TextView group_id;

        public MyViewHolder(@NonNull View itemView, RecyclerViewInterface recyclerViewInterface) {
            super(itemView);
            group_id = itemView.findViewById(R.id.fav_group_id);
            cardView = itemView.findViewById(R.id.cardView_group);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (recyclerViewInterface != null) {
                        int postion = getAdapterPosition();
                        if (postion != RecyclerView.NO_POSITION) {
                                recyclerViewInterface.onItemClick1(postion);
                        }
                    }
                }
            });
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (recyclerViewInterface != null) {
                        int postion = getAdapterPosition();
                        if (postion != RecyclerView.NO_POSITION) {
                            recyclerViewInterface.onItemLongClick1(postion);
                        }
                    }
                    return true;
                }
            });
        }
    }
}

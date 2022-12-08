package com.example.kollect;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import java.util.ArrayList;

public class CustomAdapterPosts extends BaseAdapter {
    private Context context;
    private ArrayList<Post> posts;

    public CustomAdapterPosts(Context context, ArrayList<Post> posts) {

        this.context = context;
        this.posts = posts;
    }

    @Override
    public int getCount() {
        return posts.size();
    }

    @Override
    public Object getItem(int i) {
        return posts.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.data_item_posts, null, true);

            holder.tvSellername = (TextView) convertView.findViewById(R.id.seller_name);
            holder.tvArtistname = (TextView) convertView.findViewById(R.id.artist_name);
            holder.tvgroups = (TextView) convertView.findViewById(R.id.groups);
            holder.tvprice= (TextView) convertView.findViewById(R.id.price);

            holder.tvstatus = (TextView) convertView.findViewById(R.id.status);
            holder.tvUserID = (TextView) convertView.findViewById(R.id.userID);
            holder.tvimagedata = (ImageView) convertView.findViewById(R.id.imageUpload);
            holder.tvSwitch = (Switch) convertView.findViewById(R.id.staSwt);



            convertView.setTag(holder);
        }else {
            // the getTag returns the viewHolder object set as a tag to the view
            holder = (ViewHolder)convertView.getTag();
        }

        holder.tvSellername.setText("Seller Name: "+ posts.get(position).getSellerName());
        holder.tvArtistname.setText("Artist Name: "+ posts.get(position).getArtistName());
        holder.tvgroups.setText("Artist Groups: "+ posts.get(position).getGroups());
        holder.tvprice.setText("Price: "+ posts.get(position).getPrice());
        holder.tvstatus.setText("Status: "+ posts.get(position).getStatus());
        holder.tvUserID.setText("User_ID: " + posts.get(position).getUserID());
        byte[] pic = (posts.get(position).getImages());
        Bitmap b = BitmapFactory.decodeByteArray(pic, 0, pic.length);
        holder.tvimagedata.setImageBitmap(b);

        if (posts.get(position).getStatus() == 1) {
            holder.tvSwitch.setChecked(true);
        }else {
            holder.tvSwitch.setChecked(false);
        }

        holder.tvSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.tvSwitch.isChecked()) {
                    holder.tvstatus.setText("Status: 1");
                    posts.get(position).setStatus(1);
                }else {
                    holder.tvstatus.setText("Status: 0");
                    posts.get(position).setStatus(0);
                }
            }
        });


        return convertView;
    }

    private class ViewHolder {

        protected TextView tvSellername, tvArtistname,tvgroups, tvprice, tvstatus, tvUserID;
        protected ImageView tvimagedata;
        protected Switch tvSwitch;
    }
}

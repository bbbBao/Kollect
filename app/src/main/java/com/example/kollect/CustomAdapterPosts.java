package com.example.kollect;

import android.app.Activity;
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
import android.widget.Toast;

import com.ablanco.zoomy.TapListener;
import com.ablanco.zoomy.Zoomy;

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

            convertView.setTag(holder);
        }else {
            // the getTag returns the viewHolder object set as a tag to the view
            holder = (ViewHolder)convertView.getTag();
        }

        holder.tvSellername.setText("Seller: "+ posts.get(position).getSellerName());
        holder.tvArtistname.setText("Artist: "+ posts.get(position).getArtistName());
        holder.tvgroups.setText("Groups: "+ posts.get(position).getGroups());
        holder.tvprice.setText("Price: "+ posts.get(position).getPrice());
        if (posts.get(position).getStatus() == 0){
            holder.tvstatus.setText("Completed");
        }else{
            holder.tvstatus.setText("Not Completed");
        }
        holder.tvUserID.setText("User ID: " + posts.get(position).getUserID());
        byte[] pic = (posts.get(position).getImages());
        Bitmap b = BitmapFactory.decodeByteArray(pic, 0, pic.length);
        holder.tvimagedata.setImageBitmap(b);

        Zoomy.Builder builder = new Zoomy.Builder((Activity) context)
                .target(holder.tvimagedata)
                .animateZooming(false)
                .enableImmersiveMode(false)
                .tapListener(new TapListener() {
                    @Override
                    public void onTap(View v) {
                        Toast.makeText(context, "Clicked", Toast.LENGTH_SHORT).show();
                    }
                });
        builder.register();







        return convertView;
    }

    private class ViewHolder {

        protected TextView tvSellername, tvArtistname,tvgroups, tvprice, tvstatus, tvUserID;
        protected ImageView tvimagedata;

    }
}

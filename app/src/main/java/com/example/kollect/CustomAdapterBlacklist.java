package com.example.kollect;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ablanco.zoomy.TapListener;
import com.ablanco.zoomy.Zoomy;

import java.util.ArrayList;

public class CustomAdapterBlacklist extends BaseAdapter {
    private Context context;
    private ArrayList<Blacklist> blacklists;

    public CustomAdapterBlacklist(Context context, ArrayList<Blacklist> blacklists) {

        this.context = context;
        this.blacklists = blacklists;
    }

    @Override
    public int getCount() {
        return blacklists.size();
    }

    @Override
    public Object getItem(int i) {
        return blacklists.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CustomAdapterBlacklist.ViewHolder holder;

        if (convertView == null) {
            holder = new CustomAdapterBlacklist.ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.data_item_blacklists, null, true);

            holder.tvInstagramID = (TextView) convertView.findViewById(R.id.user_name);
            holder.tvPaypalID = (TextView) convertView.findViewById(R.id.paypalID);
            holder.tvReportNum = (TextView) convertView.findViewById(R.id.reportNum);
            holder.tvProofImg = (ImageView) convertView.findViewById(R.id.imageUpload);


            convertView.setTag(holder);
        }else {
            // the getTag returns the viewHolder object set as a tag to the view
            holder = (CustomAdapterBlacklist.ViewHolder)convertView.getTag();
        }

        holder.tvInstagramID.setText("Instagram ID: "+ blacklists.get(position).getInstagramID());
        holder.tvPaypalID.setText("Paypal ID: "+ blacklists.get(position).getPaypalID());
        holder.tvReportNum.setText("Number of reports: "+ blacklists.get(position).getReportNum());
        byte[] pic = (blacklists.get(position).getImages());
        Bitmap b = BitmapFactory.decodeByteArray(pic, 0, pic.length);
        holder.tvProofImg.setImageBitmap(b);

        Zoomy.Builder builder = new Zoomy.Builder((Activity) context)
                .target(holder.tvProofImg)
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

        protected TextView tvInstagramID, tvPaypalID, tvReportNum;
        protected ImageView tvProofImg;
    }

}
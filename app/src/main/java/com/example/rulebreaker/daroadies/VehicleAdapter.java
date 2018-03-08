package com.example.rulebreaker.daroadies;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by rulebreaker on 23/2/18.
 */

public class VehicleAdapter extends RecyclerView.Adapter<VehicleAdapter.ViewHolder> {
    public ArrayList<String> bikeName=new ArrayList<>();
    public ArrayList<String> posterPath=new ArrayList<>();
    public ArrayList<Integer> bikeId=new ArrayList<>();
    public ArrayList<Integer> rentPrice=new ArrayList<>();
    public ArrayList<Boolean> available=new ArrayList<>();
    private final LayoutInflater mLayoutInflater;

    public VehicleAdapter(Context context, ArrayList<String> bikeName, ArrayList<String> posterPath, ArrayList<Integer> bikeId, ArrayList<Integer> rentPrice, ArrayList<Boolean> available) {
        this.bikeName=bikeName;
        this.bikeId=bikeId;
        this.rentPrice=rentPrice;
        this.posterPath=posterPath;
        this.available=available;
        mLayoutInflater=LayoutInflater.from(context);
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.vehicle_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.mbikeId.setText(Integer.toString(bikeId.get(position)));
        holder.mbikePrice.setText("Rs "+Integer.toString(rentPrice.get(position))+"/km (Re 0.5/km Extra per hour)");
        holder.mbikeName.setText(bikeName.get(position));
        if(available.get(position)==true)
        {
            holder.mbikeInfo.setText("Rent a Ride");
            holder.mbikePrice.setText("Rs "+Integer.toString(rentPrice.get(position))+"/km (Re 0.5/km Extra per hour)");}
        else
        {
            holder.mbikeInfo.setText("Coming Soon...");
            holder.mbikePrice.setText("Prices to be revealed soon");
        }
        Picasso.with(holder.mbikeImage.getContext()).load(posterPath.get(position)).fit().into(holder.mbikeImage);
        holder.itemView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if (available.get(position) == true) {
                    Intent intent = new Intent(holder.itemView.getContext(), SecondaryPage.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("BikeName", bikeName.get(position));
                    bundle.putInt("RentPrice", rentPrice.get(position));
                    intent.putExtras(bundle);
                    holder.itemView.getContext().startActivity(intent);
                }
                else
                    Toast.makeText(holder.itemView.getContext(),"Ride Coming Soon...",Toast.LENGTH_LONG).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return bikeName.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.bikeId)
        TextView mbikeId;
        @BindView(R.id.bikeImage)
        ImageView mbikeImage;
        @BindView(R.id.bikeName)
        TextView mbikeName;
        @BindView(R.id.bikePrice)
        TextView mbikePrice;
        @BindView(R.id.bikeInfo)
        TextView mbikeInfo;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}

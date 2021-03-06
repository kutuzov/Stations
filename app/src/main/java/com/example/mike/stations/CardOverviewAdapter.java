package com.example.mike.stations;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by prolomov on 12.09.2016.
 */
public class CardOverviewAdapter extends RecyclerView.Adapter<CardOverviewAdapter.ViewHolder> implements View.OnClickListener {

    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    public static class ViewHolder extends RecyclerView.ViewHolder {

        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public TextView tvName;
        public LinearLayout cvCard;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView) {
            super(itemView);
            this.tvName = (TextView) itemView.findViewById(R.id.tvName);
            this.cvCard = (LinearLayout) itemView.findViewById(R.id.card);
        }
    }

    private ArrayList<Response.Stations> cards;
    // Store the context for later use
    private Context context;

    // Pass in the context and cards array into the constructor
    public CardOverviewAdapter(Context context, ArrayList<Response.Stations> cards) {
        this.cards = cards;
        this.context = context;
    }

    @Override
    public CardOverviewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Inflate the custom layout
        final View itemView = LayoutInflater.from(context).inflate(R.layout.card, parent, false);
        itemView.setOnClickListener(this);
        // Return a new holder instance
        return new CardOverviewAdapter.ViewHolder(itemView);
    }

    // Involves populating data into the item through holder


    @Override
    public void onBindViewHolder(CardOverviewAdapter.ViewHolder holder, final int position) {
        // Get the data model based on position
        Response.Stations card = cards.get(position);
        // Set item views based on the data model
        String countryAndCity = card.countryTitle + ", " + card.cityTitle;

        if ((card.stationTitle.equals("")) && (card.stationId == 0)) {
            holder.tvName.setText(countryAndCity);
            holder.tvName.setTextSize(16);
            holder.cvCard.setBackgroundColor(ContextCompat.getColor(context, R.color.background_material_light));

        } else {

            holder.tvName.setText(card.stationTitle);

            holder.cvCard.setBackgroundColor(Color.parseColor("#BBDEFB"));

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    StationsApplication stApp = (StationsApplication) context.getApplicationContext();
                    stApp.setStationCard(position);
                    Intent intent = new Intent(context, ShowStationActivity.class);
                    context.startActivity(intent);
                }
            });
        }
    }

    // Return the total count of items
    @Override
    public int getItemCount() {
        return cards.size();

    }

    @Override
    public void onClick(View v) {
        Activity activity = (Activity) context;
    }


}



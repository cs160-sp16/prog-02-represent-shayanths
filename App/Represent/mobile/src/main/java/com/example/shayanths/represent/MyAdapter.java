package com.example.shayanths.represent;

/**
 * Created by ShayanthS on 3/1/16.
 */
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.InputStream;
import java.util.ArrayList;


public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    private ArrayList<PersonData> peopleDataSet;
    private ProgressDialog progressBar;

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView name;
        TextView email;
        TextView website;
        TextView twitterQuote;
        ImageView imageViewIcon;
        CardView cardLayout;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.name = (TextView) itemView.findViewById(R.id.name);
            this.email = (TextView) itemView.findViewById(R.id.email);
            this.website = (TextView) itemView.findViewById(R.id.website);
            this.twitterQuote = (TextView) itemView.findViewById(R.id.twitter_quote);
            this.imageViewIcon = (ImageView) itemView.findViewById(R.id.candidate_image);
            this.cardLayout = (CardView) itemView.findViewById(R.id.card_view);
        }
    }

    public MyAdapter(ArrayList<PersonData> people, ProgressDialog progress) {
        this.peopleDataSet = people;
        this.progressBar = progress;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent,
                                           int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cards_layout, parent, false);

        view.setOnClickListener(CandidateView.myOnClickListener);

        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int listPosition) {

        TextView textViewName = holder.name;
        TextView textViewEmail = holder.email;
        TextView textViewWebsite = holder.website;
        TextView textViewTwitter = holder.twitterQuote;
        ImageView imageView = holder.imageViewIcon;
        CardView cardLayout = holder.cardLayout;

        String name = peopleDataSet.get(listPosition).getName();
        String party = peopleDataSet.get(listPosition).getParty();
        String email = peopleDataSet.get(listPosition).getEmail();
        String website = peopleDataSet.get(listPosition).getWebsite();
        String twitterQuote = peopleDataSet.get(listPosition).getTwitterQuote();

        textViewName.setText(name);
        textViewEmail.setText(email);
        textViewWebsite.setText(website);
        textViewTwitter.setText(twitterQuote);

        //TODO: Change this back to integer.




        new DownloadImageTask((ImageView) imageView)
                .execute(peopleDataSet.get(listPosition).getImage());

//        imageView.setImageResource(peopleDataSet.get(listPosition).getImage());

        if(party.equals("Republican")){
            cardLayout.setBackgroundColor(Color.parseColor("#DC143C"));
            cardLayout.setUseCompatPadding(true);
            cardLayout.setCardElevation(5);
        }
        else{
            cardLayout.setBackgroundColor(Color.parseColor("#1E90FF"));
            cardLayout.setUseCompatPadding(true);
            cardLayout.setCardElevation(5);

        }
    }

    @Override
    public int getItemCount() {
        return peopleDataSet.size();
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }
}
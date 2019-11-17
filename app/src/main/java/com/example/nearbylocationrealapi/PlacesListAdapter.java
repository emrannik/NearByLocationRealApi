package com.example.nearbylocationrealapi;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.InputStream;
import java.util.List;

public class PlacesListAdapter extends ArrayAdapter<Result> {

    private Context context;
    private List<Result> results;

    public PlacesListAdapter(Context context, List<Result> results) {
        super(context, R.layout.place_row, results);
        this.context = context;
        this.results = results;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View view, @NonNull ViewGroup parent) {
        try {
            ViewHolder viewHolder;
            if(view == null) {
                viewHolder = new ViewHolder();
                view = LayoutInflater.from(context).inflate(R.layout.place_row,null);
                viewHolder.textViewName = view.findViewById(R.id.textViewName);
                viewHolder.textViewAddress = view.findViewById(R.id.textViewAddress);
                viewHolder.imageViewPhoto = view.findViewById(R.id.imageViewPhoto);
                view.setTag(viewHolder);
            } else  {
                viewHolder = (ViewHolder) view.getTag();
            }
            Result result = results.get(position);
            viewHolder.textViewName.setText(result.getName());
            viewHolder.textViewAddress.setText(result.getVicinity());
            Bitmap photo = new ImageRequestAsk().execute(result.getIcon()).get();
            viewHolder.imageViewPhoto.setImageBitmap(photo);
            return view;
        } catch (Exception e) {
            return null;
        }
    }

    public static class ViewHolder {
        public TextView textViewName;
        public TextView textViewAddress;
        public ImageView imageViewPhoto;
    }

    private class ImageRequestAsk extends AsyncTask<String, Void, Bitmap> {

        @Override
        protected Bitmap doInBackground(String... params) {
            try {
                InputStream inputStream = new java.net.URL(params[0]).openStream();
                return BitmapFactory.decodeStream(inputStream);
            } catch (Exception e) {
                return null;
            }
        }

    }

}
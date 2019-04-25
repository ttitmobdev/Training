package com.example.user.training;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {
    private List<JSONResponse> responses;

    public Adapter(List<JSONResponse> responses) {
        this.responses = responses;
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.post,viewGroup,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.text.setText(responses.get(i).getText());
        SimpleDateFormat frm = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        try {
            String time = responses.get(i).getTime();
            Date date = frm.parse(time);
            viewHolder.date.setText(date.toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (responses.get(i).getImage()!=null){
            Picasso.get().load(responses.get(i).getImage()).into(viewHolder.imageView);
        }
    }

    @Override
    public int getItemCount() {
        return responses.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView text;
        TextView date;
        ImageView imageView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            text = itemView.findViewById(R.id.textId);
            date = itemView.findViewById(R.id.dateId);
            imageView = itemView.findViewById(R.id.imageId);
        }
    }
}

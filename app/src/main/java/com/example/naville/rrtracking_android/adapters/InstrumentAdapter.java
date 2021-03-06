package com.example.naville.rrtracking_android.adapters;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.example.naville.rrtracking_android.MainActivity;
import com.example.naville.rrtracking_android.R;
import com.example.naville.rrtracking_android.model.Image;
import com.example.naville.rrtracking_android.model.Instrument;
import com.example.naville.rrtracking_android.util.Constants;
import com.example.naville.rrtracking_android.util.CustomAlertDialog;
import com.example.naville.rrtracking_android.util.ImageUtil;
import com.github.chrisbanes.photoview.PhotoView;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class InstrumentAdapter extends RecyclerView.Adapter<InstrumentAdapter.InstrumentViewHolder>{

    private List<String> imagensList;

    public InstrumentAdapter(List<String> imagensList) {
        this.imagensList = imagensList;
    }

    @NonNull
    @Override
    public InstrumentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_image, parent, false);

        return new InstrumentAdapter.InstrumentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull InstrumentViewHolder holder, int position) {
        String imageUrl = imagensList.get(position);
        holder.bind(imageUrl);
    }

    @Override
    public int getItemCount() {

        return (imagensList != null && imagensList.size() > 0) ? imagensList.size() : 0;
    }

    class InstrumentViewHolder extends RecyclerView.ViewHolder{

        private ImageView imageView;

        InstrumentViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image);
        }

        public void bind(String imageUrl){
            ImageUtil.loadImage(Constants.URL_IMAGE+"/"+imageUrl, imageView, null, R.drawable.logo);

            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    CustomAlertDialog.customDialogImageZoom((Activity) v.getContext(), imageUrl);

                }
            });

        }

    }
    public void setImagensList(List<String> imagensListInst){
        this.imagensList = imagensListInst;
        notifyDataSetChanged();
    }
}
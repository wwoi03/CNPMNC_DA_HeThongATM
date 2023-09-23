package com.example.app_cnpmnc_da_hethongatm.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app_cnpmnc_da_hethongatm.Model.ImageSlide;
import com.example.app_cnpmnc_da_hethongatm.R;

import java.util.ArrayList;

public class ImageSlideAdapter extends RecyclerView.Adapter<ImageSlideAdapter.ImageSlideVH> {

    private ArrayList<ImageSlide> imageSlides;

    public ImageSlideAdapter(ArrayList<ImageSlide> imageSlides) {
        this.imageSlides = imageSlides;
    }

    @NonNull
    @Override
    public ImageSlideVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_image_slide, parent, false);
        return new ImageSlideVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageSlideVH holder, int position) {
        ImageSlide imageSlide = imageSlides.get(position);

        if (imageSlide == null)
            return;
        holder.ivImageSlide.setImageResource(imageSlide.getResourceId());
    }

    @Override
    public int getItemCount() {
        if (imageSlides != null)
            return imageSlides.size();
        return 0;
    }

    public class ImageSlideVH extends RecyclerView.ViewHolder {
        private ImageView ivImageSlide;

        public ImageSlideVH(@NonNull View itemView) {
            super(itemView);

            ivImageSlide = itemView.findViewById(R.id.ivImageSlide);
        }
    }
}

package com.task.ipt.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.task.ipt.R;
import com.task.ipt.databinding.RowPhotoListBinding;
import com.task.ipt.model.PhotosModel;

import java.util.List;

/**
 * Created by Umesh on 14/04/22.
 */

public class CompareListingAdapter extends RecyclerView.Adapter<CompareListingAdapter.PhotoListViewHolder> {

    private List<PhotosModel> modelList;
    private CertificateClickListener listener;

    public CompareListingAdapter(List<PhotosModel> modelList, CertificateClickListener listener) {
        this.modelList = modelList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public PhotoListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_compare_list, parent, false);
        return new PhotoListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PhotoListViewHolder holder, int position) {
        try {
            PhotosModel model = modelList.get(position);
            holder.onBind(model);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }

    public class PhotoListViewHolder extends RecyclerView.ViewHolder {
        RowPhotoListBinding rBinding;

        public PhotoListViewHolder(@NonNull View itemView) {
            super(itemView);
            rBinding = RowPhotoListBinding.bind(itemView);
        }

        public void onBind(PhotosModel photosModel) {

            rBinding.txtId.setText(String.valueOf(photosModel.getId()));
            rBinding.txtTitle.setText(String.valueOf(photosModel.getUrl()));
            rBinding.txtUrl.setText(String.valueOf(photosModel.getUrl()));


            try {
                Glide.with(itemView)
                        .load(photosModel.getThumbnailUrl())
                        .fitCenter()
                        .placeholder(R.drawable.ic_launcher_background)
                        .into(rBinding.imgImage);
            } catch (Exception e) {
                e.printStackTrace();
            }

            rBinding.cardView.setOnClickListener(v -> {
                listener.CompareList(photosModel.getThumbnailUrl(), photosModel.getId(), photosModel.getUrl(), photosModel.getTitle());
            });
        }
    }

    public interface CertificateClickListener {
        void CompareList(String ImageUrl, int Id, String Url, String title);
    }
}

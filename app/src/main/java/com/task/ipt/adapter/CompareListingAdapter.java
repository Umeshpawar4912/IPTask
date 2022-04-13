package com.task.ipt.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.task.ipt.R;
import com.task.ipt.databinding.RowCompareListBinding;
import com.task.ipt.databinding.RowPhotoListBinding;
import com.task.ipt.model.PhotosModel;
import com.task.ipt.view.MainActivity;

import java.util.List;

/**
 * Created by Umesh on 14/04/22.
 */

public class CompareListingAdapter extends RecyclerView.Adapter<CompareListingAdapter.PhotoListViewHolder> {

    private List<PhotosModel> modelList;
    private ClickListener listener;

    public CompareListingAdapter(List<PhotosModel> modelList, ClickListener listener) {
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
        RowCompareListBinding rBinding;

        public PhotoListViewHolder(@NonNull View itemView) {
            super(itemView);
            rBinding = RowCompareListBinding.bind(itemView);
        }

        public void onBind(PhotosModel photosModel) {

            rBinding.txtId.setText(String.valueOf(photosModel.getId()));
            rBinding.txtTitle.setText(String.valueOf(photosModel.getTitle()));
            rBinding.txtUrl.setText(String.valueOf(photosModel.getUrl()));


            try {
                Glide.with(itemView.getContext())
                        .load(photosModel.getUrl())
                        .fitCenter()
                        .placeholder(R.drawable.ic_launcher_background)
                        .into(rBinding.imgImage);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    public interface ClickListener {

    }
}

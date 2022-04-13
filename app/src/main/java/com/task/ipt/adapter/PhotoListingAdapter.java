package com.task.ipt.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.task.ipt.R;
import com.task.ipt.databinding.RowPhotoListBinding;
import com.task.ipt.model.PhotosModel;
import com.task.ipt.view.MainActivity;

import java.util.List;

/**
 * Created by Umesh on 14/04/22.
 */

public class PhotoListingAdapter extends RecyclerView.Adapter<PhotoListingAdapter.PhotoListViewHolder> {

    private List<PhotosModel> modelList;
    private ClickListener listener;


    public PhotoListingAdapter(List<PhotosModel> photosModels, ClickListener listener) {
        this.modelList = photosModels;
        this.listener = listener;
    }

    @NonNull
    @Override
    public PhotoListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_photo_list, parent, false);
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

        if (modelList.size() > 10) {
            return 10;
        } else {
            return modelList.size();
        }
        // return modelList.size();
    }

    public class PhotoListViewHolder extends RecyclerView.ViewHolder {
        RowPhotoListBinding rBinding;

        public PhotoListViewHolder(@NonNull View itemView) {
            super(itemView);
            rBinding = RowPhotoListBinding.bind(itemView);
        }

        public void onBind(PhotosModel photosModel) {

            rBinding.txtId.setText(String.valueOf(photosModel.getId()));
            rBinding.txtTitle.setText(photosModel.getTitle());
            rBinding.txtUrl.setText(photosModel.getUrl());
            String Img_Url =photosModel.getUrl();


           /* try {
                Glide.with(itemView.getContext())
                        .load(photosModel.getUrl())
                        .fitCenter()
                        .placeholder(R.drawable.ic_launcher_background)
                        .into(rBinding.imgImage);
            } catch (Exception e) {
                e.printStackTrace();
            }


*/


            RequestOptions options = new RequestOptions()
                    .centerCrop()
                    .placeholder(R.mipmap.ic_launcher_round)
                    .error(R.mipmap.ic_launcher_round);



            Glide.with(itemView).load(Img_Url).apply(options).into(rBinding.imgImage);


            rBinding.btnCompare.setOnClickListener(v -> {
                if (rBinding.btnCompare.getText().equals("Compare")){
                    listener.CompareList( photosModel.getThumbnailUrl(), photosModel.getId(), photosModel.getUrl(), photosModel.getTitle());
                    rBinding.btnCompare.setText("Remove");
                }else   if (rBinding.btnCompare.getText().equals("Remove")){
                    listener.RemoveItem( photosModel.getThumbnailUrl(), photosModel.getId(), photosModel.getUrl(), photosModel.getTitle());
                    rBinding.btnCompare.setText("Compare");

                }

            });
        }
    }

    public interface ClickListener {
        void CompareList( String ImageUrl, int Id, String Url, String title);
        void RemoveItem( String ImageUrl, int Id, String Url, String title);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

}

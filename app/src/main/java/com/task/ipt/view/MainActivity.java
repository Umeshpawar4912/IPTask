package com.task.ipt.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.DefaultItemAnimator;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.task.ipt.R;
import com.task.ipt.adapter.PhotoListingAdapter;
import com.task.ipt.databinding.ActivityMainBinding;
import com.task.ipt.model.PhotosModel;
import com.task.ipt.utils.ConnectionDetector;
import com.task.ipt.viewModel.PhotoListViewModel;

import java.util.List;

public class MainActivity extends AppCompatActivity implements PhotoListViewModel.PhotoListViewModelListener,
        PhotoListingAdapter.ClickListener {


    private List<PhotosModel> CopareList;
    private PhotoListViewModel photoListViewModel;
    private ActivityMainBinding binding;
    private PhotoListingAdapter adapter;
    private ConnectionDetector connectionDetector;
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        connectionDetector = new ConnectionDetector(this);
        photoListViewModel = new PhotoListViewModel(this);
        init();

    }

    private void init() {
        progressDialog = ProgressDialog.show(this, null, null, false, false);
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        progressDialog.setContentView(R.layout.layout_progress);
        progressDialog.dismiss();
        connectionDetector = new ConnectionDetector(this);


        if (connectionDetector.isConnectingToInternet()) {
            photoListViewModel.getPhotoList().observe(this, new Observer<List<PhotosModel>>() {

                @Override
                public void onChanged(List<PhotosModel> photosModels) {

                    adapter = new PhotoListingAdapter(photosModels, MainActivity.this);
                    binding.rvPhotolist.setItemAnimator(new DefaultItemAnimator());
                    binding.rvPhotolist.setAdapter(adapter);

                }

            });
        } else {
            Toast.makeText(this, "Please check internet connection", Toast.LENGTH_SHORT).show();
        }


    }

    @Override
    public void onProgress(boolean isLoading) {
        if (isLoading) {
            progressDialog.show();
        } else {
            try {
                progressDialog.dismiss();
            } catch (Exception e) {

            }
        }

    }

    @Override
    public void onSuccessful(String message, boolean status) {

    }

    @Override
    public void CompareList(String ImageUrl, int Id, String Url, String title) {

    }
}
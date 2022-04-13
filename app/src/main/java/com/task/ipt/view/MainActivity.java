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
import com.task.ipt.adapter.CompareListingAdapter;
import com.task.ipt.adapter.PhotoListingAdapter;
import com.task.ipt.databinding.ActivityMainBinding;
import com.task.ipt.model.PhotosModel;
import com.task.ipt.utils.ConnectionDetector;
import com.task.ipt.viewModel.PhotoListViewModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements PhotoListViewModel.PhotoListViewModelListener,
        PhotoListingAdapter.ClickListener, CompareListingAdapter.ClickListener {


    private List<PhotosModel> CompareList;
    private PhotoListViewModel photoListViewModel;
    private ActivityMainBinding binding;
    private PhotoListingAdapter adapter;
    private CompareListingAdapter Cadapter;
    private ConnectionDetector connectionDetector;
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        connectionDetector = new ConnectionDetector(this);
        photoListViewModel = new PhotoListViewModel(this);
        CompareList = new ArrayList<>();
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

        PhotosModel photosModel = new PhotosModel(Id, title, Url, ImageUrl);
        CompareList.add(0, photosModel);
        Showlist();

    }

    @Override
    public void RemoveItem(String ImageUrl, int Id, String Url, String title) {

        if (CompareList.size() > 0) {
            boolean flag = false;
            PhotosModel photosModel = new PhotosModel(Id, title, Url, ImageUrl);

            try {
                for (int i = 0; i < CompareList.size(); i++) {

                    int ID = CompareList.get(i).getId();
                    if (ID == Id) {
                        CompareList.remove(i);
                        Cadapter.notifyDataSetChanged();
                        break;
                    }
                }

            } catch (Exception e) {
            }

        }
    }

    private void Showlist() {

        Cadapter = new CompareListingAdapter(CompareList, MainActivity.this);
        binding.rvCompareList.setItemAnimator(new DefaultItemAnimator());
        binding.rvCompareList.setAdapter(Cadapter);
        //Cadapter.notifyDataSetChanged();
    }


}
package com.task.ipt.viewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.task.ipt.Network.RetrofitClient;
import com.task.ipt.model.PhotosModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PhotoListViewModel extends ViewModel {


    private MutableLiveData<List<PhotosModel>> photosModelMutableLiveData;
    private PhotoListViewModelListener listener;

    public PhotoListViewModel(PhotoListViewModelListener listener) {
        this.listener = listener;
    }


    public MutableLiveData<List<PhotosModel>> getPhotoList() {
        if (photosModelMutableLiveData == null)
            photosModelMutableLiveData = new MutableLiveData<>();
        getList();
        return photosModelMutableLiveData;
    }

    private void getList() {
        listener.onProgress(true);
        Call<List<PhotosModel>> call = RetrofitClient.getInstance().getApi()
                .getphotlist();

        call.enqueue(new Callback<List<PhotosModel>>() {
            @Override
            public void onResponse(Call<List<PhotosModel>> call, Response<List<PhotosModel>> response) {
                listener.onProgress(false);
                if (response.isSuccessful()){
                    photosModelMutableLiveData.setValue(response.body());

                }
            }

            @Override
            public void onFailure(Call<List<PhotosModel>> call, Throwable t) {
                listener.onProgress(false);
            }
        });
    }

    public interface PhotoListViewModelListener {

        void onProgress(boolean isLoading);

        void onSuccessful(String message, boolean status);

    }
}

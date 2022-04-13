package com.task.ipt.Network;


import com.task.ipt.model.PhotosModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Umesh on 13/4/22.
 */

public interface Api {

    @GET("photos")
    Call<List<PhotosModel>> getphotlist();


}

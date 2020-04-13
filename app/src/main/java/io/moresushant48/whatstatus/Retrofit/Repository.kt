package io.moresushant48.whatstatus.Retrofit

import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface Repository {

    @Multipart
    @POST("upload")
    fun sendImage(@Part file: MultipartBody.Part): Call<Boolean>

}
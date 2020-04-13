package io.moresushant48.whatstatus.Retrofit

import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

class Config {

    companion object {

        fun getRetrofit(): Retrofit {

            return Retrofit.Builder()
                .baseUrl("https://wastatus.herokuapp.com/")
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
                .build()
        }
    }

}
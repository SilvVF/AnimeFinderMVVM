package com.example.animemvvm.api


import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitInstance {
    companion object {
        private val retrofit by lazy {
            Retrofit.Builder()
                .baseUrl("https://api.jikan.moe/v3/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

        val api by lazy {
          retrofit.create(TopAnimeApi::class.java)
        }
    }
}


//class RetrofitInstance {
//    companion object {
//        private val retrofit by lazy {
//            val logging = HttpLoggingInterceptor()
//            logging.setLevel(HttpLoggingInterceptor.Level.BODY)
//            val client = OkHttpClient.Builder()
//                .addInterceptor(logging)
//                .build()
//            Retrofit.Builder()
//                .baseUrl()
//                .addConverterFactory(GsonConverterFactory.create())
//                .client(client)
//                .build()
//        }
//
//        val api by lazy {
//            retrofit.create(TopAnimeApi::class.java)
//        }
//    }
//}
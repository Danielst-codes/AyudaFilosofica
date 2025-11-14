package com.example.ayudafilosofica.data.remote

import com.example.ayudafilosofica.BuildConfig
import com.example.ayudafilosofica.data.remote.openai.api.OpenAIApi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RetrofitModule {

    private val logging = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val okHttp = OkHttpClient.Builder()
        .addInterceptor(logging)
        // ⬇️ Interceptor que añade la API key de OpenAI
        .addInterceptor { chain ->
            val original = chain.request()
            val request = original.newBuilder()
                .addHeader("Authorization", "Bearer ${BuildConfig.OPENAI_API_KEY}")
                .addHeader("Content-Type", "application/json")
                .build()
            chain.proceed(request)
        }

        .connectTimeout(20, TimeUnit.SECONDS)
        .readTimeout(60, TimeUnit.SECONDS)
        .writeTimeout(60, TimeUnit.SECONDS)
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.openai.com/")   // 👈 OpenAI, no Gemini
        .client(okHttp)                       // 👈 usar el cliente con el interceptor
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val api: OpenAIApi = retrofit.create(OpenAIApi::class.java)
}

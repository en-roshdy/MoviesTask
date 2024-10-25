
package com.example.moviestask.di
import com.example.moviestask.BuildConfig

import com.example.moviestask.data.data_source.MoviesApiService
import com.example.moviestask.utils.Constants.BASE_URL
import com.google.gson.GsonBuilder
import com.ihsanbal.logging.LoggingInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.internal.platform.Platform
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {


    @Provides
    @Singleton
    fun provideRetrofit(): MoviesApiService {
        val loggingInterceptor: LoggingInterceptor = LoggingInterceptor.Builder()
//                .loggable(true)x
            .setLevel(com.ihsanbal.logging.Level.BASIC)
            .log(Platform.INFO)
            .request("request")
            .response("response")
            .addHeader("Accept", "application/json")
            .addHeader("charset", "utf-8")
            .addHeader("Content-Type", "application/json")
            .addHeader("Authorization", "Bearer ${BuildConfig.API_KEY}")
//                .addHeader("Accept-Language", Lingver.getInstance().getLocale().language)
//                .addHeader("version", BuildConfig.VERSION_NAME)
            .build()
        val okHttpClient = OkHttpClient.Builder()
            .connectTimeout(1000, TimeUnit.SECONDS)
            .addInterceptor(loggingInterceptor)
            .readTimeout(1000, TimeUnit.SECONDS)
            .writeTimeout(1000, TimeUnit.SECONDS)
//                .followRedirects(false)
//                .followSslRedirects(false)
            .build()
        val gson = GsonBuilder()
            .setLenient()
            .create()
        val retrofitObject = Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(okHttpClient).build()


        return retrofitObject.create(MoviesApiService::class.java)
    }



}
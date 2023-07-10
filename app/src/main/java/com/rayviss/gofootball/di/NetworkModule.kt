package com.rayviss.gofootball.di

import com.rayviss.gofootball.data.api.FootballApiService
import com.rayviss.gofootball.utils.Constants
import com.rayviss.gofootball.utils.RequestInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
class NetWorkModule {
    @Provides
    fun okHttp(): OkHttpClient {
        val  loggingInteceptor= HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }

        return OkHttpClient.Builder()
            .addInterceptor(loggingInteceptor)
            .addInterceptor(RequestInterceptor())
            .build();
    }

    @Provides
    fun retrofit(okHttpClient: OkHttpClient): Retrofit
    {
        return Retrofit.Builder().
        client(okHttpClient).
        addConverterFactory(GsonConverterFactory.create()).
        baseUrl(Constants.BASE_URL).
        build();
    }

    @Provides
    fun FootballAPIService(retrofit: Retrofit): FootballApiService
    {
        return retrofit.create(FootballApiService::class.java)
    }
}
package com.creations.bawender.radiusdevtask.infrastructure.di.modules

import com.creations.bawender.radiusdevtask.infrastructure.network.FacilitiesApi
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
object NetworkModule {

    @Provides
    fun providesClient(): Retrofit {
        val interceptor = HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY)

        val httpClient = OkHttpClient
            .Builder()
            .addInterceptor(interceptor)
            .build()


        return Retrofit.Builder()
            .client(httpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://my-json-server.typicode.com/iranjith4/")
            .build()
    }

    @Provides
    fun providesFacilitiesApi(retrofit: Retrofit): FacilitiesApi {
        return retrofit.create(FacilitiesApi::class.java)
    }


}
package com.bridge.androidtechnicaltest.di

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.bridge.androidtechnicaltest.data.local.AppDatabase
import com.bridge.androidtechnicaltest.data.local.Pupil
import com.bridge.androidtechnicaltest.data.remote.PupilApi
import com.bridge.androidtechnicaltest.data.remote.PupilRemoteMediator
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private const val API_TIMEOUT: Long = 30
    private const val BASE_URL =
        "https://androidtechnicaltestapi-test.bridgeinternationalacademies.com/"

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        val builder = OkHttpClient.Builder()
        builder.readTimeout(API_TIMEOUT, TimeUnit.SECONDS)
        builder.writeTimeout(API_TIMEOUT, TimeUnit.SECONDS)
        builder.connectTimeout(API_TIMEOUT, TimeUnit.SECONDS)

        val requestId = "dda7feeb-20af-415e-887e-afc43f245624"
        val userAgent = "Bridge Android Tech Test"
        val requestInterceptor = Interceptor { chain ->
            val original = chain.request()
            val newRequest = original.newBuilder().addHeader("X-Request-ID", requestId)
                .addHeader("User-Agent", userAgent).build()
            chain.proceed(newRequest)
        }
        builder.addInterceptor(requestInterceptor)
            .addInterceptor(HttpLoggingInterceptor { message ->
                println("LOG-APP: $message")
            }.apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .addNetworkInterceptor(HttpLoggingInterceptor { message ->
                println("LOG-NET: $message")
            }.apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
        return builder.build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder().baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create()).build()
    }

    @Provides
    @Singleton
    fun providePupilApi(retrofit: Retrofit): PupilApi {
        return retrofit.create(PupilApi::class.java)
    }

    @OptIn(ExperimentalPagingApi::class)
    @Provides
    @Singleton
    fun providePupilPager(
        pupilDatabase: AppDatabase,
        pupilApi: PupilApi,
    ): Pager<Int, Pupil> {
        return Pager(
            config = PagingConfig(pageSize =20),
            remoteMediator = PupilRemoteMediator(
                pupilDatabase = pupilDatabase,
                pupilApi = pupilApi,
            ),
            pagingSourceFactory = {
                pupilDatabase.pupilDao.pagingSource()
            },
        )
    }
}

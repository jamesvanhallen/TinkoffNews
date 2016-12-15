package com.james.tinkoffnews.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.james.tinkoffnews.App
import com.james.tinkoffnews.Const
import com.james.tinkoffnews.api.Api
import com.james.tinkoffnews.mvp.model.News
import com.james.tinkoffnews.mvp.model.deserializer.NewsDeserializer
import dagger.Module
import dagger.Provides
import io.realm.Realm
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class AppModule(var app: App) {

    @Provides
    @Singleton
    fun provideGson(): Gson {
        return GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(News::class.java, NewsDeserializer())
                .serializeNulls()
                .create()
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        val logger = HttpLoggingInterceptor()
        logger.level = HttpLoggingInterceptor.Level.BODY
        val client = OkHttpClient.Builder()
                .addInterceptor(logger)
                .connectTimeout(20, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)

        return client.build()

    }

    @Provides
    @Singleton
    fun provideApi(client: OkHttpClient, gson: Gson): Api {

        val builder = Retrofit.Builder()
                .baseUrl(Const.ENDPOINT)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)

        return builder.build().create(Api::class.java)
    }

    @Provides
    @Singleton
    fun getDB(): Realm {
        return Realm.getDefaultInstance()
    }

}
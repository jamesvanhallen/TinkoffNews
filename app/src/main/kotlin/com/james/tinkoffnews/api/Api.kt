package com.james.tinkoffnews.api

import com.james.tinkoffnews.api.response.NewsContentResponse
import com.james.tinkoffnews.api.response.NewsResponse
import retrofit2.http.GET
import retrofit2.http.Query
import rx.Observable

interface Api {

    @GET("news")
    fun getNewsList(): Observable<NewsResponse>

    @GET("news_content")
    fun getNews(@Query("id") id: Int): Observable<NewsContentResponse>

}
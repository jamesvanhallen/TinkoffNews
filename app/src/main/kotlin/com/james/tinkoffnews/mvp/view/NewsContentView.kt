package com.james.tinkoffnews.mvp.view

import com.arellomobile.mvp.MvpView
import com.james.tinkoffnews.mvp.model.NewsContent

interface NewsContentView : MvpView {

    fun onSuccess(newsContent: NewsContent)
    fun onError(error: Throwable)

}
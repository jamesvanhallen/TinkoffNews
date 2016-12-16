package com.james.tinkoffnews.mvp.view

import com.arellomobile.mvp.MvpView
import com.james.tinkoffnews.mvp.model.News

interface NewsListView : MvpView {

    fun onSuccess(list: List<News>)
    fun onEmptyData()
    fun onError(error: String)
    fun onCancelProgress()

}
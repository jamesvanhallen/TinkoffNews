package com.james.tinkoffnews.mvp.presenter

import com.arellomobile.mvp.InjectViewState
import com.james.tinkoffnews.App
import com.james.tinkoffnews.api.Api
import com.james.tinkoffnews.mvp.view.NewsContentView
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import javax.inject.Inject

@InjectViewState
class NewsContentPresenter : RxPresenter<NewsContentView>() {

    @Inject
    lateinit var api: Api

    init {
        App.appComponent.inject(this)
    }

    fun loadNewsContent(id: Int) {

        val sub = api.getNews(id)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({
                    response ->
                    run {
                        val newsContent = response.payload
                        viewState.onSuccess(newsContent)
                    }

                }, { throwable ->
                    run {
                        val error = httpErrorHandler(throwable)
                        viewState.onError(error)
                    }
                })

        mainSubscription.add(sub)

    }
}
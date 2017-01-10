package com.james.tinkoffnews.mvp.presenter

import com.arellomobile.mvp.InjectViewState
import com.james.tinkoffnews.App
import com.james.tinkoffnews.api.Api
import com.james.tinkoffnews.mvp.model.News
import com.james.tinkoffnews.mvp.view.NewsListView
import io.realm.Realm
import io.realm.Sort
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import javax.inject.Inject

@InjectViewState
class NewsListPresenter : RxPresenter<NewsListView>() {

    @Inject
    lateinit var api: Api
    @Inject
    lateinit var realm: Realm

    init {
        App.appComponent.inject(this)
    }

    fun refresh() {
        val sub = api.getNewsList()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({
                    response ->
                    run {
                        val newsList = response.payload
                        if (newsList.isNotEmpty()) {
                            realm.executeTransaction { realm -> realm.insertOrUpdate(newsList) }
                        }
                    }

                }, { throwable ->
                    run {
                        viewState.onError(throwable)
                        cancelProgress()
                    }
                })

        mainSubscription.add(sub)

    }

    fun loadNewsFromDB() {
        val subscription = realm.where(News::class.java)
                .findAllSortedAsync(News.DATE, Sort.DESCENDING)
                .asObservable()
                .filter { result -> result.isLoaded }
                .subscribe({
                    listFromDB ->
                    run {
                        if (listFromDB.isNotEmpty()) {
                            viewState.onSuccess(listFromDB)
                            cancelProgress()
                        } else {
                            viewState.onEmptyData()
                            cancelProgress()
                        }
                    }
                }, { throwable ->
                    run {
                        viewState.onError(throwable)
                        cancelProgress()
                    }
                })
        mainSubscription.add(subscription)
    }

    fun cancelProgress() {
        viewState.onCancelProgress()
    }
}
package com.james.tinkoffnews.mvp.presenter

import android.R.attr.data
import android.util.Log
import com.arellomobile.mvp.InjectViewState
import com.james.tinkoffnews.App
import com.james.tinkoffnews.api.Api
import com.james.tinkoffnews.mvp.model.News
import com.james.tinkoffnews.mvp.view.NewsListView
import io.realm.Realm
import io.realm.Sort
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import java.util.*
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
        Log.v("PRESENTER", "load from network")
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
                        Log.e("PRESENTER", "network on error call")
                        val error = httpErrorHandler(throwable)
                        viewState.onError(error)
                    }
                })

        mainSubscription.add(sub)

    }

    fun loadNewsFromDB(){
        Log.v("NewsListFragment", "load from db")
        val subscription = realm.where(News::class.java)
                .findAllSortedAsync(News.DATE, Sort.DESCENDING)
                .asObservable()
                .filter { result -> result.isLoaded}
                .subscribe({
                    listFromDB ->
                    run {
                        if (listFromDB.isNotEmpty()) {
                            viewState.onSuccess(listFromDB)
                        }
                        else {
                            viewState.onEmptyData()
                        }
                    }
                }, { throwable ->
                    run {
                        Log.e("NewsListFragment", "db on error call")
                        val error = httpErrorHandler(throwable)
                        viewState.onError(error)
                    }
                })
        mainSubscription.add(subscription)
    }
}
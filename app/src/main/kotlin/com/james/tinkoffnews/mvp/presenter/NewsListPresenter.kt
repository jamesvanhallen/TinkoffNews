package com.james.tinkoffnews.mvp.presenter

import com.arellomobile.mvp.InjectViewState
import com.james.tinkoffnews.App
import com.james.tinkoffnews.api.Api
import com.james.tinkoffnews.mvp.model.News
import com.james.tinkoffnews.mvp.view.NewsListView
import io.realm.Realm
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

    fun loadNewsFromNetwork() {

        val sub = api.getNewsList()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({
                    response ->
                    run {
                        val newsList = response.payload
                        if (newsList.isNotEmpty()) {
                            viewState.onSuccess(newsList)
                            realm.executeTransaction { realm -> realm.insertOrUpdate(newsList) }
                        }
                        else {
                            viewState.onEmptyData()
                        }
                    }

                }, { throwable ->
                    run {
                        val error = httpErrorHandler(throwable)
                        viewState.onError(error)
                    }
                })

        mainSubscription.add(sub)

    }

    fun loadNewsFromDB(){
        val subscription = realm.where(News::class.java)
                .findAllAsync()
                .asObservable()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    response ->
                    run {
                        if (response.isNotEmpty()) {
                            viewState.onSuccess(response)
                        }
                        else {
                            viewState.onEmptyData()
                        }
                    }
                }, { throwable ->
                    run {
                        val error = httpErrorHandler(throwable)
                        viewState.onError(error)
                    }
                })
        mainSubscription.add(subscription)
    }

    fun presentModels(isOnline: Boolean) {
        if (!isOnline) loadNewsFromDB() else loadNewsFromNetwork()
    }

    override fun onDestroy() {
        realm.close()
        super.onDestroy()
    }

}
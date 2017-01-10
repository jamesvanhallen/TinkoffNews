package com.james.tinkoffnews.mvp.presenter

import com.arellomobile.mvp.MvpPresenter
import com.arellomobile.mvp.MvpView
import rx.subscriptions.CompositeSubscription

abstract class RxPresenter<V : MvpView> : MvpPresenter<V>() {

    protected var mainSubscription = CompositeSubscription()

    override fun onDestroy() {
        mainSubscription.clear()
        super.onDestroy()
    }
}
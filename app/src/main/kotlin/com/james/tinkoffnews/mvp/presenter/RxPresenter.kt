package com.james.tinkoffnews.mvp.presenter

import com.arellomobile.mvp.MvpPresenter
import com.arellomobile.mvp.MvpView
import retrofit2.adapter.rxjava.HttpException
import rx.subscriptions.CompositeSubscription
import java.io.IOException

abstract class RxPresenter<V : MvpView> : MvpPresenter<V>() {

    protected var mainSubscription = CompositeSubscription()

    override fun onDestroy() {
        mainSubscription.clear()
        super.onDestroy()
    }

    fun httpErrorHandler(throwable: Throwable): String {
        var error = "Неизвесная ошибка"
        if (throwable is HttpException) {
            error = throwable.message()
        } else if (throwable is IOException) {
            error = "Ошибка сервера"
        }

        return error
    }
}
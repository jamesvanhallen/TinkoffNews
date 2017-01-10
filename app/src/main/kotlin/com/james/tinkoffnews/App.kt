package com.james.tinkoffnews

import android.app.Application
import com.james.tinkoffnews.di.AppComponent
import com.james.tinkoffnews.di.AppModule
import com.james.tinkoffnews.di.DaggerAppComponent
import io.realm.Realm
import io.realm.RealmConfiguration
import io.realm.rx.RealmObservableFactory
import net.danlew.android.joda.JodaTimeAndroid

class App : Application() {

    companion object {

        lateinit var appComponent: AppComponent

    }

    override fun onCreate() {
        super.onCreate()

        JodaTimeAndroid.init(this)
        Realm.init(this)
        RealmConfiguration.Builder()
                .deleteRealmIfMigrationNeeded()
                .rxFactory(RealmObservableFactory())
                .build()

        appComponent = DaggerAppComponent.builder()
                .appModule(AppModule(this))
                .build()
    }
}
package com.james.tinkoffnews.mvp.model

import com.google.gson.annotations.SerializedName
import io.realm.RealmObject

open class TinkoffDate () : RealmObject() {

    @SerializedName("milliseconds")
    var time: Long? = null

}
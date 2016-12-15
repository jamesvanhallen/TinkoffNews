package com.james.tinkoffnews.mvp.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class News() : RealmObject() {

    companion object {
        const val ID = "id"
        const val REALM_ID = "realm_id"
        const val NAME = "name"
        const val TEXT = "text"
        const val DATE = "publicationDate"
        const val BANK_ID = "bankInfoTypeId"
        const val MILLS = "milliseconds"
    }

    @SerializedName(ID)
    @PrimaryKey
    var id: Int? = null

    @SerializedName(NAME)
    var name: String? = null

    @SerializedName(TEXT)
    var text: String? = null

    @SerializedName(DATE)
    var publicationDate: Long? = null

    @SerializedName(BANK_ID)
    var baknId: Int? = null

    @SerializedName(REALM_ID)
    var realmId: Int? = null

}
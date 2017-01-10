package com.james.tinkoffnews.mvp.model

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class News : RealmObject() {

    companion object {
        const val ID = "id"
        const val NAME = "name"
        const val TEXT = "text"
        const val DATE = "publicationDate"
        const val BANK_ID = "bankInfoTypeId"
        const val MILLS = "milliseconds"
    }

    @PrimaryKey
    var id: Int? = null

    var name: String? = null

    var text: String? = null

    var publicationDate: Long? = null

    var bankId: Int? = null
}
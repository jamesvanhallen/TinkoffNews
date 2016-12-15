package com.james.tinkoffnews.mvp.model

import com.google.gson.annotations.SerializedName

open class NewsContent(@SerializedName("content") val content: String) {
    override fun toString(): String {
        return "NewsContent(content='$content')"
    }
}

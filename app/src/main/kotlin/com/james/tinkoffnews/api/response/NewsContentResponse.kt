package com.james.tinkoffnews.api.response

import com.google.gson.annotations.SerializedName
import com.james.tinkoffnews.mvp.model.NewsContent

data class NewsContentResponse(@SerializedName("payload") val payload: NewsContent)  {
    override fun toString(): String {
        return "NewsContentResponse(payload=$payload)"
    }
}

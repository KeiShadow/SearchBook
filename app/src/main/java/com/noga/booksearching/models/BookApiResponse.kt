package com.noga.booksearching.models

import com.google.gson.annotations.SerializedName
import com.noga.booksearching.LoadingState

data class BookApiResponse(

    @SerializedName("page")
    var page: String? = null,

    @SerializedName("error")
    var error: String?= null,

    @SerializedName("books")
    var books: List<ItBook>?= null,


)

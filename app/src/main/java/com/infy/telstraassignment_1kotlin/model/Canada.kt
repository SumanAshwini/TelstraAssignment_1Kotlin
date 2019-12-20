package com.infy.telstraassignment_1kotlin.model

import com.google.gson.annotations.SerializedName

import com.google.gson.annotations.Expose

class Canada {

    @SerializedName("title")
    var title: String? = null

    @SerializedName("description")
    var description: String? = null

    @SerializedName("imageHref")
    var imageHref: String? = null


}

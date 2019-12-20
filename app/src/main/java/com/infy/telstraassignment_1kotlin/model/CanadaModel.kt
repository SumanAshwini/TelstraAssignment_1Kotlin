package com.infy.telstraassignment_1kotlin.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

import java.util.ArrayList

class CanadaModel {
    @SerializedName("title")
    @Expose
    var title: String? = null
    @SerializedName("rows")
    @Expose
    var rows: ArrayList<Canada>? = null

}
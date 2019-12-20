package com.infy.telstraassignment_1kotlin.model

import com.infy.telstraassignment_1kotlin.model.Canada
import com.infy.telstraassignment_1kotlin.roomdb.RoomEntity

import java.util.ArrayList

interface CanadaRepo {
    fun getCanadasList()

    fun prepareLocalDbList(canadasModelArrayList: ArrayList<Canada>)

    fun list(roomEntityList: List<RoomEntity>)
}
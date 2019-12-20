package com.infy.telstraassignment_1kotlin.mvp.canadaslist

import com.infy.telstraassignment_1kotlin.model.Canada
import com.infy.telstraassignment_1kotlin.roomdb.RoomEntity

import java.util.ArrayList

interface CanadasListContract {


    @Throws(Throwable::class)
    fun getCanadaListFromLocal()

    @Throws(Throwable::class)
    fun setList(roomEntityList: List<RoomEntity>)

    @Throws(Throwable::class)
    fun clearLocalDb(roomEntityList: List<RoomEntity>)

    fun checkIntentConnection(): Boolean

    fun setActionBarTitle(title: String)

    fun setList(rowArrayList: ArrayList<Canada>)

    fun showLoading()

    fun hideRefreshing()

    @Throws(Throwable::class)
    fun showToast(message: String)
}
package com.infy.telstraassignment_1kotlin.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo

object Utility {

    fun checkIntenetConnection(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).state == NetworkInfo.State.CONNECTED || connectivityManager.getNetworkInfo(
                ConnectivityManager.TYPE_WIFI
            ).state == NetworkInfo.State.CONNECTED
        ) {

            true
        } else
            false
    }
}
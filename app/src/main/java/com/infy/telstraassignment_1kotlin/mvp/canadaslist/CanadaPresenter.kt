package com.infy.telstraassignment_1kotlin.mvp.canadaslist

import android.os.Build
import com.google.gson.Gson
import com.infy.telstraassignment_1kotlin.model.Canada
import com.infy.telstraassignment_1kotlin.model.CanadaModel
import com.infy.telstraassignment_1kotlin.model.CanadaRepo
import com.infy.telstraassignment_1kotlin.network.APIUtils
import com.infy.telstraassignment_1kotlin.roomdb.RoomEntity
import java.util.ArrayList
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class CanadaPresenter(internal var contract: CanadasListContract) : CanadaRepo {


    override fun list(roomEntityList: List<RoomEntity>) {
        val canadasModelArrayList = ArrayList<Canada>()
        for (i in roomEntityList.indices) {
            val canada = Canada()
            canada.title=roomEntityList[i].title
            canada.description=roomEntityList[i].description
            canada.imageHref=roomEntityList[i].imageUrl
            canadasModelArrayList.add(canada)
        }
        contract.setList(canadasModelArrayList)
    }

    override fun prepareLocalDbList(titlesModelArrayList: ArrayList<Canada>) {
        val titlesArrayList = ArrayList<RoomEntity>()
        for (i in titlesModelArrayList.indices) {
            val canadas = RoomEntity(
                titlesModelArrayList[i].title,
                titlesModelArrayList[i].description, titlesModelArrayList[i].imageHref
            )
            titlesArrayList.add(canadas)
        }
        try {
            contract.clearLocalDb(titlesArrayList)
        } catch (throwable: Throwable) {
            throwable.printStackTrace()
        }

    }

    override fun getCanadasList() {
        if (contract.checkIntentConnection()) {
            val call = APIUtils.getAPI("s/2iodh4vg0eortkl/facts.json")
            contract.showLoading()
            call.enqueue(object : Callback<ResponseBody> {
                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {
                    try {
                        assert(response.body() != null)
                        val canadasModel = Gson().fromJson<CanadaModel>(
                            response.body()!!.string(),
                            CanadaModel::class.java!!
                        )
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                            canadasModel!!.title?.let { contract.setActionBarTitle(it) }
                        } else {
                            contract.setActionBarTitle("TelstraAssignment")
                        }
                        if (canadasModel.rows!!.size != 0) {
                            canadasModel.rows?.let { contract.setList(it) }
                            canadasModel.rows?.let { prepareLocalDbList(it) }
                        }
                        contract.hideRefreshing()
                    } catch (e: Exception) {
                        e.printStackTrace()
                        contract.hideRefreshing()
                        e.message?.let { contract.showToast(it) }
                    }

                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    contract.hideRefreshing()
                    t.message?.let { contract.showToast(it) }
                }
            })
        } else {
            contract.showToast("Please check your Internet Connection")
            contract.hideRefreshing()
            contract.getCanadaListFromLocal()
        }
    }



}

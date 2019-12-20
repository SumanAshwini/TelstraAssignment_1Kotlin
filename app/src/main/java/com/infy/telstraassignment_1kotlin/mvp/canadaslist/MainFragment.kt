package com.infy.telstraassignment_1kotlin.mvp.canadaslist

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.AsyncTask
import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout

import com.infy.telstraassignment_1kotlin.R
import com.infy.telstraassignment_1kotlin.model.Canada
import com.infy.telstraassignment_1kotlin.roomdb.CanadaRoomDb
import com.infy.telstraassignment_1kotlin.util.Utility
import com.infy.telstraassignment_1kotlin.model.CanadaRepo
import com.infy.telstraassignment_1kotlin.roomdb.RoomEntity

import java.util.ArrayList


class MainFragment : Fragment(), CanadasListContract {

    internal lateinit var canadaList: RecyclerView
    internal lateinit var swipeRefreshLayout: SwipeRefreshLayout
    internal lateinit var netWorkStatus: TextView

    internal lateinit var interpreter: CanadaRepo
    internal lateinit var canadasAdapter: CanadasAdapter
    internal var networkReceiver: BroadcastReceiver? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val rootView = inflater.inflate(R.layout.frag_main, container, false)
        canadaList = rootView.findViewById(R.id.mRcvTitlesList)
        swipeRefreshLayout = rootView.findViewById(R.id.swipeRefreshLayout)
        netWorkStatus = rootView.findViewById(R.id.netWorkStatus)
        interpreter = CanadaPresenter(this)
        swipeRefreshLayout.setOnRefreshListener { checkInternetStatus() }
        checkInternetStatus()
        return rootView
    }

    override fun checkIntentConnection(): Boolean {
        return Utility.checkIntenetConnection(this!!.activity!!)
    }


    override fun setActionBarTitle(title: String) {
        if (activity != null)
            (activity as CanadasListActivity).supportActionBar!!.setTitle(title)
    }


    override fun setList(rowArrayList: ArrayList<Canada>) {
        canadasAdapter = CanadasAdapter(this!!.activity!!, rowArrayList, object : CanadasAdapter.IOnRowClickListener {
            override fun onRowClick(position: Int) {}
        })
        canadaList.layoutManager = LinearLayoutManager(activity)
        canadaList.itemAnimator = DefaultItemAnimator()
        canadaList.drawingCacheQuality = View.DRAWING_CACHE_QUALITY_HIGH
        canadaList.adapter = canadasAdapter
        canadaList.setHasFixedSize(true)
        canadaList.setItemViewCacheSize(20)
        canadaList.isDrawingCacheEnabled = true
    }

    @Throws(Throwable::class)
    override fun getCanadaListFromLocal() {
        class GetTasks : AsyncTask<Void, Void, List<RoomEntity>>() {

            override fun doInBackground(vararg voids: Void): List<RoomEntity>? {
                return if (activity != null) {
                    CanadaRoomDb
                        .getInstance(activity!!.applicationContext).canadaDao().canadasList
                } else {
                    null
                }

            }

            override fun onPostExecute(tasks: List<RoomEntity>?) {
                if (tasks != null)
                    super.onPostExecute(tasks)
                if (tasks != null) {
                    interpreter.list(tasks)
                }
            }
        }

        val gt = GetTasks()
        gt.execute()

    }


    override fun showLoading() {
        swipeRefreshLayout.isRefreshing = true
    }


    override fun hideRefreshing() {
        swipeRefreshLayout.isRefreshing = false
    }

    @Throws(Throwable::class)
    override fun showToast(message: String) {
        if (activity != null)
            Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
    }

    override fun setList(roomEntityList: List<RoomEntity>) {

        class InsertCanadas : AsyncTask<Void, Void, Void>() {

            override fun onPreExecute() {
                super.onPreExecute()
            }
            override fun doInBackground(vararg params: Void?): Void? {
                if (activity!=null)
                    CanadaRoomDb.getInstance(activity!!.applicationContext).canadaDao()
                        .insertListOfUsers(roomEntityList.requireNoNulls())
                return null
            }
        }


        val st = InsertCanadas()
        st.execute()

    }


    override fun clearLocalDb(roomEntityList: List<RoomEntity>) {

        class DeleteCanada : AsyncTask<Void, Void, Void>(){

            override fun onPreExecute() {
                super.onPreExecute()
            }

            override fun doInBackground(vararg params: Void?): Void? {
                if (activity!=null)
                    CanadaRoomDb.getInstance(activity!!.applicationContext).canadaDao()
                        .deleteTitle()
                return null
            }

            override fun onPostExecute(result: Void?) {
                if (roomEntityList.isNotEmpty()) {
                    setList(roomEntityList)
                }
            }
        }

        val dt = DeleteCanada()
        dt.execute()
    }

    fun checkInternetStatus() {
        if (networkReceiver == null) {
            networkReceiver = object : BroadcastReceiver() {
                override fun onReceive(context: Context, intent: Intent) {
                    val extras = intent.extras

                    val info = extras!!
                        .getParcelable<Parcelable>("networkInfo") as NetworkInfo

                    val state = info.state
                    if (state == NetworkInfo.State.CONNECTED) {
                        netWorkStatus.visibility = View.GONE
                        interpreter.getCanadasList()
                    } else {
                        netWorkStatus.visibility = View.VISIBLE
                        netWorkStatus.text = "Your internet Connection is Disabled"
                        try {
                            getCanadaListFromLocal()
                        } catch (throwable: Throwable) {
                            throwable.printStackTrace()
                        }

                    }
                }
            }
            val intentFilter = IntentFilter()
            intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION)
            activity!!.registerReceiver(networkReceiver, intentFilter)
        } else {
            hideRefreshing()
        }
    }

    override fun onDetach() {
        super.onDetach()
        if (networkReceiver != null) {
            activity!!.unregisterReceiver(networkReceiver)
        }
    }
}

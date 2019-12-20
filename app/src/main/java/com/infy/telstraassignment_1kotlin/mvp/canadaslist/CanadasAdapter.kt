package com.infy.telstraassignment_1kotlin.mvp.canadaslist

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.infy.telstraassignment_1kotlin.R
import com.infy.telstraassignment_1kotlin.model.Canada

import java.util.ArrayList

class CanadasAdapter(
    internal var context: Context,
    internal var rowArrayList: ArrayList<Canada>,
    internal var listener: IOnRowClickListener
) : RecyclerView.Adapter<CanadasAdapter.CanadaListHolder>() {

    interface IOnRowClickListener {
        fun onRowClick(position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CanadaListHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_canada_model, parent, false)
        return CanadaListHolder(view)
    }

    override fun onBindViewHolder(canadalistholder: CanadaListHolder, position: Int) {
        if (rowArrayList[position].title != null) {
            canadalistholder.tvTitle.setText(rowArrayList[position].title)
        } else {
            canadalistholder.tvTitle.text = "No Title"
        }

        if (rowArrayList[position].description != null) {
            canadalistholder.tvItem.setText(rowArrayList[position].description)
        } else {
            canadalistholder.tvItem.text = "No Description"
        }

        var imgUrl = rowArrayList[position].imageHref
        if (imgUrl != null && imgUrl!!.contains("http://")) {
            imgUrl = imgUrl!!.replace("http://", "https://")
        }
        if (imgUrl != null) {
            try {
                Glide.with(context)
                    .load(imgUrl)
                    .error(R.drawable.no_preview)
                    .placeholder(R.drawable.placeholder)
                    .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                    .listener(object : RequestListener<Drawable> {
                        override fun onLoadFailed(
                            e: GlideException?, model: Any,
                            target: Target<Drawable>, isFirstResource: Boolean
                        ): Boolean {
                            canadalistholder.pgBar.visibility = View.GONE
                            return false
                        }

                        override fun onResourceReady(
                            resource: Drawable, model: Any,
                            target: Target<Drawable>, dataSource: DataSource,
                            isFirstResource: Boolean
                        ): Boolean {
                            canadalistholder.pgBar.visibility = View.GONE
                            return false
                        }
                    }).into(canadalistholder.imageItem)

            } catch (e: Exception) {
                Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
            }

        } else {
            canadalistholder.imageItem.setImageResource(R.drawable.no_preview)
        }

    }

    override fun getItemCount(): Int {
        return rowArrayList.size
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    inner class CanadaListHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal var imageHref: ImageView
        internal var imageItem: ImageView
        internal var tvTitle: TextView
        internal var tvItem: TextView
        internal var relList: RelativeLayout
        internal var pgBar: ProgressBar

        init {
            imageHref = itemView.findViewById(R.id.imageItem)
            tvTitle = itemView.findViewById(R.id.tvTitle)
            tvItem = itemView.findViewById(R.id.tvItem)
            imageItem = itemView.findViewById(R.id.imageItems)
            relList = itemView.findViewById(R.id.relListItem)
            pgBar = itemView.findViewById(R.id.pgBar)
        }
    }
}

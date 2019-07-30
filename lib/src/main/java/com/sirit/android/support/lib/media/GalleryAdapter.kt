package com.sirit.android.support.lib.media

import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.Rect
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sirit.android.support.lib.R
import com.sirit.android.support.lib.extention.widthPixels

/**
 * @author kai.w
 * @des  $des
 */
class GalleryAdapter(private val ctx: Context, private val mediaBeans: MutableList<com.sirit.android.support.lib.media.MediaBean> = mutableListOf()) : androidx.recyclerview.widget.RecyclerView.Adapter<GalleryAdapter.Companion.GalleryViewHolder>() {


    init {

    }


    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): GalleryViewHolder {
        val view = LayoutInflater.from(ctx).inflate(R.layout.item_gallery, p0, false)
        return GalleryViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mediaBeans.size
    }

    override fun onBindViewHolder(p0: GalleryViewHolder, p1: Int) {
        val mediaBean = mediaBeans[p1]

        mediaBean?.let {
            p0.ivShowImg.setImageBitmap(BitmapFactory.decodeFile(it.filePath))
        }
    }


    fun loadData(mediaBeans: MutableList<com.sirit.android.support.lib.media.MediaBean>) {
        this.mediaBeans.addAll(mediaBeans)
//        this.notifyItemRangeInserted(this.mediaBeans.size - mediaBeans.size, mediaBeans.size)
        this.notifyDataSetChanged()
    }

    fun resetData(mediaBeans: MutableList<com.sirit.android.support.lib.media.MediaBean>) {
        this.mediaBeans.clear()
        this.mediaBeans.addAll(mediaBeans)
//        this.notifyItemRangeInserted(this.mediaBeans.size - mediaBeans.size, mediaBeans.size)
        this.notifyDataSetChanged()
    }

    override fun onAttachedToRecyclerView(recyclerView: androidx.recyclerview.widget.RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
    }

    override fun onViewAttachedToWindow(holder: GalleryViewHolder) {
        super.onViewAttachedToWindow(holder)
        (holder.itemView?.parent as? androidx.recyclerview.widget.RecyclerView?)?.let {
            (it.layoutManager as? androidx.recyclerview.widget.GridLayoutManager?)?.let {
                val screenWidth = holder.itemView.context.widthPixels
                val spanCount = it.spanCount
                val outRect = Rect()
                it.calculateItemDecorationsForChild(holder.itemView, outRect)
                val viewWidth = screenWidth.toFloat() / spanCount - outRect.left - outRect.right
                holder.itemView.layoutParams.height = viewWidth.toInt()
            }
        }
    }

    companion object {
        class GalleryViewHolder(convertView: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(convertView) {
            val ivShowImg by lazy { convertView.findViewById<AppCompatImageView>(R.id.ivShowImg) }
        }
    }
}
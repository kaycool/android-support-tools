package com.sirit.android.support.media

import android.content.Context
import android.graphics.BitmapFactory
import android.support.v7.widget.AppCompatImageView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
 * @author kai.w
 * @des  $des
 */
class GalleryAdapter(private val ctx: Context, private val mediaBeans: MutableList<MediaBean> = mutableListOf()) : RecyclerView.Adapter<GalleryAdapter.Companion.GalleryViewHolder>() {

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
            (p0.itemView.layoutParams as? RecyclerView.LayoutParams)?.apply {
                this.height = this.width
                p0.itemView.layoutParams = this

                p0.ivShowImg.setImageBitmap(BitmapFactory.decodeFile(it.filePath))
            }
        }
    }


    fun loadData(mediaBeans: MutableList<MediaBean>) {
        this.mediaBeans.addAll(mediaBeans)
//        this.notifyItemRangeInserted(this.mediaBeans.size - mediaBeans.size, mediaBeans.size)
        this.notifyDataSetChanged()
    }

    fun resetData(mediaBeans: MutableList<MediaBean>) {
        this.mediaBeans.clear()
        this.mediaBeans.addAll(mediaBeans)
//        this.notifyItemRangeInserted(this.mediaBeans.size - mediaBeans.size, mediaBeans.size)
        this.notifyDataSetChanged()
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
    }

    companion object {
        class GalleryViewHolder(convertView: View) : RecyclerView.ViewHolder(convertView) {
            val ivShowImg by lazy { convertView.findViewById<AppCompatImageView>(R.id.ivShowImg) }
        }
    }
}
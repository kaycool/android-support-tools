package com.sirit.android.support.media

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
 * @author kai.w
 * @des  $des
 */
class GalleryAdapter(val ctx: Context) : RecyclerView.Adapter<GalleryAdapter.Companion.GalleryViewHolder>() {

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): GalleryViewHolder {
        val view = LayoutInflater.from(ctx).inflate(R.layout.item_gallery, p0, false)
        return GalleryViewHolder(view)
    }

    override fun getItemCount(): Int {
        return 8
    }

    override fun onBindViewHolder(p0: GalleryViewHolder, p1: Int) {

    }


    companion object {
        class GalleryViewHolder(convertView: View) : RecyclerView.ViewHolder(convertView) {

        }
    }
}
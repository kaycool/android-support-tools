package com.sirit.android.support.lib.widget.recycle

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup

/**
 * @author kai.w
 * @des  $des
 */
class WrapAdapter<T>(val list: MutableList<T>, val adapter: RecyclerView.Adapter<RecyclerView.ViewHolder>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var mHeaderView: View? = null
    private var mFooterView: View? = null
    private var mSpaceView: View? = null

    fun setHeader(headerView: View) {
        mHeaderView = headerView
    }

    fun setFooter(footerView: View) {
        mFooterView = footerView
    }

    fun setSpace(spaceView: View) {
        mSpaceView = spaceView
    }

    override fun getItemViewType(position: Int): Int {
        return when {
            position == 0 && mHeaderView != null -> ITEM_HEADER_TYPE
            position == itemCount - 1 && mFooterView != null -> ITEM_FOOTER_TYPE
            itemCount == 1 && mSpaceView != null -> ITEM_SPACE_TYPE
            else -> ITEM_NORMAL_TYPE
        }
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): RecyclerView.ViewHolder {

        return when (p1) {
//            ITEM_HEADER_TYPE -> {
//            }
//            ITEM_HEADER_TYPE -> {
//            }
//            ITEM_HEADER_TYPE -> {
//            }
            else -> adapter.onCreateViewHolder(p0, p1)
        }

    }

    override fun getItemCount(): Int {
        var itemCount = list.size

        if (mHeaderView != null) {
            itemCount++
        }

        if (mFooterView != null) {
            itemCount++
        }

        if (mSpaceView != null && itemCount == 0) {
            itemCount++
        }
        return itemCount
    }

    override fun onBindViewHolder(p0: RecyclerView.ViewHolder, p1: Int) {
        when (p1) {
            ITEM_HEADER_TYPE -> {
            }
            ITEM_HEADER_TYPE -> {
            }
            ITEM_HEADER_TYPE -> {
            }
            else -> adapter.onBindViewHolder(p0, p1)
        }
    }


    companion object {
        val ITEM_NORMAL_TYPE = 0
        val ITEM_HEADER_TYPE = -1
        val ITEM_FOOTER_TYPE = -2
        val ITEM_SPACE_TYPE = -3

    }
}
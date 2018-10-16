package com.sirit.android.support.media.demo

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import com.sirit.android.support.lib.compat.StatusBarCompat
import com.sirit.android.support.lib.media.MediaBean
import com.sirit.android.support.lib.media.MediaDataCallback
import com.sirit.android.support.lib.media.MediaHelp
import com.sirit.android.support.media.R

/**
 * @author kai.w
 * @des  $des
 */
class GalleryActivity : AppCompatActivity(), MediaDataCallback {

    private val mediaModel by lazy { intent.getSerializableExtra(Params.MEDIA_MODEL) as? MediaModel? }
    private val mRvGallery by lazy { findViewById<RecyclerView>(R.id.rvGallery) }
    private val mGalleryAdapter by lazy { GalleryAdapter(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gallery)
        StatusBarCompat.compat(this)

        mRvGallery.apply {
            this.layoutManager = GridLayoutManager(this@GalleryActivity, 2)
            this.adapter = mGalleryAdapter
        }

        MediaHelp.Companion.Build(this).setDataCallback(this).build().startImages(true, contentResolver)
    }


    override fun mediaCallback(mediaList: MutableList<MediaBean>) {
        mGalleryAdapter.resetData(mediaList)
    }

}
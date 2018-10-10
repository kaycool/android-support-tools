package com.sirit.android.support.media

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import com.sirit.android.support.lib.compat.StatusBarCompat

/**
 * @author kai.w
 * @des  $des
 */
class GalleryActivity : AppCompatActivity() {
    private val mediaModel by lazy { intent.getSerializableExtra(Params.MEDIA_MODEL) as? MediaModel? }
    private val mTitleToolBar by lazy { findViewById<Toolbar>(R.id.titleToolbar) }
    private val mRvGallery by lazy { findViewById<RecyclerView>(R.id.rvGallery) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gallery)
        StatusBarCompat.compat(this)
        setSupportActionBar(mTitleToolBar)

        mediaModel?.let {
            mRvGallery.layoutManager = GridLayoutManager(this, it.spanCount)
            mRvGallery.adapter = GalleryAdapter(this)
        }
    }

}
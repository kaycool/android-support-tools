package com.sirit.android.support.lib.media

import android.graphics.Canvas
import android.graphics.Rect
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.View
import com.sirit.android.support.lib.R
import com.sirit.android.support.lib.extention.dp2px

/**
 * @author kai.w
 * @des  $des
 */
class GalleryActivity : AppCompatActivity(), MediaScanCallback {
    private val mRvGallery by lazy { findViewById<androidx.recyclerview.widget.RecyclerView>(R.id.rvGallery) }
    private val mGalleryAdapter by lazy { GalleryAdapter(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gallery)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE), 0x111111)
        }

        mRvGallery.apply {
            this.layoutManager = androidx.recyclerview.widget.GridLayoutManager(this@GalleryActivity, 2)
            this.adapter = mGalleryAdapter

            this.addItemDecoration(object : androidx.recyclerview.widget.RecyclerView.ItemDecoration() {

                override fun onDrawOver(c: Canvas, parent: androidx.recyclerview.widget.RecyclerView, state: androidx.recyclerview.widget.RecyclerView.State) {
                    super.onDrawOver(c, parent, state)
                }

                override fun onDraw(c: Canvas, parent: androidx.recyclerview.widget.RecyclerView, state: androidx.recyclerview.widget.RecyclerView.State) {
                    super.onDraw(c, parent, state)
                }

                override fun getItemOffsets(outRect: Rect, view: View, parent: androidx.recyclerview.widget.RecyclerView, state: androidx.recyclerview.widget.RecyclerView.State) {
                    super.getItemOffsets(outRect, view, parent, state)

                    val layoutmanager = (parent.layoutManager as androidx.recyclerview.widget.GridLayoutManager)
                    val spanCount: Int = layoutmanager.spanCount

                    val viewPosition: Int = layoutmanager.getPosition(view)

                    val rem = viewPosition.rem(spanCount)

                    when (rem) {
                        0 -> {
                            outRect.right = dp2px(5f)
                        }
                        else -> {
                            outRect.left = dp2px(5f)
                        }
                    }
                    outRect.bottom = dp2px(10f)
                }
            })
        }
        MediaScanHelp.registerMediaScanCallback(this).scanImages(true, contentResolver)
    }


    override fun onDestroy() {
        super.onDestroy()
        MediaScanHelp.unRegisterMediaScanCallback().clear()
    }


    override fun mediaCallback(dirMedia: DirMedia?, groupMedia: DirWithMedia?) {

        groupMedia?.mDirWithPhotoMap?.let {
            mGalleryAdapter.loadData(mutableListOf<MediaBean>().apply {
                it[dirMedia!!.dirName[0]]?.forEach {
                    this.add(it.parseToMediaBean())
                }
            })
        }

        groupMedia?.mDirWithVideoMap?.let {
            mGalleryAdapter.loadData(mutableListOf<MediaBean>().apply {
                it[dirMedia!!.dirName[0]]?.forEach {
                    this.add(it.parseToMediaBean())
                }
            })
        }

    }
}
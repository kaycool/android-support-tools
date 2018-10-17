package com.sirit.android.support.lib.media

import android.graphics.Canvas
import android.graphics.Rect
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.sirit.android.support.lib.R
import com.sirit.android.support.lib.extention.dp2px

/**
 * @author kai.w
 * @des  $des
 */
class GalleryActivity : AppCompatActivity(), MediaScanCallback {
    private val mRvGallery by lazy { findViewById<RecyclerView>(R.id.rvGallery) }
    private val mGalleryAdapter by lazy { GalleryAdapter(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gallery)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE), 0x111111)
        }

        mRvGallery.apply {
            this.layoutManager = GridLayoutManager(this@GalleryActivity, 2)
            this.adapter = mGalleryAdapter

            this.addItemDecoration(object : RecyclerView.ItemDecoration() {

                override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
                    super.onDrawOver(c, parent, state)
                }

                override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
                    super.onDraw(c, parent, state)
                }

                override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
                    super.getItemOffsets(outRect, view, parent, state)

                    val layoutmanager = (parent.layoutManager as GridLayoutManager)
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